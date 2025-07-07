import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

// --- 1. Interfaces for Abstraction (ISP & DIP) ---

// Keeping core interfaces for good design principles
interface PersonDetails {
    String getName();
    String getEmail();
    String getPhoneNumber();
    String getRoleDescription();
}

interface InputReader {
    String readLine(String prompt);
    int readInt(String prompt);
    double readDouble(String prompt);
    void close();
}

interface OutputWriter {
    void print(String message);
    void println(String message);
    void printf(String format, Object... args);
}

interface FlightManager {
    List<Flight> getAvailableFlights();
    void displayAvailableFlights(OutputWriter writer); // Can be used for console logs
    boolean bookSeat(Flight flight, String seatId, OutputWriter writer);
}

interface PaymentProcessor {
    void processPayment(Booking booking, InputReader reader, OutputWriter writer);
}

// --- 2. Concrete Implementations (Low-level Modules) ---

// Simplified Person and User classes
abstract class Person implements PersonDetails {
    protected String name, email, phoneNumber;

    public Person(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getName() { return name; }
    @Override
    public String getEmail() { return email; }
    @Override
    public String getPhoneNumber() { return phoneNumber; }
    @Override
    public abstract String getRoleDescription();
}

class User extends Person {
    // Removed dob, nationality, Passport, Visa for simplification
    public User(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }

    @Override
    public String getRoleDescription() { return "Passenger"; }
}

// Removed Aircraft, Airlines, Airport classes as Admin Portal is removed.

class Booking {
    private static int idCounter = 1;
    private int bookingId;
    private User passenger;
    private String flightNumber, seatId;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private String bookingStatus = "Confirmed", paymentStatus = "Pending";

    public Booking(User passenger, Flight flight, String seatId) {
        this.bookingId = idCounter++;
        this.passenger = passenger;
        this.flightNumber = flight.getFlightNumber();
        this.seatId = seatId;
        this.totalPrice = flight.getPrice();
        this.bookingTime = LocalDateTime.now();
    }

    public String getBookingStatus(){
        return bookingStatus;
    }
   public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public String getBookingTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return bookingTime.format(formatter);
    }

    public Timestamp getBookingTimeAsTimestamp() {
        return Timestamp.valueOf(bookingTime);
    }

    public void displayBookingDetails(OutputWriter writer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        writer.println("\nBooking Confirmation");
        writer.println("Booking ID: " + bookingId);
        writer.println("Passenger: " + passenger.getName() + " (" + passenger.getRoleDescription() + ")");
        writer.println("Email: " + passenger.getEmail());
        writer.println("Phone: " + passenger.getPhoneNumber());
        writer.println("Flight Number: " + flightNumber);
        writer.println("Seat ID: " + seatId);
        writer.println("Booking Time: " + bookingTime.format(formatter));
        writer.printf("Total Price: $%.2f%n", totalPrice);
        writer.println("Booking Status: " + bookingStatus);
        writer.println("Payment Status: " + paymentStatus);
        writer.println("--------------------------");
    }

    public int getBookingId() { return bookingId; }
    public double getTotalPrice() { return totalPrice; }
    public void setPaymentStatus(String status) { this.paymentStatus = status; }
    public String getPaymentStatus() { return paymentStatus; }
}

class Flight { // Removed IdentifiableEntity as it's no longer an interface after simplification
    private String flightNumber, departureLocation, arrivalLocation;
    private double price;
    private Set<String> bookedSeats = new HashSet<>();
    private static final int MAX_ROWS = 14;
    private static final char[] SEAT_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F'};

    public Flight(String flightNum, String departureLoc, String arrivalLoc, double price) {
        this.flightNumber = flightNum;
        this.departureLocation = departureLoc;
        this.arrivalLocation = arrivalLoc;
        this.price = price;
    }

    public void displayInfo(OutputWriter writer) {
        writer.printf("Flight: %s from %s to %s - Price: $%.2f%n", flightNumber, departureLocation, arrivalLocation, price);
    }

    public boolean isSeatValid(String seatId) {
        if (seatId == null || seatId.length() < 2) return false;
        try {
            int r = Integer.parseInt(seatId.substring(0, seatId.length() - 1));
            char l = Character.toUpperCase(seatId.charAt(seatId.length() - 1));
            if (r < 1 || r > MAX_ROWS) return false;
            for (char sl : SEAT_LETTERS) if (sl == l) return true;
            return false;
        } catch (NumberFormatException e) { return false; }
    }

    public boolean bookSeat(String seatId, OutputWriter writer) {
        if (!isSeatValid(seatId)) {
            writer.println("Error: Invalid seat format or out of bounds.");
            return false;
        }
        if (bookedSeats.contains(seatId)) {
            writer.println("Error: Seat " + seatId + " is already booked.");
            return false;
        }
        bookedSeats.add(seatId);
        writer.println("Seat " + seatId + " booked successfully for flight " + flightNumber + ".");
        return true;
    }

    public String getFlightNumber() { return flightNumber; }
    public double getPrice() { return price; }
    public String getArrivalLocation() { return arrivalLocation; }
    public String getDepartureLocation() { return departureLocation; }
    public Set<String> getBookedSeats() { return Collections.unmodifiableSet(bookedSeats); }
    public static int getMaxRows() { return MAX_ROWS; }
    public static char[] getSeatLetters() { return SEAT_LETTERS; }

    @Override
    public String toString() {
        return flightNumber + " (" + departureLocation + " -> " + arrivalLocation + ")";
    }
}

abstract class AbstractPayment implements PaymentProcessor {
    protected int paymentId;
    protected double amount;
    protected String paymentMethod;
    protected Date paymentDate;

    public AbstractPayment(int pId, double amt, String method, Date date) {
        this.paymentId = pId;
        this.amount = amt;
        this.paymentMethod = method;
        this.paymentDate = date;
    }

    public double getAmount() { return amount; }

    protected boolean validatePaymentDetails() {
        return amount > 0 && paymentMethod != null && !paymentMethod.isEmpty() && paymentDate != null;
    }

    @Override
    public abstract void processPayment(Booking booking, InputReader reader, OutputWriter writer);

    @Override
    public String toString() {
        return String.format("Payment ID: %d, Amount: ksh, Method: %s, Date: %s", paymentId, amount, paymentMethod, paymentDate.toString());
    }
}

class CashPayment extends AbstractPayment {
    public CashPayment(int pId, double amt, Date date) {
        super(pId, amt, "Cash", date);
    }

    @Override
    public void processPayment(Booking booking, InputReader reader, OutputWriter writer) {
        writer.println("\nProcessing cash payment...");
        if (validatePaymentDetails()) {
            writer.printf("Payment of Ksh%.2f using %s accepted for booking ID %d. Payment successful on: %s%n",
                    amount, paymentMethod, booking.getBookingId(), paymentDate.toString());
            booking.setPaymentStatus("Paid");
        } else {
            writer.println("Cash payment validation failed for booking ID " + booking.getBookingId() + ".");
            booking.setPaymentStatus("Failed");
        }
    }
}

class CardPayment extends AbstractPayment {
    private String cardNumber, expiryDate, cvv;

    public CardPayment(int pId, double amt, Date date, String cNum, String exp, String cvv) {
        super(pId, amt, "Card", date);
        this.cardNumber = cNum;
        this.expiryDate = exp;
        this.cvv = cvv;
    }

    @Override
    protected boolean validatePaymentDetails() {
        return super.validatePaymentDetails() &&
                cardNumber != null && Pattern.matches("\\d{16}", cardNumber) &&
                expiryDate != null && Pattern.matches("\\d{2}/\\d{2}", expiryDate) &&
                cvv != null && Pattern.matches("\\d{3}", cvv);
    }

    @Override
    public void processPayment(Booking booking, InputReader reader, OutputWriter writer) {
        writer.println("\nProcessing card payment...");
        if (validatePaymentDetails()) {
            writer.printf("Payment of Ksh%.2f using Card (ending with %s) accepted for booking ID %d. Payment successful on: %s%n",
                    amount, cardNumber.substring(cardNumber.length() - 4), booking.getBookingId(), paymentDate.toString());
            booking.setPaymentStatus("Paid");
        } else {
            writer.println("Card payment validation failed for booking ID " + booking.getBookingId() + ".");
            writer.println("Card details provided: " + (cardNumber != null ? "Card#: " + cardNumber.replaceAll("\\d(?=\\d{4})", "*") : "N/A") +
                    ", Expiry: " + expiryDate + ", CVV: " + (cvv != null ? "***" : "N/A"));
            booking.setPaymentStatus("Failed");
        }
    }
}

class GUITextFieldReader implements InputReader {
    private JTextField textField;

    public GUITextFieldReader(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public String readLine(String prompt) {
        return textField.getText();
    }

    @Override
    public int readInt(String prompt) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public double readDouble(String prompt) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    @Override
    public void close() {
        // Not applicable for this simple GUI reader
    }
}

class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}

// --- 3. Service Layer (High-level Modules) ---

class SimpleFlightManager implements FlightManager {
    private List<Flight> availableFlights;

    public SimpleFlightManager(List<Flight> initialFlights) {
        this.availableFlights = new ArrayList<>(initialFlights);
    }

    @Override
    public List<Flight> getAvailableFlights() {
        return Collections.unmodifiableList(availableFlights);
    }

    @Override
    public void displayAvailableFlights(OutputWriter writer) {
        if (availableFlights.isEmpty()) {
            writer.println("\nSorry, no flights available.");
            return;
        }
        writer.println("\nAvailable Flights:");
        for (int i = 0; i < availableFlights.size(); i++) {
            writer.print((i + 1) + ". ");
            availableFlights.get(i).displayInfo(writer);
        }
    }


    @Override
    public boolean bookSeat(Flight flight, String seatId, OutputWriter writer) {
        return flight.bookSeat(seatId, writer);
    }
}

class DefaultPaymentService {
    private static int nextPaymentId = 1;

    public void initiatePayment(Booking booking, InputReader reader, OutputWriter writer) {
        writer.println("\n Payment Initiation");
    }

    public PaymentProcessor createCashPayment(double amount) {
        return new CashPayment(nextPaymentId++, amount, new Date(System.currentTimeMillis()));
    }

    public PaymentProcessor createCardPayment(double amount, String cardNum, String expiry, String cvv) {
        return new CardPayment(nextPaymentId++, amount, new Date(System.currentTimeMillis()), cardNum, expiry, cvv);
    }
}

// Custom Cell Renderer for JTable to color seats
class SeatTableCellRenderer extends DefaultTableCellRenderer {
    private Set<String> bookedSeats;
    private Flight currentFlight;

    public void setFlight(Flight flight) {
        this.currentFlight = flight;
        this.bookedSeats = (flight != null) ? flight.getBookedSeats() : Collections.emptySet();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);

        if (currentFlight == null) {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
            return label;
        }

        int seatRow = row + 1;
        char seatLetter = Flight.getSeatLetters()[column];
        String seatId = seatRow + "" + seatLetter;

        if (bookedSeats.contains(seatId)) {
            label.setBackground(Color.RED);
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(Color.GREEN);
            label.setForeground(Color.BLACK);
        }

        if (isSelected) {
            label.setBackground(label.getBackground().darker());
        }

        return label;
    }
}

// Payment Dialog for GUI
class PaymentDialog extends JDialog {
    private String paymentMethodChoice = "";
    private JTextField cardNumberField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private boolean paymentConfirmed = false;

    public PaymentDialog(JFrame parent, double amount) {
        super(parent, "Process Payment for Ksh" + String.format("%.2f", amount), true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel amountLabel = new JLabel("Amount to Pay: Ksh" + String.format("%.2f", amount));
        amountLabel.setFont(amountLabel.getFont().deriveFont(Font.BOLD, 14f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(amountLabel, gbc);

        JRadioButton cashRadio = new JRadioButton("Cash");
        JRadioButton cardRadio = new JRadioButton("Card");
        ButtonGroup group = new ButtonGroup();
        group.add(cashRadio);
        group.add(cardRadio);
        cashRadio.setSelected(true);
        paymentMethodChoice = "Cash";

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        radioPanel.add(new JLabel("Payment Method:"));
        radioPanel.add(cashRadio);
        radioPanel.add(cardRadio);
        gbc.gridy = 1;
        mainPanel.add(radioPanel, gbc);

        cardNumberField = new JTextField(16);
        expiryDateField = new JTextField(5);
        cvvField = new JTextField(3);

        JLabel cardNumLabel = new JLabel("Card Number (16 digits):");
        JLabel expiryLabel = new JLabel("Expiry (MM/YY):");
        JLabel cvvLabel = new JLabel("CVV (3 digits):");

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(cardNumLabel, gbc);
        gbc.gridx = 1; mainPanel.add(cardNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(expiryLabel, gbc);
        gbc.gridx = 1; mainPanel.add(expiryDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; mainPanel.add(cvvLabel, gbc);
        gbc.gridx = 1; mainPanel.add(cvvField, gbc);

        setCardFieldsEnabled(false);

        cashRadio.addActionListener(e -> {
            paymentMethodChoice = "Cash";
            setCardFieldsEnabled(false);
        });
        cardRadio.addActionListener(e -> {
            paymentMethodChoice = "Card";
            setCardFieldsEnabled(true);
        });

        JButton payButton = new JButton("Pay Now");
        payButton.addActionListener(e -> {
            paymentConfirmed = true;
            dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            paymentConfirmed = false;
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(payButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setCardFieldsEnabled(boolean enabled) {
        cardNumberField.setEnabled(enabled);
        expiryDateField.setEnabled(enabled);
        cvvField.setEnabled(enabled);
    }

    public String getPaymentMethodChoice() {
        return paymentMethodChoice;
    }

    public String getCardNumber() {
        return cardNumberField.getText();
    }

    public String getExpiryDate() {
        return expiryDateField.getText();
    }

    public String getCvv() {
        return cvvField.getText();
    }

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }
}


public class PlaneBookingSystemGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private DatabaseManager dbManager;

    //  Components for Main Menu
    private JPanel roleSelectionPanel;

    // Components for User Portal
    private JPanel userPanel;
    private JTextField userNameField, userEmailField, userPhoneField,userNationalityField,userDOBField,userPassporTextField,userVisaField;
    private JTable flightTable;
    private DefaultTableModel flightTableModel;
    private JButton selectFlightButton, bookSeatButton;
    private JTable seatMapTable;
    private DefaultTableModel seatMapTableModel;
    private SeatTableCellRenderer seatMapRenderer;
    private JTextField selectedSeatField;

    // Components for Admin Portal
    private JPanel adminPanel;
    private DefaultTableModel airlineTableModel, airportTableModel, planeTableModel, adminFlightTableModel;
    private JTable airlineTable, airportTable, planeTable, adminFlightTable;
    private List<Airline> airlines = new ArrayList<>();
    private List<Airport> airports = new ArrayList<>();
    private List<Plane> planes = new ArrayList<>();

    //  Dependencies 
    private InputReader inputReader;
    private FlightManager flightManager;
    private DefaultPaymentService paymentService;
    private OutputWriter consoleOutputWriter; // For logging to console

    private Flight currentlySelectedFlight = null;

    public PlaneBookingSystemGUI(InputReader inputReader, OutputWriter outputWriter,
                                 FlightManager flightManager, DefaultPaymentService paymentService) {
        this.inputReader = inputReader;
        this.consoleOutputWriter = outputWriter; // Assign to the console writer
        this.flightManager = flightManager;
        this.paymentService = paymentService;

        try {
    this.dbManager = new DatabaseManager();
    if (!dbManager.isConnected()) {
        JOptionPane.showMessageDialog(this, 
            "Connected to database but connection status is invalid", 
            "Database Warning", JOptionPane.WARNING_MESSAGE);
    }
    } catch (SQLException e) {
    JOptionPane.showMessageDialog(this, 
        "Database connection failed: " + e.getMessage(), 
        "Critical Error", JOptionPane.ERROR_MESSAGE);
    System.exit(1);
    }
        setTitle("Plane Booking System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
        addDummyData(); // Add dummy flights only
        refreshFlightTable();
    }

    private void setupUI() {
        setupRoleSelectionPanel();
        setupUserPanel();
        setupAdminPanel(); // <-- Add this line

        mainPanel.add(roleSelectionPanel, "ROLE_SELECTION");
        mainPanel.add(userPanel, "USER_PORTAL");
        mainPanel.add(adminPanel, "ADMIN_PORTAL"); // <-- Add this line

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "ROLE_SELECTION");
    }

    private void setupRoleSelectionPanel() {
        roleSelectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Welcome to Plane Booking System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridwidth = 2;
        roleSelectionPanel.add(titleLabel, gbc);

        JButton userButton = new JButton("Start Booking (User Portal)");
        userButton.setFont(new Font("Arial", Font.PLAIN, 18));
        userButton.setPreferredSize(new Dimension(250, 60)); // Make it larger and central
        gbc.gridx = 0; // Center it
        gbc.gridy = 1; // Align under title
        roleSelectionPanel.add(userButton, gbc);

        userButton.addActionListener(e -> cardLayout.show(mainPanel, "USER_PORTAL"));

        JButton adminButton = new JButton("Admin Portal");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 18));
        adminButton.setPreferredSize(new Dimension(250, 60));
        gbc.gridx = 0;
        gbc.gridy = 2;
        roleSelectionPanel.add(adminButton, gbc);
        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "ADMIN_PORTAL"));
    }

    private void setupUserPanel() {
        userPanel = new JPanel(new BorderLayout());

        JPanel userDetailsPanel = new JPanel(new GridBagLayout());
        userDetailsPanel.setBorder(BorderFactory.createTitledBorder("Passenger Details"));
          GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Add some padding
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Row 0
    gbc.gridx = 0; gbc.gridy = 0;
    userDetailsPanel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    userNameField = new JTextField(20);
    userDetailsPanel.add(userNameField, gbc);

    // Row 1
    gbc.gridx = 0; gbc.gridy = 1;
    userDetailsPanel.add(new JLabel("DOB (yyyy-mm-dd):"), gbc);
    gbc.gridx = 1;
    userDOBField = new JTextField(20);
    userDetailsPanel.add(userDOBField, gbc);

    // Row 2
    gbc.gridx = 0; gbc.gridy = 2;
    userDetailsPanel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    userEmailField = new JTextField(20);
    userDetailsPanel.add(userEmailField, gbc);

    // Row 3
    gbc.gridx = 0; gbc.gridy = 3;
    userDetailsPanel.add(new JLabel("Phone:"), gbc);
    gbc.gridx = 1;
    userPhoneField = new JTextField(20);
    userDetailsPanel.add(userPhoneField, gbc);

    // Row 4
    gbc.gridx = 0; gbc.gridy = 4;
    userDetailsPanel.add(new JLabel("Nationality:"), gbc);
    gbc.gridx = 1;
    userNationalityField = new JTextField(20);
    userDetailsPanel.add(userNationalityField, gbc);

    // Row 5
    gbc.gridx = 0; gbc.gridy = 5;
    userDetailsPanel.add(new JLabel("Passport:"), gbc);
    gbc.gridx = 1;
    userPassporTextField = new JTextField(20);
    userDetailsPanel.add(userPassporTextField, gbc);

    // Row 6
    gbc.gridx = 0; gbc.gridy = 6;
    userDetailsPanel.add(new JLabel("Visa:"), gbc);
    gbc.gridx = 1;
    userVisaField = new JTextField(20);
    userDetailsPanel.add(userVisaField, gbc);

    userPanel.add(userDetailsPanel, BorderLayout.NORTH);

        JPanel flightSelectionPanel = new JPanel(new BorderLayout());
        flightSelectionPanel.setBorder(BorderFactory.createTitledBorder("Available Flights"));
        flightTableModel = new DefaultTableModel(new Object[]{"Flight Number", "Departure", "Arrival", "Price"}, 0);
        flightTable = new JTable(flightTableModel);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightTable.setFillsViewportHeight(true);
        flightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = flightTable.getSelectedRow();
                if (selectedRow != -1) {
                    currentlySelectedFlight = flightManager.getAvailableFlights().get(selectedRow);
                    selectFlightButton.setEnabled(true);
                    updateSeatMap();
                } else {
                    currentlySelectedFlight = null;
                    selectFlightButton.setEnabled(false);
                    clearSeatMap();
                }
            }
        });
        JScrollPane flightTableScrollPane = new JScrollPane(flightTable);
        flightSelectionPanel.add(flightTableScrollPane, BorderLayout.CENTER);

        JPanel flightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        selectFlightButton = new JButton("Select This Flight");
        selectFlightButton.setEnabled(false);
        selectFlightButton.addActionListener(e -> {
            if (currentlySelectedFlight != null) {
                JOptionPane.showMessageDialog(this, "You have selected Flight: " + currentlySelectedFlight.getFlightNumber(), "Flight Selected", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        flightButtonsPanel.add(selectFlightButton);
        flightSelectionPanel.add(flightButtonsPanel, BorderLayout.SOUTH);

        JPanel seatMapPanel = new JPanel(new BorderLayout());
        seatMapPanel.setBorder(BorderFactory.createTitledBorder("Select Your Seat (Green: Available, Red: Booked)"));
        seatMapTableModel = new DefaultTableModel(Flight.getMaxRows(), Flight.getSeatLetters().length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        seatMapTable = new JTable(seatMapTableModel);
        seatMapTable.setRowHeight(30);
        seatMapTable.setCellSelectionEnabled(true);
        seatMapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        seatMapTable.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < Flight.getSeatLetters().length; i++) {
            seatMapTable.getColumnModel().getColumn(i).setHeaderValue(String.valueOf(Flight.getSeatLetters()[i]));
        }

        seatMapRenderer = new SeatTableCellRenderer();
        for (int i = 0; i < seatMapTable.getColumnModel().getColumnCount(); i++) {
            seatMapTable.getColumnModel().getColumn(i).setCellRenderer(seatMapRenderer);
        }

        seatMapTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && currentlySelectedFlight != null) {
                int selectedRow = seatMapTable.getSelectedRow();
                int selectedCol = seatMapTable.getSelectedColumn();
                if (selectedRow != -1 && selectedCol != -1) {
                    int seatRow = selectedRow + 1;
                    char seatLetter = Flight.getSeatLetters()[selectedCol];
                    String selectedSeat = seatRow + "" + seatLetter;
                    selectedSeatField.setText(selectedSeat);
                    bookSeatButton.setEnabled(true);
                } else {
                    selectedSeatField.setText("");
                    bookSeatButton.setEnabled(false);
                }
            }
        });

        JScrollPane seatMapScrollPane = new JScrollPane(seatMapTable);
        seatMapPanel.add(seatMapScrollPane, BorderLayout.CENTER);

        JPanel seatSelectionActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        selectedSeatField = new JTextField(8);
        selectedSeatField.setEditable(false);
        bookSeatButton = new JButton("Book Selected Seat");
        bookSeatButton.setEnabled(false);

        bookSeatButton.addActionListener(e -> bookSeat());

        seatSelectionActionsPanel.add(new JLabel("Selected Seat:"));
        seatSelectionActionsPanel.add(selectedSeatField);
        seatSelectionActionsPanel.add(bookSeatButton);
        seatMapPanel.add(seatSelectionActionsPanel, BorderLayout.SOUTH);

        JSplitPane userSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, flightSelectionPanel, seatMapPanel);
        userSplitPane.setResizeWeight(0.5);

        userPanel.add(userSplitPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Role Selection");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);
        userPanel.add(backButtonPanel, BorderLayout.SOUTH);
    }

    private void setupAdminPanel() {
        adminPanel = new JPanel(new BorderLayout(10, 10));
        JTabbedPane tabbedPane = new JTabbedPane();

        // Airlines Tab
        airlineTableModel = new DefaultTableModel(new Object[]{"Code", "Name"}, 0);
        airlineTable = new JTable(airlineTableModel);
        JPanel airlinePanel = new JPanel(new BorderLayout());
        airlinePanel.add(new JScrollPane(airlineTable), BorderLayout.CENTER);
        JButton addAirlineBtn = new JButton("Add Airline");
        addAirlineBtn.addActionListener(e -> {
            String code = JOptionPane.showInputDialog(this, "Enter Airline Code:");
            String name = JOptionPane.showInputDialog(this, "Enter Airline Name:");
            if (code != null && name != null) {
                airlines.add(new Airline(name, code));
                airlineTableModel.addRow(new Object[]{code, name});
            }
        });
        airlinePanel.add(addAirlineBtn, BorderLayout.SOUTH);
        tabbedPane.addTab("Airlines", airlinePanel);

        // Airports Tab
        airportTableModel = new DefaultTableModel(new Object[]{"Code", "Name", "City"}, 0);
        airportTable = new JTable(airportTableModel);
        JPanel airportPanel = new JPanel(new BorderLayout());
        airportPanel.add(new JScrollPane(airportTable), BorderLayout.CENTER);
        JButton addAirportBtn = new JButton("Add Airport");
        addAirportBtn.addActionListener(e -> {
            String code = JOptionPane.showInputDialog(this, "Enter Airport Code:");
            String name = JOptionPane.showInputDialog(this, "Enter Airport Name:");
            String city = JOptionPane.showInputDialog(this, "Enter City:");
            if (code != null && name != null && city != null) {
                airports.add(new Airport(name, code, city));
                airportTableModel.addRow(new Object[]{code, name, city});
            }
        });
        airportPanel.add(addAirportBtn, BorderLayout.SOUTH);
        tabbedPane.addTab("Airports", airportPanel);

        // Planes Tab
        planeTableModel = new DefaultTableModel(new Object[]{"Registration", "Model", "Capacity"}, 0);
        planeTable = new JTable(planeTableModel);
        JPanel planePanel = new JPanel(new BorderLayout());
        planePanel.add(new JScrollPane(planeTable), BorderLayout.CENTER);
        JButton addPlaneBtn = new JButton("Add Plane");
        addPlaneBtn.addActionListener(e -> {
            String reg = JOptionPane.showInputDialog(this, "Enter Registration:");
            String model = JOptionPane.showInputDialog(this, "Enter Model:");
            String capStr = JOptionPane.showInputDialog(this, "Enter Capacity:");
            try {
                int cap = Integer.parseInt(capStr);
                if (reg != null && model != null) {
                    planes.add(new Plane(model, reg, cap));
                    planeTableModel.addRow(new Object[]{reg, model, cap});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid capacity.");
            }
        });
        planePanel.add(addPlaneBtn, BorderLayout.SOUTH);
        tabbedPane.addTab("Planes", planePanel);

        // Flights Tab (Admin)
        adminFlightTableModel = new DefaultTableModel(new Object[]{"Flight Number", "Departure", "Arrival", "Price"}, 0);
        adminFlightTable = new JTable(adminFlightTableModel);
        JPanel flightPanel = new JPanel(new BorderLayout());
        flightPanel.add(new JScrollPane(adminFlightTable), BorderLayout.CENTER);
        JButton addFlightBtn = new JButton("Add Flight");
        addFlightBtn.addActionListener(e -> {
            String num = JOptionPane.showInputDialog(this, "Enter Flight Number:");
            String dep = JOptionPane.showInputDialog(this, "Enter Departure:");
            String arr = JOptionPane.showInputDialog(this, "Enter Arrival:");
            String priceStr = JOptionPane.showInputDialog(this, "Enter Price:");
            try {
                double price = Double.parseDouble(priceStr);
                if (num != null && dep != null && arr != null) {
                    Flight f = new Flight(num, dep, arr, price);
                    ((SimpleFlightManager)flightManager).getAvailableFlights().add(f);
                    adminFlightTableModel.addRow(new Object[]{num, dep, arr, "Ksh" + String.format("%.2f", price)});
                    refreshFlightTable();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid price.");
            }
        });
        flightPanel.add(addFlightBtn, BorderLayout.SOUTH);
        tabbedPane.addTab("Flights", flightPanel);

        adminPanel.add(tabbedPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Role Selection");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        adminPanel.add(backBtn, BorderLayout.SOUTH);
    }

    private void refreshFlightTable() {
        flightTableModel.setRowCount(0);
        for (Flight flight : flightManager.getAvailableFlights()) {
            flightTableModel.addRow(new Object[]{
                    flight.getFlightNumber(),
                    flight.getDepartureLocation(),
                    flight.getArrivalLocation(),
                   "Kes"+ String.format("$%.2f", flight.getPrice())
            });
        }
        currentlySelectedFlight = null;
        selectFlightButton.setEnabled(false);
        clearSeatMap();
    }

    private void updateSeatMap() {
        seatMapRenderer.setFlight(currentlySelectedFlight);
        seatMapTable.repaint();
        seatMapTableModel.setRowCount(0);
        seatMapTableModel.setColumnCount(Flight.getSeatLetters().length);
        for (int i = 0; i < Flight.getMaxRows(); i++) {
            Object[] rowData = new Object[Flight.getSeatLetters().length];
            for (int j = 0; j < Flight.getSeatLetters().length; j++) {
                rowData[j] = (i + 1) + "" + Flight.getSeatLetters()[j];
            }
            seatMapTableModel.addRow(rowData);
        }
    }

    private void clearSeatMap() {
        seatMapRenderer.setFlight(null);
        seatMapTable.repaint();
        seatMapTableModel.setRowCount(0);
        seatMapTableModel.setColumnCount(0);
        seatMapTableModel.setRowCount(Flight.getMaxRows());
        seatMapTableModel.setColumnCount(Flight.getSeatLetters().length);
        for (int i = 0; i < Flight.getSeatLetters().length; i++) {
            seatMapTable.getColumnModel().getColumn(i).setHeaderValue(String.valueOf(Flight.getSeatLetters()[i]));
        }
        for (int i = 0; i < Flight.getMaxRows(); i++) {
            Object[] rowData = new Object[Flight.getSeatLetters().length];
            for (int j = 0; j < Flight.getSeatLetters().length; j++) {
                rowData[j] = (i + 1) + "" + Flight.getSeatLetters()[j];
            }
            seatMapTableModel.addRow(rowData);
        }
        selectedSeatField.setText("");
        bookSeatButton.setEnabled(false);
    }

    private void bookSeat() {
        if (currentlySelectedFlight == null) {
            JOptionPane.showMessageDialog(this, "Please select a flight first.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userName = userNameField.getText();
        String userEmail = userEmailField.getText();
        String userPhone = userPhoneField.getText();

        if (userName.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name, email, and phone number.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String seatId = selectedSeatField.getText();
        if (seatId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a seat from the map.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!currentlySelectedFlight.isSeatValid(seatId)) {
            JOptionPane.showMessageDialog(this, "Invalid seat selected: " + seatId, "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentlySelectedFlight.getBookedSeats().contains(seatId)) {
            JOptionPane.showMessageDialog(this, "Seat " + seatId + " is already booked. Please choose another.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User passenger = new User(userName, userEmail, userPhone);

        Booking booking = new Booking(passenger, currentlySelectedFlight, seatId);

         Date dob;
        try {
            dob = Date.valueOf(userDOBField.getText());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid date format. Please use yyyy-MM-dd.", 
                "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PaymentDialog paymentDialog = new PaymentDialog(this, booking.getTotalPrice());
        paymentDialog.setVisible(true);

        if (paymentDialog.isPaymentConfirmed()) {
            //to save booking to database
             try {
                // Save user to database
                int userId = dbManager.addUser(
                    userNameField.getText(),
                    dob,
                    userNationalityField.getText(),
                    userPassporTextField.getText().isEmpty()? null :userPassporTextField.getText(),
                    userVisaField.getText().isEmpty()? null : userVisaField.getText(),
                    userEmailField.getText()
                );
                
                // Save booking to database
                int bookingId = dbManager.createBooking(
                    userId,
                    currentlySelectedFlight.getFlightNumber(),
                    seatId,
                    Timestamp.valueOf(booking.getBookingTime()),
                    booking.getTotalPrice(),
                    booking.getBookingStatus(),
                    booking.getPaymentStatus()
                );
                
                // Process payment
                PaymentProcessor processor = null;
                if (paymentDialog.getPaymentMethodChoice().equals("Cash")) {
                    processor = paymentService.createCashPayment(booking.getTotalPrice());
                    dbManager.recordPayment(
                        bookingId,
                        booking.getTotalPrice(),
                        "Cash",
                        new Timestamp(System.currentTimeMillis())
                    );
            
            
            
            
            
            
            
            
           
            
            } else if (paymentDialog.getPaymentMethodChoice().equals("Card")) {
                String cardNumber = paymentDialog.getCardNumber();
                String expiryDate = paymentDialog.getExpiryDate();
                String cvv = paymentDialog.getCvv();
                processor = paymentService.createCardPayment(booking.getTotalPrice(), cardNumber, expiryDate, cvv);
            };
            dbManager.recordPayment(bookingId,booking.getTotalPrice(),"card",new Timestamp(System.currentTimeMillis()));

            if (processor != null) {
                processor.processPayment(booking, inputReader, consoleOutputWriter);
                if (booking.getPaymentStatus().equals("Paid")) {
                    if (flightManager.bookSeat(currentlySelectedFlight, seatId, consoleOutputWriter)) {
                        // Display booking details using JOptionPane for direct user feedback
                        JOptionPane.showMessageDialog(this,
                                "Booking successful!\n" +
                                "Booking ID: " + booking.getBookingId() + "\n" +
                                "Flight: " + currentlySelectedFlight.getFlightNumber() + "\n" +
                                "Seat: " + seatId + "\n" +
                                "Price: Ksh" + String.format("%.2f", booking.getTotalPrice()),
                                "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
                        updateSeatMap();
                        refreshFlightTable();
                        userNameField.setText("");
                        userEmailField.setText("");
                        userPhoneField.setText("");
                        selectedSeatField.setText("");
                        bookSeatButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to book seat " + seatId + ". It might have just been taken.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
                        booking.setPaymentStatus("Refund Required");
                        consoleOutputWriter.println("Payment processed but seat booking failed. Refund might be required.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid payment method selected.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }}
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            consoleOutputWriter.println("Payment cancelled by user for booking ID " + booking.getBookingId());
            JOptionPane.showMessageDialog(this, "Booking cancelled.", "Booking Status", JOptionPane.INFORMATION_MESSAGE);
        }
    } 
    
    

     private List<Flight> loadFlightsFromDatabase() {
        List<Flight> flights = new ArrayList<>();
        try {
            ResultSet rs = dbManager.executeQuery("SELECT * FROM flight");
            while (rs.next()) {
                flights.add(new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("departureLocation"),
                    rs.getString("arrivalLocation"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load flights: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
        return flights;
    }

    private void addDummyData() {
        List<Flight> initialFlights = new ArrayList<>();
        initialFlights.add(new Flight("KQ100", "Nairobi", "Dubai", 450.00));
        initialFlights.add(new Flight("EK201", "Dubai", "London", 700.00));
        initialFlights.add(new Flight("BA249", "London", "Nairobi", 600.00));
        this.flightManager = new SimpleFlightManager(initialFlights);
        flightManager.bookSeat(initialFlights.get(0), "1A", consoleOutputWriter);
        flightManager.bookSeat(initialFlights.get(0), "1B", consoleOutputWriter);
        flightManager.bookSeat(initialFlights.get(1), "5C", consoleOutputWriter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OutputWriter consoleWriter = new ConsoleOutputWriter();
            GUITextFieldReader dummyReader = new GUITextFieldReader(new JTextField());

            FlightManager flightMgr = null;
            DefaultPaymentService paymentSvc = new DefaultPaymentService();

            PlaneBookingSystemGUI app = new PlaneBookingSystemGUI(dummyReader, consoleWriter,
                    flightMgr, paymentSvc);
            app.setVisible(true);
        });
    }
}

// --- Data Models for Admin Portal ---
class Airline {
    private String name, code;
    public Airline(String name, String code) { this.name = name; this.code = code; }
    public String getName() { return name; }
    public String getCode() { return code; }
    @Override public String toString() { return code + " - " + name; }
}

class Airport {
    private String name, code, city;
    public Airport(String name, String code, String city) { this.name = name; this.code = code; this.city = city; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getCity() { return city; }
    @Override public String toString() { return code + " - " + name + " (" + city + ")"; }
}

class Plane {
    private String model, registration;
    private int capacity;
    public Plane(String model, String registration, int capacity) {
        this.model = model; this.registration = registration; this.capacity = capacity;
    }
    public String getModel() { return model; }
    public String getRegistration() { return registration; }
    public int getCapacity() { return capacity; }
    @Override public String toString() { return registration + " - " + model + " (" + capacity + " seats)"; }
}
