package sql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptDownloader {

    public static void saveAsText(JFrame parentFrame, DefaultTableModel model, double totalAmount) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Bill Receipt");
        chooser.setSelectedFile(new File("Bill_Receipt.txt"));

        int result = chooser.showSaveDialog(parentFrame);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();

                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                FileWriter fw = new FileWriter(file);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String dateTime = LocalDateTime.now().format(formatter);
                String receiptNo = "RCPT-" + System.currentTimeMillis();

                fw.write("=========================================\n");
                fw.write("          GROCERY BILL RECEIPT\n");
                fw.write("               @ Vee Mart\n");
                fw.write("=========================================\n");
                fw.write("Receipt No : " + receiptNo + "\n");
                fw.write("Date & Time: " + dateTime + "\n");
                fw.write("-----------------------------------------\n\n");

                fw.write(String.format("%-20s %-10s %-10s\n", "Item", "Qty", "Amount"));
                fw.write("-----------------------------------------\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    fw.write(String.format("%-20s %-10s %-10s\n",
                            model.getValueAt(i, 0),
                            model.getValueAt(i, 1),
                            model.getValueAt(i, 2)));
                }

                fw.write("\n-----------------------------------------\n");
                fw.write(String.format("TOTAL AMOUNT : Rs. %.2f\n", totalAmount));
                fw.write("-----------------------------------------\n");
                fw.write(" Thank you for shopping with us!\n");
                fw.write(" Contact: 9876543210\n");
                fw.write("=========================================\n");

                fw.close();

                JOptionPane.showMessageDialog(parentFrame,
                        "Bill Receipt Saved Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame,
                        "Error saving receipt!\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
