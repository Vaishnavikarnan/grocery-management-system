package sql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerLogin {

    JFrame frame;
    JLabel bgLabel;
    Image bgImage;

    JTextField name;
    JPasswordField pass;

    public CustomerLogin() {

        frame=new JFrame("Grocery Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //Background Image 
        bgImage=new ImageIcon("images/admin_bg.png").getImage();
        bgLabel=new JLabel();
        bgLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.setContentPane(bgLabel);
        bgLabel.setLayout(null);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Image scaled=bgImage.getScaledInstance(
                        frame.getWidth(),
                        frame.getHeight(),
                        Image.SCALE_SMOOTH
                );
                bgLabel.setIcon(new ImageIcon(scaled));
            }
        });

        // Fonts
        Font titleFont=new Font("Arial", Font.BOLD, 32);
        Font labelFont=new Font("Arial", Font.BOLD, 18);
        Font fieldFont=new Font("Arial", Font.PLAIN, 18);
        Font buttonFont=new Font("Arial", Font.BOLD, 16);

        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int cx=d.width / 2;

        // Title 
        JLabel title=new JLabel("WELCOME !", JLabel.CENTER);
        title.setFont(titleFont);
        title.setForeground(Color.blue);
        title.setBounds(cx - 200, 100, 400, 40);
        bgLabel.add(title);

        // Username 
        JLabel nameLbl=new JLabel("USER NAME");
        nameLbl.setFont(labelFont);
        nameLbl.setForeground(Color.WHITE);
        nameLbl.setBounds(cx - 250, 200, 120, 30);
        bgLabel.add(nameLbl);

        name = new JTextField();
        name.setFont(fieldFont);
        name.setBounds(cx - 100, 195, 250, 40);
        bgLabel.add(name);

        //Password 
        JLabel passLbl=new JLabel("PASSWORD");
        passLbl.setFont(labelFont);
        passLbl.setForeground(Color.WHITE);
        passLbl.setBounds(cx - 250, 260, 120, 30);
        bgLabel.add(passLbl);

        pass=new JPasswordField();
        pass.setFont(fieldFont);
        pass.setBounds(cx - 100, 255, 250, 40);
        bgLabel.add(pass);

        //Buttons
        JButton login=new JButton("Login");
        login.setFont(buttonFont);
        login.setBounds(cx - 100, 330, 110, 40);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(login);

        JButton cancel=new JButton("Cancel");
        cancel.setFont(buttonFont);
        cancel.setBounds(cx + 40, 330, 110, 40);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(cancel);

        JButton register=new JButton("New Customer? Register");
        register.setFont(new Font("Verdana", Font.BOLD, 14));
        register.setBounds(cx - 100, 390, 250, 35);
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(register);

        // LOGIN ACTION (MySQL) 
        login.addActionListener(e -> {

            String uname=name.getText();
            String upass=new String(pass.getPassword());

            if (uname.isEmpty() || upass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter username and password");
                return;
            }

            try {
                Connection con=DBConnection.getConnection();

                String sql="SELECT * FROM customers WHERE name=? AND password=?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1, uname);
                ps.setString(2, upass);

                ResultSet rs=ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame,"Login Successful");
                    frame.dispose();
                    new ProductMenu();
                } else {
                    JOptionPane.showMessageDialog(frame,"Invalid Username or Password");
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame,"Database Error");
            }
        });

        // next page connection
        register.addActionListener(e ->new CustomerRegister());
        cancel.addActionListener(e ->frame.dispose());

        frame.setVisible(true);
    }
}
