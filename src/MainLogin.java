package sql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainLogin {

    JFrame frame;
    JLabel bgLabel;
    Image bgImage;

    public MainLogin() {

        frame =new JFrame("Grocery Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Background image
        bgImage=new ImageIcon("images/bg2.jpg").getImage();
        bgLabel=new JLabel();
        bgLabel.setLayout(new GridBagLayout());
        frame.add(bgLabel, BorderLayout.CENTER);

        // Content panel with spacing
        JPanel content=new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Top space
        content.add(Box.createVerticalStrut(40));

        // Welcome text (TOP)
        JLabel title1 = new JLabel("WELCOME TO ONLINE SHOPPING");
        title1.setAlignmentX(Component.CENTER_ALIGNMENT);
        title1.setFont(new Font("Times New Roman", Font.BOLD, 50));
        title1.setForeground(Color.BLACK);  

        // Space
        content.add(title1);
        content.add(Box.createVerticalStrut(25));

   
        JLabel title2 = new JLabel("@ VEE MART");
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2.setFont(new Font("Times New Roman", Font.BOLD, 38));
       // title2.setForeground(new Color(243, 156, 18)); // Golden Orange
        title2.setForeground(Color.white);

        content.add(title2);
        content.add(Box.createVerticalStrut(30));

        //Login Button
        JButton loginBtn=new JButton("Shop Now !");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        loginBtn.setBackground(new Color(255, 140, 0));   // dark Orange
        loginBtn.setForeground(Color.white);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setPreferredSize(new Dimension(180, 35));
        loginBtn.setMaximumSize(new Dimension(180, 35));
        loginBtn.setOpaque(true);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        content.add(loginBtn);
        bgLabel.add(content);

        //Button action
        loginBtn.addActionListener(e -> {
            frame.dispose();
            new CustomerLogin();
        });

        //Resize background
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeBackground();
            }
        });
        frame.setVisible(true);
    }

    private void resizeBackground() {
        int w=frame.getWidth();
        int h=frame.getHeight();
        if (w <= 0 || h <= 0) 
        	return;

        Image scaled=bgImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        bgLabel.setIcon(new ImageIcon(scaled));
    }

    public static void main(String[] args) {
    	System.out.println("hello,Runnable Jar");
        new MainLogin();
    }
}
