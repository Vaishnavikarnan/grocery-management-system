package sql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerRegister {

    JFrame frame;
    JLabel bgLabel;
    Image bgImage;

    JTextField name, phone, address, pincode;
    JPasswordField pass;

    public CustomerRegister() {

        frame=new JFrame("Grocery Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Background Image
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
        Font titleFont=new Font("Arial", Font.BOLD, 28);
        Font labelFont=new Font("Arial", Font.BOLD, 16);
        Font fieldFont=new Font("Arial", Font.PLAIN, 16);
        Font buttonFont=new Font("Arial", Font.BOLD, 15);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = d.width / 2;

        // Title 
        JLabel title=new JLabel("Customer Registration", JLabel.CENTER);
        title.setFont(titleFont);
        title.setForeground(Color.BLUE);
        title.setBounds(cx - 220, 80, 440, 40);
        bgLabel.add(title);

        int y =160;
        int gap =55;

        // Name 
        bgLabel.add(createLabel("Name", cx - 240, y, labelFont));
        name = createField(cx - 80, y - 5, fieldFont);
        bgLabel.add(name);

        // Phone 
        y +=gap;
        bgLabel.add(createLabel("Phone", cx - 240, y, labelFont));
        phone = createField(cx - 80, y - 5, fieldFont);
        bgLabel.add(phone);

        // Address 
        y +=gap;
        bgLabel.add(createLabel("Address", cx - 240, y, labelFont));
        address = createField(cx - 80, y - 5, fieldFont);
        bgLabel.add(address);

        // Pincode 
        y += gap;
        bgLabel.add(createLabel("Pincode", cx - 240, y, labelFont));
        pincode=createField(cx - 80, y - 5, fieldFont);
        bgLabel.add(pincode);

        // Password 
        y += gap;
        bgLabel.add(createLabel("Password", cx - 240, y, labelFont));
        pass =new JPasswordField();
        pass.setFont(fieldFont);
        pass.setBounds(cx - 80, y - 5, 230, 35);
        bgLabel.add(pass);

        // Buttons 
        JButton submit =new JButton("Submit");
        submit.setFont(buttonFont);
        submit.setBounds(cx - 80, y + 60, 100, 35);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(submit);

        JButton cancel=new JButton("Cancel");
        cancel.setFont(buttonFont);
        cancel.setBounds(cx + 50, y + 60, 100, 35);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(cancel);

        // next page connection
        submit.addActionListener(e -> registerCustomer());
        cancel.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    //MYSQL INSERT 
    private void registerCustomer() {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO customers (name, phone, address, pincode, password) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1, name.getText());
            ps.setString(2, phone.getText());
            ps.setString(3, address.getText());
            ps.setString(4, pincode.getText());
            ps.setString(5, new String(pass.getPassword()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Registration Successful");

            frame.dispose();
            new CustomerLogin(); // optional redirect

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,"Database Error");
        }
    }

    //Helper Methods
    private JLabel createLabel(String text, int x, int y, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, 120, 25);
        return lbl;
    }

    private JTextField createField(int x, int y, Font font) {
        JTextField tf=new JTextField();
        tf.setFont(font);
        tf.setBounds(x, y, 230, 35);
        return tf;
    }
}
