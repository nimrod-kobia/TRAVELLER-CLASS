package gui.panels.admin;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookingManagementPanel extends JPanel {
    private DefaultTableModel bookingTableModel;
    private JTable bookingTable;

    public BookingManagementPanel() {
        setLayout(new BorderLayout());
        bookingTableModel = new DefaultTableModel(new Object[]{"Booking ID", "User", "Flight", "Seat", "Time", "Price", "Status"}, 0);
        bookingTable = new JTable(bookingTableModel);
        bookingTable.getTableHeader().setResizingAllowed(false);
        bookingTable.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton createBookingBtn = new JButton("Create Booking");
        JButton editBookingBtn = new JButton("Edit Booking");
        JButton cancelBookingBtn = new JButton("Cancel Booking");
        btnPanel.add(createBookingBtn);
        btnPanel.add(editBookingBtn);
        btnPanel.add(cancelBookingBtn);
        add(btnPanel, BorderLayout.SOUTH);

        createBookingBtn.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Show booking creation dialog here."));
        editBookingBtn.addActionListener(_ -> {
            if (bookingTable.getSelectedRow() >= 0) {
                JOptionPane.showMessageDialog(this, "Show booking edit dialog here.");
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to edit.");
            }
        });
        cancelBookingBtn.addActionListener(_ -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookingTableModel.removeRow(selectedRow);
                    // Add logic to actually cancel the booking in your data model
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to cancel.");
            }
        });
    }
}