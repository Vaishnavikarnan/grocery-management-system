package sql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillPage {

    JFrame frame;
    JLabel bgLabel, title, totalLabel;
    JTable billTable;
    Image bgImage;
    
    double total;
    public BillPage(DefaultTableModel orderModel) {

        frame=new JFrame("Bill Receipt");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // BG
        bgImage=new ImageIcon("images/admin_bg.png").getImage();
        bgLabel=new JLabel();
        bgLabel.setLayout(null);
        frame.setContentPane(bgLabel);

        // title
        title=new JLabel("YOUR BILL",JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        bgLabel.add(title);

        // table
        String[] cols={"Item", "Quantity", "Amount"};
        DefaultTableModel billModel=new DefaultTableModel(cols, 0);
        billTable=new JTable(billModel);
        billTable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        billTable.setRowHeight(32);

        JScrollPane sp=new JScrollPane(billTable);
        bgLabel.add(sp);

        // Bill calculation
         total=0;
        for (int i=0; i<orderModel.getRowCount(); i++) {

            String item=orderModel.getValueAt(i, 0).toString();
            int qty=Integer.parseInt(orderModel.getValueAt(i, 1).toString());
            double price=Double.parseDouble(orderModel.getValueAt(i, 2).toString());

            total+=price;
            billModel.addRow(new Object[]{item, qty, price});
        }

        // total
        totalLabel = new JLabel("TOTAL AMOUNT : Rs. " + total, JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 26));
        totalLabel.setForeground(Color.BLACK);
        bgLabel.add(totalLabel);

        // Finish Button
        JButton finish = new JButton("Finish");
        finish.setFont(new Font("Arial", Font.BOLD, 20));
        finish.setBackground(new Color(135, 206, 235));
        finish.setForeground(Color.black);
        finish.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(finish);

        //Download Receipt Button
        JButton downloadBtn = new JButton("Download bill");
        downloadBtn.setFont(new Font("Arial", Font.BOLD, 18));
        downloadBtn.setBackground(new Color(135,206,235));
        downloadBtn.setForeground(Color.black);
        downloadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bgLabel.add(downloadBtn);

        // SAVE TO DATABASE
        finish.addActionListener(e -> {
            try {
                Connection con=DBConnection.getConnection();

                String sql="INSERT INTO orders (item_name, quantity, price) VALUES (?, ?, ?)";
                PreparedStatement ps=con.prepareStatement(sql);

                for (int i=0; i < billModel.getRowCount(); i++) {
                    ps.setString(1, billModel.getValueAt(i, 0).toString());
                    ps.setInt(2, Integer.parseInt(billModel.getValueAt(i, 1).toString()));
                    ps.setDouble(3, Double.parseDouble(billModel.getValueAt(i, 2).toString()));
                    ps.executeUpdate();
                }

                con.close();
                JOptionPane.showMessageDialog(frame,"Thanks Shopping!");
                frame.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,"Database Error while saving order");
            }
        });

        // ⭐ NEW: Download button action
        downloadBtn.addActionListener(e -> {
            ReceiptDownloader.saveAsText(frame, billModel, total);
        });

        // Resize Handling
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                int w=frame.getWidth();
                int h=frame.getHeight();

                Image scaled=bgImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                bgLabel.setIcon(new ImageIcon(scaled));

                title.setBounds(0, 30, w, 50);
                sp.setBounds(200, 120, w - 400, h - 350);
                totalLabel.setBounds(0, h - 200, w, 40);
                finish.setBounds(w / 2 + 20, h - 140, 160, 45);
                downloadBtn.setBounds(w / 2 - 200, h - 140, 180, 45);
            }
        });

        frame.setVisible(true);
    }
}
