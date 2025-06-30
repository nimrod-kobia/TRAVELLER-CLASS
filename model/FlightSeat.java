package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;

// Independent FlightSeat class
public class FlightSeat {
    private String seatNumber;
    private String seatClass; // e.g., "Economy", "Business", "First"
    private boolean isBooked;
    private double price;

    // Constructor
    public FlightSeat(String seatNumber, String seatClass, double price) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
        this.isBooked = false; // Seat is available by default
    }

    // Getters
    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public double getPrice() {
        return price;
    }

    // Book the seat
    public boolean bookSeat() {
        if (!isBooked) {
            isBooked = true;
            System.out.println("Seat " + seatNumber + " booked successfully.");
            return true;
        } else {
            System.out.println("Seat " + seatNumber + " is already booked.");
            return false;
        }
    }

    // Display seat details
    public void displaySeatInfo() {
        System.out.println("Seat: " + seatNumber +
                           " | Class: " + seatClass +
                           " | Price: Ksh " + price +
                           " | Status: " + (isBooked ? "Booked" : "Available"));
    }
}

class FlightSeatPanel extends JPanel {
    private JTable seatMapTable;
    private DefaultTableModel seatMapTableModel;
    private JTextField selectedSeatField;
    private SeatTableCellRenderer seatMapRenderer;

    public FlightSeatPanel() {
        setLayout(new BorderLayout());

        seatMapTableModel = new DefaultTableModel(6, 4) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        seatMapTable = new JTable(seatMapTableModel);

        seatMapTable.setCellSelectionEnabled(true);
        seatMapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        seatMapRenderer = new SeatTableCellRenderer();
        seatMapTable.setDefaultRenderer(Object.class, seatMapRenderer);
        seatMapTable.setRowHeight(30);
        JScrollPane seatScroll = new JScrollPane(seatMapTable);
        add(seatScroll, BorderLayout.EAST);

        selectedSeatField = new JTextField(5);
        selectedSeatField.setEditable(false);
        add(selectedSeatField, BorderLayout.SOUTH);

        seatMapTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = seatMapTable.getSelectedRow();
                int col = seatMapTable.getSelectedColumn();
                String seatId = (row + 1) + "" + (char)('A' + col);
                selectedSeatField.setText(seatId);
                seatMapTable.changeSelection(row, col, false, false);
            }
        });
    }

    // You should also define SeatTableCellRenderer as a static inner class or import it if it's elsewhere
    static class SeatTableCellRenderer extends DefaultTableCellRenderer {
        // ...implement as needed...
    }
}
