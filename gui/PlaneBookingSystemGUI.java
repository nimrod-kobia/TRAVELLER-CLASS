package gui;

import javax.swing.*;
import javax.swing.table.*;

import model.*;
import database.DBHelper;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.time.LocalDateTime;
import javax.swing.table.TableCellRenderer;

public class PlaneBookingSystemGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // User panel components
    private JPanel userPanel;
    private JTextField userNameField, userEmailField, userPhoneField, selectedSeatField;
    private JTable flightTable, seatMapTable;
    private DefaultTableModel flightTableModel, seatMapTableModel;
    private SeatMapCellRenderer seatMapRenderer;
    private Flight currentlySelectedFlight;
    private Set<String> bookedSeats = new HashSet<>();

    // Admin panel components
    private JPanel adminPanel;
    private DefaultTableModel airlineTableModel, airportTableModel;
    private JTable airlineTable, airportTable;
    private List<Airline> airlines = new ArrayList<>();
    private List<Airport> airports = new ArrayList<>();

    // Additional fields for user details
    private JTextField passportNumberField, nationalityField, visaNumberField, visaCountryField, baggageWeightField;
    private JButton bookAndPayButton;

    public PlaneBookingSystemGUI() {
        setTitle("Plane Booking System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupPanels();
        add(mainPanel, BorderLayout.CENTER);
    }

    // --- SeatMapCellRenderer implementation ---
    static class SeatMapCellRenderer extends JLabel implements TableCellRenderer {
        private Set<String> bookedSeats = new HashSet<>();

        public void setBookedSeats(Set<String> bookedSeats) {
            this.bookedSeats = bookedSeats != null ? bookedSeats : new HashSet<>();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            String seatId = value == null ? "" : value.toString();

            if (bookedSeats != null && bookedSeats.contains(seatId)) {
                setBackground(Color.RED);
                setForeground(Color.WHITE);
            } else if (isSelected) {
                setBackground(Color.BLUE);
                setForeground(Color.WHITE);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }
    //  Setup panels for role selection, user portal, and admin portal 

    private void setupPanels() {
        // Role Selection Panel
        JPanel roleSelectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome to Plane Booking System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        roleSelectionPanel.add(titleLabel, gbc);

        JButton userButton = new JButton("User Portal");
        userButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        roleSelectionPanel.add(userButton, gbc);

        JButton adminButton = new JButton("Admin Portal");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1; gbc.gridy = 1;
        roleSelectionPanel.add(adminButton, gbc);

        userButton.addActionListener(_ -> cardLayout.show(mainPanel, "USER_PANEL"));
        adminButton.addActionListener(_ -> cardLayout.show(mainPanel, "ADMIN_PANEL"));

        // --- User Panel ---
        userPanel = new JPanel(new BorderLayout());

        // User info form
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
        userPanel.add(userFormPanel, BorderLayout.NORTH);

        // Flight table
        flightTableModel = new DefaultTableModel(new Object[]{"Flight No", "From", "To", "Price"}, 0);
        flightTable = new JTable(flightTableModel);
        JScrollPane flightScroll = new JScrollPane(flightTable);
        userPanel.add(flightScroll, BorderLayout.CENTER);

        // Dummy flights
        Flight[] flights = {
            new Flight("KQ101", "Nairobi", "London", 8500),
            new Flight("KQ202", "Nairobi", "Paris", 7000),
            new Flight("KQ303", "Nairobi", "Dubai", 7500)
        };
        for (Flight f : flights) {
            flightTableModel.addRow(new Object[]{f.getFlightNumber(), f.getDepartureLocation(), f.getArrivalLocation(), f.getPrice()});
        }

        // Seat map table
        int numRows = 6;
        int numCols = 4;
        String[] seatColumns = {"A", "B", "C", "D"};
        String[][] seatData = new String[numRows][numCols];

        // Fill seat numbers (e.g., 1A, 1B, ...)
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                seatData[row][col] = (row + 1) + seatColumns[col];
            }
        }

        seatMapTableModel = new DefaultTableModel(seatData, seatColumns) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        seatMapTable = new JTable(seatMapTableModel);

        // Enable single cell selection
        seatMapTable.setCellSelectionEnabled(true);
        seatMapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Optional: Custom renderer to color booked seats
        seatMapRenderer = new SeatMapCellRenderer();
        seatMapTable.setDefaultRenderer(Object.class, seatMapRenderer);
        seatMapTable.setRowHeight(30);

        JScrollPane seatScroll = new JScrollPane(seatMapTable);
        userPanel.add(seatScroll, BorderLayout.EAST);

        // Selected seat field
        selectedSeatField = new JTextField(5);
        selectedSeatField.setEditable(false);
        userPanel.add(selectedSeatField, BorderLayout.SOUTH);

        // Seat selection listener
        seatMapTable.getSelectionModel().addListSelectionListener(e -> {
            int row = seatMapTable.getSelectedRow();
            int col = seatMapTable.getSelectedColumn();
            if (!e.getValueIsAdjusting() && row >= 0 && col >= 0) {
                String seatId = (row + 1) + seatColumns[col];
                if (bookedSeats == null || !bookedSeats.contains(seatId)) {
                    selectedSeatField.setText(seatId);
                } else {
                    selectedSeatField.setText("");
                }
            }
        });

        JButton userBack = new JButton("Back");
        userBack.addActionListener(_ -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        userPanel.add(userBack, BorderLayout.WEST);

        // Book & Pay button
        bookAndPayButton = new JButton("Book & Pay");
        userPanel.add(bookAndPayButton, BorderLayout.AFTER_LAST_LINE);

        bookAndPayButton.addActionListener(e -> {
            if (currentlySelectedFlight == null || selectedSeatField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a flight and seat.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PaymentDialog dialog = new PaymentDialog(this, currentlySelectedFlight.getPrice());
            dialog.setVisible(true);
            if (dialog.isPaymentConfirmed()) {
                String paymentMethod = dialog.getPaymentMethodChoice();
                String cardNumber = dialog.getCardNumber();
                String expiry = dialog.getExpiryDate();
                String cvv = dialog.getCvv();
                seatMapRenderer.setBookedSeats(bookedSeats);
                seatMapTable.repaint();
                // Optionally, mark the seat as booked
                String seatId = selectedSeatField.getText();
                currentlySelectedFlight.bookSeat(seatId);
                bookedSeats = currentlySelectedFlight.getBookedSeats();
                seatMapRenderer.setBookedSeats(bookedSeats);
                seatMapTable.repaint();
            }
        });

        // Admin Panel
        adminPanel = new JPanel(new BorderLayout());
        JTabbedPane adminTabs = new JTabbedPane();

        // Airlines tab
        JPanel airlinePanel = new JPanel(new BorderLayout());
        airlineTableModel = new DefaultTableModel(new Object[]{"Code", "Name"}, 0);
        airlineTable = new JTable(airlineTableModel);
        airlinePanel.add(new JScrollPane(airlineTable), BorderLayout.CENTER);

        JPanel airlineForm = new JPanel(new FlowLayout());
        JTextField airlineCodeField = new JTextField(5);
        JTextField airlineNameField = new JTextField(10);
        JButton addAirlineBtn = new JButton("Add Airline");
        airlineForm.add(new JLabel("Code:"));
        airlineForm.add(airlineCodeField);
        airlineForm.add(new JLabel("Name:"));
        airlineForm.add(airlineNameField);
        airlineForm.add(addAirlineBtn);
        airlinePanel.add(airlineForm, BorderLayout.SOUTH);

        addAirlineBtn.addActionListener(_-> {
            String code = airlineCodeField.getText().trim();
            String name = airlineNameField.getText().trim();
            if (!code.isEmpty() && !name.isEmpty()) {
                airlines.add(new Airline(name, code));
                airlineTableModel.addRow(new Object[]{code, name});
                airlineCodeField.setText("");
                airlineNameField.setText("");
            }
        });

        adminTabs.addTab("Airlines", airlinePanel);

        // Airports tab
        JPanel airportPanel = new JPanel(new BorderLayout());
        airportTableModel = new DefaultTableModel(new Object[]{"Code", "Name", "City", "Country", "IATA Code"}, 0);
        airportTable = new JTable(airportTableModel);
        airportPanel.add(new JScrollPane(airportTable), BorderLayout.CENTER);

        JPanel airportForm = new JPanel(new FlowLayout());
        JTextField airportCodeField = new JTextField(5);
        JTextField airportNameField = new JTextField(10);
        JTextField airportCityField = new JTextField(10);
        JTextField airportCountryField = new JTextField(10);
        JTextField airportIataField = new JTextField(5);
        JButton addAirportBtn = new JButton("Add Airport");
        airportForm.add(new JLabel("Code:"));
        airportForm.add(airportCodeField);
        airportForm.add(new JLabel("Name:"));
        airportForm.add(airportNameField);
        airportForm.add(new JLabel("City:"));
        airportForm.add(airportCityField);
        airportForm.add(new JLabel("Country:"));
        airportForm.add(airportCountryField);
        airportForm.add(new JLabel("IATA Code:"));
        airportForm.add(airportIataField);
        airportForm.add(addAirportBtn);
        airportPanel.add(airportForm, BorderLayout.SOUTH);

        addAirportBtn.addActionListener(_ -> {
            String code = airportCodeField.getText().trim();
            String name = airportNameField.getText().trim();
            String city = airportCityField.getText().trim();
            String country = airportCountryField.getText().trim();
            String iata = airportIataField.getText().trim();
            if (!code.isEmpty() && !name.isEmpty() && !city.isEmpty() && !country.isEmpty() && !iata.isEmpty()) {
                airports.add(new Airport(code, name, city, country, iata));
                airportTableModel.addRow(new Object[]{code, name, city, country, iata});
                airportCodeField.setText("");
                airportNameField.setText("");
                airportCityField.setText("");
                airportCountryField.setText("");
                airportIataField.setText("");
                // Optionally, save to DB using AirportDAO
            }
        });

        JButton removeAirportBtn = new JButton("Remove Selected Airport");
        airportPanel.add(removeAirportBtn, BorderLayout.NORTH);

        removeAirportBtn.addActionListener(_ -> {
            int selectedRow = airportTable.getSelectedRow();
            if (selectedRow >= 0) {
                airports.remove(selectedRow);
                airportTableModel.removeRow(selectedRow);
                // Optionally, remove from DB using AirportDAO
            }
        });

        adminTabs.addTab("Airports", airportPanel);

        // --- Bookings tab ---
        JPanel bookingPanel = new JPanel(new BorderLayout());

        // Table to display bookings
        DefaultTableModel bookingTableModel = new DefaultTableModel(
            new Object[]{"Booking ID", "User", "Flight", "Seat", "Time", "Price", "Status"}, 0);
        JTable bookingTable = new JTable(bookingTableModel);
        JScrollPane bookingScroll = new JScrollPane(bookingTable);
        bookingPanel.add(bookingScroll, BorderLayout.CENTER);

        // Buttons for admin actions
        JPanel bookingBtnPanel = new JPanel();
        JButton createBookingBtn = new JButton("Create Booking");
        JButton editBookingBtn = new JButton("Edit Booking");
        JButton cancelBookingBtn = new JButton("Cancel Booking");
        bookingBtnPanel.add(createBookingBtn);
        bookingBtnPanel.add(editBookingBtn);
        bookingBtnPanel.add(cancelBookingBtn);
        bookingPanel.add(bookingBtnPanel, BorderLayout.SOUTH);

        createBookingBtn.addActionListener(e -> {
            // Show a dialog to enter booking details (user, flight, seat, etc.)
            // For simplicity, you can use JOptionPane or a custom JDialog
            // On confirmation, create a new Booking and add to the table/database
            JOptionPane.showMessageDialog(this, "Show booking creation dialog here.");
        });

        editBookingBtn.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Load booking details, show editable dialog, update booking in table/database
                JOptionPane.showMessageDialog(this, "Show booking edit dialog here.");
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to edit.");
            }
        });

        cancelBookingBtn.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Confirm cancellation
                int confirm = JOptionPane.showConfirmDialog(this, "Cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Remove from table and database
                    bookingTableModel.removeRow(selectedRow);
                    // Also update/cancel in your BookingDAO/database as needed
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a booking to cancel.");
            }
        });

        // Add the bookings tab to the admin tabs
        adminTabs.addTab("Bookings", bookingPanel);

        adminPanel.add(adminTabs, BorderLayout.CENTER);

        JButton adminBack = new JButton("Back");
        adminBack.addActionListener(_ -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        adminPanel.add(adminBack, BorderLayout.SOUTH);

        // Add panels to mainPanel
        mainPanel.add(roleSelectionPanel, "ROLE_SELECTION");
        mainPanel.add(userPanel, "USER_PANEL");
        mainPanel.add(adminPanel, "ADMIN_PANEL");
    }

    // --- Payment Dialog ---
    static class PaymentDialog extends JDialog {
        private String paymentMethodChoice = "Cash";
        private JTextField cardNumberField = new JTextField();
        private JTextField expiryDateField = new JTextField();
        private JTextField cvvField = new JTextField();
        private boolean paymentConfirmed = false;

        public PaymentDialog(JFrame parent, double amount) {
            super(parent, "Payment", true);
            setLayout(new GridLayout(6, 2, 10, 10));
            setSize(350, 250);
            setLocationRelativeTo(parent);

            ButtonGroup group = new ButtonGroup();
            JRadioButton cashRadio = new JRadioButton("Cash", true);
            JRadioButton cardRadio = new JRadioButton("Card");
            group.add(cashRadio);
            group.add(cardRadio);

            add(new JLabel("Amount:"));
            add(new JLabel("Ksh " + amount));
            add(new JLabel("Payment Method:"));
            JPanel radioPanel = new JPanel();
            radioPanel.add(cashRadio);
            radioPanel.add(cardRadio);
            add(radioPanel);

            add(new JLabel("Card Number:"));
            add(cardNumberField);
            add(new JLabel("Expiry (MM/YY):"));
            add(expiryDateField);
            add(new JLabel("CVV:"));
            add(cvvField);

            cardNumberField.setEnabled(false);
            expiryDateField.setEnabled(false);
            cvvField.setEnabled(false);

            cashRadio.addActionListener(e -> {
                paymentMethodChoice = "Cash";
                cardNumberField.setEnabled(false);
                expiryDateField.setEnabled(false);
                cvvField.setEnabled(false);
            });
            cardRadio.addActionListener(e -> {
                paymentMethodChoice = "Card";
                cardNumberField.setEnabled(true);
                expiryDateField.setEnabled(true);
                cvvField.setEnabled(true);
            });

            JButton payBtn = new JButton("Pay");
            JButton cancelBtn = new JButton("Cancel");
            add(cancelBtn);
            add(payBtn);

            payBtn.addActionListener(e -> {
                if (paymentMethodChoice.equals("Card")) {
                    if (cardNumberField.getText().trim().isEmpty() ||
                        expiryDateField.getText().trim().isEmpty() ||
                        cvvField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all card details.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                paymentConfirmed = true;
                dispose();
            });

            cancelBtn.addActionListener(e -> {
                paymentConfirmed = false;
                dispose();
            });
        }

        public String getPaymentMethodChoice() { return paymentMethodChoice; }
        public String getCardNumber() { return cardNumberField.getText(); }
        public String getExpiryDate() { return expiryDateField.getText(); }
        public String getCvv() { return cvvField.getText(); }
        public boolean isPaymentConfirmed() { return paymentConfirmed; }
    }
    public static void main(String[] args) {
        // Database connection test
        try (java.sql.Connection conn = database.DBHelper.getConnection()) {
            System.out.println("Database connection successful!");
        } catch (Exception ex) {
            System.out.println("Database connection failed: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            new PlaneBookingSystemGUI().setVisible(true);
        });
    }
}
