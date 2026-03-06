package sql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductMenu {

    JFrame frame;
    JLabel bgLabel, title;
    JPanel productsPanel;
    Image bgImage;

    public ProductMenu() {

        frame = new JFrame("Grocery Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Background 
        bgImage = new ImageIcon("images/wood.jpg").getImage();
        bgLabel = new JLabel();
        bgLabel.setLayout(null);
        frame.setContentPane(bgLabel);

        // Title 
        title = new JLabel("PRODUCT MENU", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setForeground(Color.WHITE);
        bgLabel.add(title);

        //  Products Panel 
        productsPanel = new JPanel(new GridLayout(2, 5, 25, 25));
        productsPanel.setOpaque(false);
        bgLabel.add(productsPanel);

        //  Load products from MySQL 
        loadProductsFromDB();

        //  Cancel Button 
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.BOLD, 14));
        cancel.setFocusPainted(false);
        bgLabel.add(cancel);
        cancel.addActionListener(e -> frame.dispose());

        // Resize Handling 
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                int w = frame.getWidth();
                int h = frame.getHeight();

                Image scaled = bgImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                bgLabel.setIcon(new ImageIcon(scaled));

                title.setBounds(0, 25, w, 50);
                productsPanel.setBounds(60, 110, w - 120, h - 240);
                cancel.setBounds(w - 140, h - 90, 100, 35);
            }
        });

        frame.setVisible(true);
    }

    // FETCH PRODUCTS FROM MYSQL
    private void loadProductsFromDB() {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM products";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                addProduct(
                        rs.getString("name"),
                        rs.getString("image_path"),
                        rs.getDouble("price")
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame,"Unable to load products");
        }
    }

    // PRODUCT CARD 
    private void addProduct(String name, String imgPath, double price) {

        JPanel card =new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 220));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // BIGGER IMAGE
        JLabel img =new JLabel(new ImageIcon(
                new ImageIcon(imgPath).getImage()
                        .getScaledInstance(180, 140, Image.SCALE_SMOOTH)));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLbl =new JLabel(name);
        nameLbl.setFont(new Font("Arial", Font.BOLD, 16));
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLbl=new JLabel("Rs " + price + " /kg");
        priceLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CUSTOM BUY BUTTON
        JButton buy =new JButton("Buy");
        buy.setFont(new Font("Arial", Font.BOLD, 14));
        buy.setAlignmentX(Component.CENTER_ALIGNMENT);
        buy.setBackground(new Color(46, 204, 113)); // Green
        buy.setForeground(Color.WHITE);
        buy.setFocusPainted(false);
        buy.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        buy.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buy.addActionListener(e -> {
            String qtyStr = JOptionPane.showInputDialog(
                    frame,"Enter Quantity (kg):"
            );

            if (qtyStr ==null) 
            	return;

            int qty;
            try {
                qty=Integer.parseInt(qtyStr);
                if (qty <= 0) 
                	throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity");
                return;
            }

            OrderSummary.getInstance().addItem(name, qty, qty * price);
        });

        //  Layout spacing 
        card.add(Box.createVerticalStrut(12));
        card.add(img);
        card.add(Box.createVerticalStrut(10));
        card.add(nameLbl);
        card.add(Box.createVerticalStrut(4));
        card.add(priceLbl);
        card.add(Box.createVerticalStrut(10));
        card.add(buy);

        productsPanel.add(card);
    }
}
