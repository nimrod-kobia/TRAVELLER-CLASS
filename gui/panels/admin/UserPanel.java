package gui.panels.admin;


import gui.components.SeatMapCellRenderer;
import gui.dialogs.PaymentDialog;
import model.Flight;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class UserPanel extends JPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private List<Flight> flights;

    private JTextField userNameField, userEmailField, userPhoneField, selectedSeatField;
    private JTable flightTable, seatMapTable;
    private DefaultTableModel flightTableModel, seatMapTableModel;
    private SeatMapCellRenderer seatMapRenderer;
    private Flight currentlySelectedFlight;

    private JTextField passportNumberField, nationalityField, visaNumberField, visaCountryField, baggageWeightField;
    private JButton bookAndPayButton;

    public UserPanel(JPanel mainPanel, CardLayout cardLayout, List<Flight> flights) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.flights = flights; // Reference to the shared flights list

        setLayout(new BorderLayout());

        setupUserFormPanel();
        setupFlightTable();
        setupSeatMapPanel();
        setupControlPanel();

        updateUserFlightTable(); // Initial population
    }

    private void setupUserFormPanel() {
        JPanel userFormPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        userFormPanel.setBorder(BorderFactory.createTitledBorder("Enter Your Details"));
        userNameField = new JTextField();
        userEmailField = new JTextField();
        userPhoneField = new JTextField();
        passportNumberField = new JTextField();
        nationalityField = new JTextField();
        visaNumberField = new JTextField();
        visaCountryField = new JTextField();
        baggageWeightField = new JTextField();
        userFormPanel.add(new JLabel("Name:"));      
           userFormPanel.add(userNameField);
        userFormPanel.add(new JLabel("Email:"));     
           userFormPanel.add(userEmailField);
        userFormPanel.add(new JLabel("Phone:"));     
           userFormPanel.add(userPhoneField);
        userFormPanel.add(new JLabel("Passport Number:")); 
        userFormPanel.add(passportNumberField);
        userFormPanel.add(new JLabel("Nationality:"));  
        userFormPanel.add(nationalityField);
        userFormPanel.add(new JLabel("Visa Number:")); 
         userFormPanel.add(visaNumberField);
        userFormPanel.add(new JLabel("Visa Country:"));
         userFormPanel.add(visaCountryField);
        userFormPanel.add(new JLabel("Baggage Weight (kg):"));
         userFormPanel.add(baggageWeightField);
        add(userFormPanel, BorderLayout.NORTH);
    }

    private void setupFlightTable() {
        flightTableModel = new DefaultTableModel(new Object[]{"Flight No", "From", "To", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        flightTable = new JTable(flightTableModel);
        flightTable.getTableHeader().setResizingAllowed(false); // Fix 1: Prevent column resizing
        flightTable.getTableHeader().setReorderingAllowed(false);
        flightTable.getSelectionModel().addListSelectionListener(new FlightSelectionListener());
        add(new JScrollPane(flightTable), BorderLayout.CENTER);
    }

   // Inside gui/panels/UserPanel.java

private void setupSeatMapPanel() {
    int numRows = 30;
    String[] seatColumns = {"A", "B", "C", " ", "D", "E", "F"};
    String[][] seatData = new String[numRows][seatColumns.length];

    for (int row = 0; row < numRows; row++) {
        for (int col = 0; col < seatColumns.length; col++) {
            seatData[row][col] = seatColumns[col].equals(" ") ? "" : (row + 1) + seatColumns[col];
        }
    }

    seatMapTableModel = new DefaultTableModel(seatData, seatColumns) {
        public boolean isCellEditable(int row, int col) { return false; }
    };
    seatMapTable = new JTable(seatMapTableModel);
    seatMapTable.setCellSelectionEnabled(true);
    seatMapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    seatMapRenderer = new SeatMapCellRenderer();
    seatMapTable.setDefaultRenderer(Object.class, seatMapRenderer);
    seatMapTable.setRowHeight(25);
    seatMapTable.getSelectionModel().addListSelectionListener(new SeatSelectionListener(seatData, seatColumns));

    // Calculate total preferred width for the seat map table
    int totalWidth = 0;
    for (int i = 0; i < seatMapTable.getColumnCount(); i++) {
        TableColumn column = seatMapTable.getColumnModel().getColumn(i);
        if (seatColumns[i].equals(" ")) { // Aisle column
            column.setPreferredWidth(20);
            column.setMinWidth(20);
            column.setMaxWidth(20);
            totalWidth += 20; // Add to total width
        } else {
            column.setPreferredWidth(40);
            column.setMinWidth(40);
            column.setMaxWidth(40); // Fix width for seat columns
            totalWidth += 40; // Add to total width
        }
    }
    // Add space for table borders/grid lines and potential scrollbar
    totalWidth += seatMapTable.getIntercellSpacing().width * (seatMapTable.getColumnCount() - 1); // For intercell spacing
    totalWidth += seatMapTable.getInsets().left + seatMapTable.getInsets().right; // For table insets

    seatMapTable.getTableHeader().setResizingAllowed(false);
    seatMapTable.getTableHeader().setReorderingAllowed(false);

    // ********* FIX FOR EXTRA SPACE *********
    // 1. Tell the table to automatically resize its width to fit columns
    // This is important when setting fixed column widths.
    seatMapTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    // 2. Wrap the JTable in a JScrollPane
    JScrollPane scrollPane = new JScrollPane(seatMapTable);

    // 3. Explicitly set the preferred width of the JScrollPane
    // This makes the JScrollPane try to be only as wide as its contained table.
    // The height can be flexible, but the width should be constrained.
    Dimension preferredScrollPaneSize = new Dimension(totalWidth + scrollPane.getVerticalScrollBar().getPreferredSize().width + 5, // Add scrollbar width and a little extra padding
                                                       seatMapTable.getPreferredScrollableViewportSize().height); // Let height be determined by table rows
    scrollPane.setPreferredSize(preferredScrollPaneSize);
    scrollPane.setMaximumSize(preferredScrollPaneSize); // Important: Prevent it from growing too wide

    // Adding a border to see the boundaries (optional, for debugging)
    // scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLUE));

    add(scrollPane, BorderLayout.EAST);
}
    private void setupControlPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        southPanel.add(new JLabel("Selected Seat:"));
        southPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        selectedSeatField = new JTextField(5);
        selectedSeatField.setEditable(false);
        selectedSeatField.setMaximumSize(selectedSeatField.getPreferredSize());
        southPanel.add(selectedSeatField);

        southPanel.add(Box.createHorizontalGlue());

        bookAndPayButton = new JButton("Book & Pay");
        bookAndPayButton.addActionListener(_ -> handleBookAndPay());
        southPanel.add(bookAndPayButton);

        southPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton userBack = new JButton("Back");
        userBack.addActionListener(_ -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        southPanel.add(userBack);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void handleBookAndPay() {
        if (currentlySelectedFlight == null) {
            JOptionPane.showMessageDialog(this, "Please select a flight.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedSeatField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a seat.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String seatIdToBook = selectedSeatField.getText();
        if (currentlySelectedFlight.isSeatBooked(seatIdToBook)) {
            JOptionPane.showMessageDialog(this, "The selected seat is already booked. Please choose another.", "Seat Unavailable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PaymentDialog dialog = new PaymentDialog(SwingUtilities.getWindowAncestor(this), currentlySelectedFlight.getPrice());
        dialog.setVisible(true);

        if (dialog.isPaymentConfirmed()) {
            currentlySelectedFlight.bookSeat(seatIdToBook);
            JOptionPane.showMessageDialog(this, "Payment successful! Seat " + seatIdToBook + " booked for flight " + currentlySelectedFlight.getFlightNumber(), "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);

            seatMapRenderer.setBookedSeats(currentlySelectedFlight.getBookedSeats());
            seatMapTable.repaint();
            selectedSeatField.setText("");
            seatMapTable.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Payment cancelled or failed.", "Payment Status", JOptionPane.WARNING_MESSAGE);
        }
    }

    private class FlightSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = flightTable.getSelectedRow();
                if (selectedRow >= 0) {
                    currentlySelectedFlight = flights.get(selectedRow);
                    seatMapRenderer.setBookedSeats(currentlySelectedFlight.getBookedSeats());
                    seatMapTable.repaint();
                    selectedSeatField.setText("");
                } else {
                    currentlySelectedFlight = null;
                    seatMapRenderer.setBookedSeats(new HashSet<>());
                    seatMapTable.repaint();
                    selectedSeatField.setText("");
                }
            }
        }
    }

    private class SeatSelectionListener implements ListSelectionListener {
        private String[][] seatData;
        public SeatSelectionListener(String[][] seatData, String[] seatColumns) {
            this.seatData = seatData;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row = seatMapTable.getSelectedRow();
            int col = seatMapTable.getSelectedColumn();
            if (!e.getValueIsAdjusting() && row >= 0 && col >= 0) {
                String seatId = seatData[row][col];

                if (!seatId.isEmpty()) {
                    if (currentlySelectedFlight != null && !currentlySelectedFlight.isSeatBooked(seatId)) {
                        selectedSeatField.setText(seatId);
                    } else if (currentlySelectedFlight != null && currentlySelectedFlight.isSeatBooked(seatId)) {
                        JOptionPane.showMessageDialog(UserPanel.this, "This seat is already booked.", "Seat Unavailable", JOptionPane.WARNING_MESSAGE);
                        selectedSeatField.setText("");
                        seatMapTable.clearSelection();
                    } else {
                        selectedSeatField.setText("");
                    }
                } else {
                    selectedSeatField.setText("");
                }
            }
        }
    }

    // This method needs to be called when flights data changes (e.g., from admin panel)
    public void updateUserFlightTable() {
        flightTableModel.setRowCount(0); // Clear existing data
        for (Flight f : flights) {
            flightTableModel.addRow(new Object[]{f.getFlightNumber(), f.getDepartureLocation(), f.getArrivalLocation(), f.getPrice()});
        }
    }
}