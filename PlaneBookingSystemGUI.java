import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

// Interfaces
interface IdentifiableEntity {
    String getID();
}

interface AirlineInfo {
    String getAirlineDetails();
}

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

interface AircraftManager {
    void addAircraft(Aircraft aircraft);
    List<Aircraft> getAllAircraft();
    void displayAllAircraft(OutputWriter writer);
}

interface AirportManager {
    void addAirport(Airport airport);
    List<Airport> getAllAirports();
    void displayAllAirports(OutputWriter writer);
}

interface FlightManager {
    List<Flight> getAvailableFlights();
    void displayAvailableFlights(OutputWriter writer);
    Flight selectFlight(InputReader reader, OutputWriter writer);
    boolean bookSeat(Flight flight, String seatId, OutputWriter writer);
}

interface PaymentProcessor {
    void processPayment(Booking booking, InputReader reader, OutputWriter writer);
}

// --- New Classes for User details (Passport and Visa) ---
class Passport {
    private String passportNumber;

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    @Override
    public String toString() {
        return passportNumber;
    }
}

class Visa {
    private String visaNumber;

    public Visa(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    @Override
    public String toString() {
        return visaNumber;
    }
}

// --- 2. Concrete Implementations (Low-level Modules) ---

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
    private String dob; // Date of Birth as String (YYYY-MM-DD)
    private String nationality;
    private Passport passport;
    private Visa visa;

    // Constructor for GUI/basic interaction (without full document details)
    public User(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber); // phoneNumber here is the contact number
        this.dob = null; // These will be null unless explicitly set via another constructor or setter
        this.nationality = null;
        this.passport = null;
        this.visa = null;
    }

    // Constructor for loading from database or detailed input
    public User(String name, String dob, String nationality, Passport passport, Visa visa, String email) {
        super(name, email, null); // phoneNumber is null, assume email is unique identifier for this constructor
        this.dob = dob;
        this.nationality = nationality;
        this.passport = passport;
        this.visa = visa;
        // If phone number is also part of DB user, it needs to be passed here too.
        // For simplicity, sticking to the email as primary contact in the super call.
    }

    public String getDOB() {
        return dob;
    }

    public String getNationality() {
        return nationality;
    }

    public String getVisaNumber() {
        return (visa != null) ? visa.getVisaNumber() : null;
    }

    public String getPassportNumber() {
        return (passport != null) ? passport.getPassportNumber() : null;
    }

    @Override
    public String getRoleDescription() { return "Passenger"; }
}

class Aircraft implements IdentifiableEntity {
    private String registrationNumber, model, manufacturer;
    private int seatingCapacity;
    private double maxTakeoffWeight, range;
    private int yearOfManufacture;

    public Aircraft(String regNum, String model, String manf, int cap, double mtow, double rng, int year) {
        this.registrationNumber = regNum;
        this.model = model;
        this.manufacturer = manf;
        this.seatingCapacity = cap;
        this.maxTakeoffWeight = mtow;
        this.range = rng;
        this.yearOfManufacture = year;
    }

    @Override
    public String getID() { return registrationNumber; }
    public double getRange() { return range; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getSeatingCapacity() { return seatingCapacity; }

    @Override
    public String toString() {
        return String.format("Aircraft: %s %s (%s)\n   Capacity: %d, Range: %.1f km, Max Takeoff Weight: %.1f kg, Year: %d",
                manufacturer, model, registrationNumber, seatingCapacity, range, maxTakeoffWeight, yearOfManufacture);
    }
}

class Airlines implements AirlineInfo {
    private String airlineName, airlineCode, headquarters, contactNumber, website;

    public Airlines(String name, String code, String hq, String contact, String web) {
        this.airlineName = name;
        this.airlineCode = code;
        this.headquarters = hq;
        this.contactNumber = contact;
        this.website = web;
    }

    @Override
    public String getAirlineDetails() {
        return "Airline Name: " + airlineName + "\nAirline Code: " + airlineCode + "\nHeadquarters: " + headquarters +
                "\nContact: " + contactNumber + "\nWebsite: " + website;
    }
}

class Airport implements IdentifiableEntity {
    private String name, iataCode, icaoCode, city, country;
    private int numberOfTerminals, numberOfRunways;
    private double latitude, longitude;

    public Airport(String name, String iata, String icao, String city, String country, int term, int runs, double lat, double lon) {
        this.name = name;
        this.iataCode = iata;
        this.icaoCode = icao;
        this.city = city;
        this.country = country;
        this.numberOfTerminals = term;
        this.numberOfRunways = runs;
        this.latitude = lat;
        this.longitude = lon;
    }

    @Override
    public String getID() { return iataCode; }
    public String getName() { return name; }
    public String getIataCode() { return iataCode; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public int getNumberOfTerminals() { return numberOfTerminals; }
    public int getNumberOfRunways() { return numberOfRunways; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return String.format("Airport: %s (%s/%s)\n   Location: %s, %s\n   Terminals: %d, Runways: %d\n   Coordinates: Lat %.4f, Lon %.4f",
                name, iataCode, icaoCode, city, country, numberOfTerminals, numberOfRunways, latitude, longitude);
    }
}

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

    public void displayBookingDetails(OutputWriter writer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        writer.println("\nBooking Confirmation");
        writer.println("Booking ID: " + bookingId);
        writer.println("Passenger: " + passenger.getName() + " (" + passenger.getRoleDescription() + ")");
        writer.println("Email: " + passenger.getEmail());
        writer.println("Phone: " + passenger.getPhoneNumber()); // This is the contact phone from Person
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

class Flight implements IdentifiableEntity {
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

    @Override
    public String getID() { return flightNumber; }

    public void displayInfo(OutputWriter writer) {
        writer.printf("Flight: %s from %s to %s - Price: $%.2f%n", flightNumber, departureLocation, arrivalLocation, price);
    }

    public void displayAvailableSeats(OutputWriter writer) {
        writer.println("Available Seats for Flight " + flightNumber + ":");
        for (int r = 1; r <= MAX_ROWS; r++) {
            StringBuilder rowSeats = new StringBuilder();
            for (char l : SEAT_LETTERS) {
                rowSeats.append((bookedSeats.contains(r + "" + l) ? "XX " : (r + "" + l + " ")));
            }
            writer.println(rowSeats.toString());
        }
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

    public boolean bookSeat(String seatId) {
        if (!isSeatValid(seatId)) {
            return false;
        }
        if (bookedSeats.contains(seatId)) {
            return false;
        }
        bookedSeats.add(seatId);
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
        return String.format("Payment ID: %d, Amount: $%.2f, Method: %s, Date: %s", paymentId, amount, paymentMethod, paymentDate.toString());
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
            writer.printf("Payment of $%.2f using %s accepted for booking ID %d. Payment successful on: %s%n",
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
            writer.printf("Payment of $%.2f using Card (ending with %s) accepted for booking ID %d. Payment successful on: %s%n",
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

class GUITextAreaWriter implements OutputWriter {
    private JTextArea textArea;

    public GUITextAreaWriter(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void print(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message));
    }

    @Override
    public void println(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
    }

    @Override
    public void printf(String format, Object... args) {
        SwingUtilities.invokeLater(() -> textArea.append(String.format(format, args)));
    }
}

// --- 3. Service Layer (High-level Modules) ---

class SimpleAircraftManager implements AircraftManager {
    private List<Aircraft> aircraftFleet = new ArrayList<>();

    @Override
    public void addAircraft(Aircraft aircraft) {
        aircraftFleet.add(aircraft);
    }

    @Override
    public List<Aircraft> getAllAircraft() {
        return Collections.unmodifiableList(aircraftFleet);
    }

    @Override
    public void displayAllAircraft(OutputWriter writer) {
        writer.println("\nRegistered Aircraft Fleet");
        if (aircraftFleet.isEmpty()) {
            writer.println("No aircraft added yet.");
            return;
        }
        for (int i = 0; i < aircraftFleet.size(); i++) {
            writer.println("\nAircraft #" + (i + 1) + "\n" + aircraftFleet.get(i));
        }
        writer.println("-------------------------------");
    }
}

class SimpleAirportManager implements AirportManager {
    private List<Airport> airportNetwork = new ArrayList<>();

    @Override
    public void addAirport(Airport airport) {
        airportNetwork.add(airport);
    }

    @Override
    public List<Airport> getAllAirports() {
        return Collections.unmodifiableList(airportNetwork);
    }

    @Override
    public void displayAllAirports(OutputWriter writer) {
        writer.println("\nRegistered Airports");
        if (airportNetwork.isEmpty()) {
            writer.println("No airports added yet.");
            return;
        }
        for (int i = 0; i < airportNetwork.size(); i++) {
            writer.println("\nAirport #" + (i + 1) + "\n" + airportNetwork.get(i));
        }
    }
}

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
    public Flight selectFlight(InputReader reader, OutputWriter writer) {
        // This method is designed for console, but in GUI, the flight is selected directly from table.
        writer.println("This selectFlight method is not used directly in GUI interaction for selecting a flight.");
        return null;
    }

    @Override
    public boolean bookSeat(Flight flight, String seatId, OutputWriter writer) {
        return flight.bookSeat(seatId);
    }
}

class DefaultPaymentService {
    private static int nextPaymentId = 1;

    public void initiatePayment(Booking booking, InputReader reader, OutputWriter writer) {
        writer.println("\nPayment Initiation");
        // In GUI, this method is called after the PaymentDialog handles user input.
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
        super(parent, "Process Payment for $" + String.format("%.2f", amount), true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel amountLabel = new JLabel("Amount to Pay: $" + String.format("%.2f", amount));
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

    // --- Components for Main Menu / Role Selection ---
    private JPanel roleSelectionPanel;

    // --- Components for Admin Portal ---
    private JPanel adminPanel;
    private JTextField adminAirportNameField, adminAirportIataField, adminAirportIcaoField, adminAirportCityField, adminAirportCountryField;
    private JSpinner adminAirportTerminalsSpinner, adminAirportRunwaysSpinner;
    private JTextField adminAirportLatField, adminAirportLonField;
    private JTable airportTable;
    private DefaultTableModel airportTableModel;

    private JTextField adminAircraftRegField, adminAircraftModelField, adminAircraftManfField;
    private JSpinner adminAircraftCapacitySpinner, adminAircraftYearSpinner;
    private JTextField adminAircraftMTOWField, adminAircraftRangeField;
    private JTable aircraftTable;
    private DefaultTableModel aircraftTableModel;

    // --- Components for User Portal ---
    private JPanel userPanel;
    private JTextField userNameField, userEmailField, userPhoneField;
    private JTable flightTable;
    private DefaultTableModel flightTableModel;
    private JButton selectFlightButton, bookSeatButton;
    private JTable seatMapTable;
    private DefaultTableModel seatMapTableModel;
    private SeatTableCellRenderer seatMapRenderer;
    private JTextField selectedSeatField;

    private JTextArea outputDisplayArea;
    private GUITextAreaWriter guiOutputWriter;

    // --- Dependencies ---
    private InputReader inputReader;
    private AircraftManager aircraftManager;
    private AirportManager airportManager;
    private FlightManager flightManager;
    private DefaultPaymentService paymentService;

    private Flight currentlySelectedFlight = null;

    public PlaneBookingSystemGUI(InputReader inputReader, OutputWriter outputWriter,
        AircraftManager aircraftManager, AirportManager airportManager,FlightManager flightManager, DefaultPaymentService paymentService) 
        {
        this.inputReader = inputReader;
        this.guiOutputWriter = (GUITextAreaWriter) outputWriter;
        this.aircraftManager = aircraftManager;
        this.airportManager = airportManager;
        this.flightManager = flightManager;
        this.paymentService = paymentService;

        setTitle("Plane Booking System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
        addDummyData();
        refreshAdminTables();
        refreshFlightTable();
    }

    private void setupUI() {
        setupRoleSelectionPanel();
        setupAdminPanel();
        setupUserPanel();

        mainPanel.add(roleSelectionPanel, "ROLE_SELECTION");
        mainPanel.add(adminPanel, "ADMIN_PORTAL");
        mainPanel.add(userPanel, "USER_PORTAL");

        outputDisplayArea = new JTextArea(10, 80);
        outputDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputDisplayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Output"));

        add(mainPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

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

        JButton adminButton = new JButton("Admin Portal");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 18));
        adminButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        roleSelectionPanel.add(adminButton, gbc);

        JButton userButton = new JButton("User Portal");
        userButton.setFont(new Font("Arial", Font.PLAIN, 18));
        userButton.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 1;
        roleSelectionPanel.add(userButton, gbc);

        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "ADMIN_PORTAL"));
        userButton.addActionListener(e -> cardLayout.show(mainPanel, "USER_PORTAL"));
    }

    private void setupAdminPanel() {
        adminPanel = new JPanel(new BorderLayout(10, 10));

        JTabbedPane adminTabbedPane = new JTabbedPane();
        adminTabbedPane.addTab("Manage Airports", createAirportManagementPanel());
        adminTabbedPane.addTab("Manage Aircraft", createAircraftManagementPanel());

        adminPanel.add(adminTabbedPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Role Selection");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);
        adminPanel.add(backButtonPanel, BorderLayout.NORTH);
    }

    private JPanel createAirportManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Airport"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; adminAirportNameField = new JTextField(20); inputPanel.add(adminAirportNameField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("IATA Code:"), gbc);
        gbc.gridx = 1; adminAirportIataField = new JTextField(5); inputPanel.add(adminAirportIataField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("ICAO Code:"), gbc);
        gbc.gridx = 1; adminAirportIcaoField = new JTextField(5); inputPanel.add(adminAirportIcaoField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1; adminAirportCityField = new JTextField(15); inputPanel.add(adminAirportCityField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Country:"), gbc);
        gbc.gridx = 1; adminAirportCountryField = new JTextField(15); inputPanel.add(adminAirportCountryField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Terminals:"), gbc);
        gbc.gridx = 1; adminAirportTerminalsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1)); inputPanel.add(adminAirportTerminalsSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Runways:"), gbc);
        gbc.gridx = 1; adminAirportRunwaysSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); inputPanel.add(adminAirportRunwaysSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Latitude:"), gbc);
        gbc.gridx = 1; adminAirportLatField = new JTextField(10); inputPanel.add(adminAirportLatField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Longitude:"), gbc);
        gbc.gridx = 1; adminAirportLonField = new JTextField(10); inputPanel.add(adminAirportLonField, gbc);

        JButton addAirportButton = new JButton("Add Airport");
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        inputPanel.add(addAirportButton, gbc);

        addAirportButton.addActionListener(e -> addAirport());

        airportTableModel = new DefaultTableModel(new Object[]{"Name", "IATA", "City", "Country", "Terminals", "Runways"}, 0);
        airportTable = new JTable(airportTableModel);
        airportTable.setFillsViewportHeight(true);
        JScrollPane airportTableScrollPane = new JScrollPane(airportTable);
        airportTableScrollPane.setBorder(BorderFactory.createTitledBorder("Registered Airports"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, airportTableScrollPane);
        splitPane.setResizeWeight(0.3);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAircraftManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Aircraft"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; inputPanel.add(new JLabel("Registration No.:"), gbc);
        gbc.gridx = 1; adminAircraftRegField = new JTextField(15); inputPanel.add(adminAircraftRegField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1; adminAircraftModelField = new JTextField(15); inputPanel.add(adminAircraftModelField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Manufacturer:"), gbc);
        gbc.gridx = 1; adminAircraftManfField = new JTextField(15); inputPanel.add(adminAircraftManfField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1; adminAircraftCapacitySpinner = new JSpinner(new SpinnerNumberModel(100, 1, 800, 1)); inputPanel.add(adminAircraftCapacitySpinner, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Max Takeoff Weight (kg):"), gbc);
        gbc.gridx = 1; adminAircraftMTOWField = new JTextField(10); inputPanel.add(adminAircraftMTOWField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Range (km):"), gbc);
        gbc.gridx = 1; adminAircraftRangeField = new JTextField(10); inputPanel.add(adminAircraftRangeField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; inputPanel.add(new JLabel("Year of Manufacture:"), gbc);
        gbc.gridx = 1; adminAircraftYearSpinner = new JSpinner(new SpinnerNumberModel(2020, 1900, LocalDateTime.now().getYear(), 1)); inputPanel.add(adminAircraftYearSpinner, gbc);

        JButton addAircraftButton = new JButton("Add Aircraft");
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        inputPanel.add(addAircraftButton, gbc);

        addAircraftButton.addActionListener(e -> addAircraft());

        aircraftTableModel = new DefaultTableModel(new Object[]{"Registration No.", "Model", "Manufacturer", "Capacity", "Range"}, 0);
        aircraftTable = new JTable(aircraftTableModel);
        aircraftTable.setFillsViewportHeight(true);
        JScrollPane aircraftTableScrollPane = new JScrollPane(aircraftTable);
        aircraftTableScrollPane.setBorder(BorderFactory.createTitledBorder("Registered Aircraft"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, aircraftTableScrollPane);
        splitPane.setResizeWeight(0.3);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private void addAirport() {
        try {
            String name = adminAirportNameField.getText();
            String iata = adminAirportIataField.getText().toUpperCase();
            String icao = adminAirportIcaoField.getText().toUpperCase();
            String city = adminAirportCityField.getText();
            String country = adminAirportCountryField.getText();
            int terminals = (Integer) adminAirportTerminalsSpinner.getValue();
            int runways = (Integer) adminAirportRunwaysSpinner.getValue();
            double lat = Double.parseDouble(adminAirportLatField.getText());
            double lon = Double.parseDouble(adminAirportLonField.getText());

            if (name.isEmpty() || iata.isEmpty() || icao.isEmpty() || city.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all airport fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Airport newAirport = new Airport(name, iata, icao, city, country, terminals, runways, lat, lon);
            airportManager.addAirport(newAirport);
            refreshAdminTables();
            guiOutputWriter.println("Airport added: " + newAirport.getName());
            clearAirportFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format for latitude, longitude, terminals, or runways.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding airport: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAirportFields() {
        adminAirportNameField.setText("");
        adminAirportIataField.setText("");
        adminAirportIcaoField.setText("");
        adminAirportCityField.setText("");
        adminAirportCountryField.setText("");
        adminAirportTerminalsSpinner.setValue(1);
        adminAirportRunwaysSpinner.setValue(1);
        adminAirportLatField.setText("");
        adminAirportLonField.setText("");
    }

    private void addAircraft() {
        try {
            String regNum = adminAircraftRegField.getText().toUpperCase();
            String model = adminAircraftModelField.getText();
            String manf = adminAircraftManfField.getText();
            int capacity = (Integer) adminAircraftCapacitySpinner.getValue();
            double mtow = Double.parseDouble(adminAircraftMTOWField.getText());
            double range = Double.parseDouble(adminAircraftRangeField.getText());
            int year = (Integer) adminAircraftYearSpinner.getValue();

            if (regNum.isEmpty() || model.isEmpty() || manf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all aircraft fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Aircraft newAircraft = new Aircraft(regNum, model, manf, capacity, mtow, range, year);
            aircraftManager.addAircraft(newAircraft);
            refreshAdminTables();
            guiOutputWriter.println("Aircraft added: " + newAircraft.getModel() + " (" + newAircraft.getID() + ")");
            clearAircraftFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format for capacity, MTOW, or range.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding aircraft: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAircraftFields() {
        adminAircraftRegField.setText("");
        adminAircraftModelField.setText("");
        adminAircraftManfField.setText("");
        adminAircraftCapacitySpinner.setValue(100);
        adminAircraftMTOWField.setText("");
        adminAircraftRangeField.setText("");
        adminAircraftYearSpinner.setValue(LocalDateTime.now().getYear());
    }

    private void refreshAdminTables() {
        airportTableModel.setRowCount(0);
        aircraftTableModel.setRowCount(0);

        for (Airport airport : airportManager.getAllAirports()) {
            airportTableModel.addRow(new Object[]{
                    airport.getName(),
                    airport.getIataCode(),
                    airport.getCity(),
                    airport.getCountry(),
                    airport.getNumberOfTerminals(),
                    airport.getNumberOfRunways()
            });
        }

        for (Aircraft aircraft : aircraftManager.getAllAircraft()) {
            aircraftTableModel.addRow(new Object[]{
                    aircraft.getID(),
                    aircraft.getModel(),
                    aircraft.getManufacturer(),
                    aircraft.getSeatingCapacity(),
                    String.format("%.1f km", aircraft.getRange())
            });
        }
    }

    private void setupUserPanel() {
        userPanel = new JPanel(new BorderLayout(10, 10));

        JPanel userDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        userDetailsPanel.setBorder(BorderFactory.createTitledBorder("Passenger Details"));
        userNameField = new JTextField(15);
        userEmailField = new JTextField(15);
        userPhoneField = new JTextField(15);
        userDetailsPanel.add(new JLabel("Name:"));
        userDetailsPanel.add(userNameField);
        userDetailsPanel.add(new JLabel("Email:"));
        userDetailsPanel.add(userEmailField);
        userDetailsPanel.add(new JLabel("Phone:"));
        userDetailsPanel.add(userPhoneField);
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
                    guiOutputWriter.println("Selected Flight: " + currentlySelectedFlight.getFlightNumber());
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
                guiOutputWriter.println("You have confirmed selection of Flight: " + currentlySelectedFlight.getFlightNumber());
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

    private void refreshFlightTable() {
        flightTableModel.setRowCount(0);
        for (Flight flight : flightManager.getAvailableFlights()) {
            flightTableModel.addRow(new Object[]{
                    flight.getFlightNumber(),
                    flight.getDepartureLocation(),
                    flight.getArrivalLocation(),
                    String.format("$%.2f", flight.getPrice())
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

        // Create a basic User object for the booking flow
        User passenger = new User(userName, userEmail, userPhone);

        Booking booking = new Booking(passenger, currentlySelectedFlight, seatId);

        PaymentDialog paymentDialog = new PaymentDialog(this, booking.getTotalPrice());
        paymentDialog.setVisible(true);

        if (paymentDialog.isPaymentConfirmed()) {
            PaymentProcessor processor = null;
            if (paymentDialog.getPaymentMethodChoice().equals("Cash")) {
                processor = paymentService.createCashPayment(booking.getTotalPrice());
            } else if (paymentDialog.getPaymentMethodChoice().equals("Card")) {
                String cardNumber = paymentDialog.getCardNumber();
                String expiryDate = paymentDialog.getExpiryDate();
                String cvv = paymentDialog.getCvv();
                processor = paymentService.createCardPayment(booking.getTotalPrice(), cardNumber, expiryDate, cvv);
            }

            if (processor != null) {
                processor.processPayment(booking, inputReader, guiOutputWriter);
                if (booking.getPaymentStatus().equals("Paid")) {
                    if (flightManager.bookSeat(currentlySelectedFlight, seatId, guiOutputWriter)) {
                        booking.displayBookingDetails(guiOutputWriter);
                        JOptionPane.showMessageDialog(this, "Booking successful for Flight " + currentlySelectedFlight.getFlightNumber() + ", Seat " + seatId + "!", "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
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
                        guiOutputWriter.println("Payment processed but seat booking failed. Refund might be required.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid payment method selected.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            guiOutputWriter.println("Payment cancelled by user for booking ID " + booking.getBookingId());
            JOptionPane.showMessageDialog(this, "Booking cancelled.", "Booking Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addDummyData() {
        airportManager.addAirport(new Airport("Jomo Kenyatta International Airport", "NBO", "HKJK", "Nairobi", "Kenya", 2, 2, -1.3192, 36.9278));
        airportManager.addAirport(new Airport("Dubai International Airport", "DXB", "OMDB", "Dubai", "UAE", 3, 2, 25.2532, 55.3657));
        airportManager.addAirport(new Airport("Heathrow Airport", "LHR", "EGLL", "London", "UK", 5, 2, 51.4700, -0.4543));
        airportManager.addAirport(new Airport("John F. Kennedy International Airport", "JFK", "KJFK", "New York", "USA", 6, 4, 40.6413, -73.7781));

        aircraftManager.addAircraft(new Aircraft("5Y-KDN", "Boeing 787 Dreamliner", "Boeing", 280, 254000, 15000, 2018));
        aircraftManager.addAircraft(new Aircraft("A6-EFA", "Airbus A380", "Airbus", 550, 575000, 15200, 2010));
        aircraftManager.addAircraft(new Aircraft("G-GBCL", "Boeing 737-800", "Boeing", 189, 79000, 5700, 2015));
        aircraftManager.addAircraft(new Aircraft("N123AA", "Airbus A320", "Airbus", 150, 78000, 6100, 2019));

        List<Flight> initialFlights = new ArrayList<>();
        initialFlights.add(new Flight("KQ100", "Nairobi", "Dubai", 450.00));
        initialFlights.add(new Flight("EK201", "Dubai", "London", 700.00));
        initialFlights.add(new Flight("BA249", "London", "Nairobi", 600.00));
        initialFlights.add(new Flight("DL400", "New York", "London", 850.00));
        this.flightManager = new SimpleFlightManager(initialFlights);
        flightManager.bookSeat(initialFlights.get(0), "1A", guiOutputWriter);
        flightManager.bookSeat(initialFlights.get(0), "1B", guiOutputWriter);
        flightManager.bookSeat(initialFlights.get(1), "5C", guiOutputWriter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JTextArea sharedOutputArea = new JTextArea();
            GUITextAreaWriter guiWriter = new GUITextAreaWriter(sharedOutputArea);
            GUITextFieldReader dummyReader = new GUITextFieldReader(new JTextField());

            AircraftManager aircraftMgr = new SimpleAircraftManager();
            AirportManager airportMgr = new SimpleAirportManager();
            FlightManager flightMgr = null;

            DefaultPaymentService paymentSvc = new DefaultPaymentService();

            PlaneBookingSystemGUI app = new PlaneBookingSystemGUI(dummyReader, guiWriter,
                    aircraftMgr, airportMgr, flightMgr, paymentSvc);
            app.setVisible(true);
        });
    }
}
