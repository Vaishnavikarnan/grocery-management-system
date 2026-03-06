package sql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderSummary {

    JFrame frame;
    JTable table;
    DefaultTableModel model;

    private static OrderSummary instance;

    private OrderSummary() {

        frame=new JFrame("Order Summary");
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table 
        String[] cols={"Item", "Quantity", "Price"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(30);

        JScrollPane sp = new JScrollPane(table);

        // Confirm Button
        JButton confirm = new JButton("Confirm Order");
        confirm.setFont(new Font("Arial", Font.BOLD, 18));
        confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));

        confirm.addActionListener(e -> {
            saveOrderToDB();
            frame.dispose();
            new BillPage(model);
        });

        frame.add(sp, BorderLayout.CENTER);
        frame.add(confirm, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // SINGLE INSTANCE
    public static OrderSummary getInstance() {
        if (instance == null) {
            instance=new OrderSummary();
        }
        return instance;
    }

    // ADD ITEM 
    public void addItem(String item, int qty, double price) {
        model.addRow(new Object[]{item, qty, price});
    }

    // SAVE ORDER TO MYSQL
    private void saveOrderToDB() {
        try {
            Connection con=DBConnection.getConnection();

            String sql="INSERT INTO orders (item_name, quantity, price) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            for (int i=0; i < model.getRowCount(); i++) {
                ps.setString(1, model.getValueAt(i, 0).toString());
                ps.setInt(2, Integer.parseInt(model.getValueAt(i, 1).toString()));
                ps.setDouble(3, Double.parseDouble(model.getValueAt(i, 2).toString()));
                ps.executeUpdate();
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to save order");
        }
    }
}
