import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
<<<<<<< HEAD
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date; // Still using java.sql.Date as in your original code
=======
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
<<<<<<< HEAD

// --- 1. Interfaces for Abstraction (ISP & DIP) ---

// S - Single Responsibility: Defines common identifiable entities.
interface IdentifiableEntity {
    String getID();
}

// S - Single Responsibility: Defines how to get airline specific details.
interface AirlineInfo {
    String getAirlineDetails();
}

// S - Single Responsibility: Defines common person details.
=======
// import java.time.LocalDate; // Not needed after User simplification

// --- 1. Interfaces for Abstraction (ISP & DIP) ---

// Keeping core interfaces for good design principles
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
interface PersonDetails {
    String getName();
    String getEmail();
    String getPhoneNumber();
    String getRoleDescription();
}

<<<<<<< HEAD
// S - Single Responsibility: Defines an interface for handling user input.
// NOTE: For GUI, this interface becomes less about blocking prompts and more about providing
// a mechanism to 'get' input after user interaction, or for mocking during tests.
// The GUI will often directly read from Swing components.
interface InputReader {
    String readLine(String prompt); // In GUI, this might be a JOptionPane or just a direct field read
    int readInt(String prompt);
    double readDouble(String prompt);
    void close(); // Less relevant for GUI
}

// S - Single Responsibility: Defines an interface for handling output.
=======
interface InputReader {
    String readLine(String prompt);
    int readInt(String prompt);
    double readDouble(String prompt);
    void close();
}

>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
interface OutputWriter {
    void print(String message);
    void println(String message);
    void printf(String format, Object... args);
}

<<<<<<< HEAD
// S - Single Responsibility: Defines a service for managing aircraft.
// O - Open/Closed: New aircraft types can be handled by implementations.
interface AircraftManager {
    void addAircraft(Aircraft aircraft);
    List<Aircraft> getAllAircraft();
    void displayAllAircraft(OutputWriter writer);
}

// S - Single Responsibility: Defines a service for managing airports.
// O - Open/Closed: New airport types can be handled by implementations.
interface AirportManager {
    void addAirport(Airport airport);
    List<Airport> getAllAirports();
    void displayAllAirports(OutputWriter writer);
}

// S - Single Responsibility: Defines a service for managing flights.
interface FlightManager {
    List<Flight> getAvailableFlights();
    void displayAvailableFlights(OutputWriter writer);
    Flight selectFlight(InputReader reader, OutputWriter writer); // This method will be adapted for GUI selection
    boolean bookSeat(Flight flight, String seatId, OutputWriter writer);
}

// S - Single Responsibility: Defines an interface for payment processing.
// O - Open/Closed: New payment methods can be added by implementing this.
// L - Liskov Substitution: Any PaymentProcessor can be used interchangeably.
=======
interface FlightManager {
    List<Flight> getAvailableFlights();
    void displayAvailableFlights(OutputWriter writer); // Can be used for console logs
    boolean bookSeat(Flight flight, String seatId, OutputWriter writer);
}

>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
interface PaymentProcessor {
    void processPayment(Booking booking, InputReader reader, OutputWriter writer);
}

// --- 2. Concrete Implementations (Low-level Modules) ---

<<<<<<< HEAD
// S - Single Responsibility: Focuses on Person attributes.
// L - Liskov Substitution: User will extend this correctly.
=======
// Simplified Person and User classes
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
// S - Single Responsibility: Represents a user/passenger.
class User extends Person {
    public User(String name,String dobString,String phoneNumber, Passport passport, Visa visa,String email) {
=======
class User extends Person {
    // Removed dob, nationality, Passport, Visa for simplification
    public User(String name, String email, String phoneNumber) {
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        super(name, email, phoneNumber);
    }

    @Override
    public String getRoleDescription() { return "Passenger"; }
<<<<<<< HEAD

    public String getVisaNumber() {
        return this.getVisaNumber();
    }

    public String getDOB() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDOB'");
    }

    public String getNationality() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNationality'");
    }
}

// S - Single Responsibility: Represents an aircraft's data.
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
    public String getID() { return registrationNumber; } // Implementing IdentifiableEntity
    public double getRange() { return range; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getSeatingCapacity() { return seatingCapacity; }

    @Override
    public String toString() {
        return String.format("Aircraft: %s %s (%s)\n  Capacity: %d, Range: %.1f km, Max Takeoff Weight: %.1f kg, Year: %d",
                manufacturer, model, registrationNumber, seatingCapacity, range, maxTakeoffWeight, yearOfManufacture);
    }

    public Object getYearOfManufacture() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getYearOfManufacture'");
    }
}

// S - Single Responsibility: Represents an airline's data.
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

// S - Single Responsibility: Represents an airport's data.
class Airport implements IdentifiableEntity {
    private String name, iataCode, icaoCode, city, country;
    private int numberOfTerminals, numberOfRunways;
    private double latitude, longitude;
    private List<String> flightNumbersServed = new ArrayList<>();

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
    public String getID() { return iataCode; } // IATA code as ID for airports
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
        return String.format("Airport: %s (%s/%s)\n  Location: %s, %s\n  Terminals: %d, Runways: %d\n  Coordinates: Lat %.4f, Lon %.4f",
                name, iataCode, icaoCode, city, country, numberOfTerminals, numberOfRunways, latitude, longitude);
    }
}

// S - Single Responsibility: Represents a flight booking.
=======
}

// Removed Aircraft, Airlines, Airport classes as Admin Portal is removed.

>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
        this.totalPrice = flight.getPrice(); // Price comes from the flight
=======
        this.totalPrice = flight.getPrice();
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        this.bookingTime = LocalDateTime.now();
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

<<<<<<< HEAD
// S - Single Responsibility: Represents a flight with seat management.
class Flight implements IdentifiableEntity {
=======
class Flight { // Removed IdentifiableEntity as it's no longer an interface after simplification
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
    @Override
    public String getID() { return flightNumber; }

=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    public void displayInfo(OutputWriter writer) {
        writer.printf("Flight: %s from %s to %s - Price: $%.2f%n", flightNumber, departureLocation, arrivalLocation, price);
    }

<<<<<<< HEAD
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

=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
    public boolean bookSeat(String seatId) {
        if (!isSeatValid(seatId)) {
            // In GUI, we'll use JOptionPane or update a JLabel instead of System.out
            // System.out.println("Error: Invalid seat format or out of bounds.");
            return false;
        }
        if (bookedSeats.contains(seatId)) {
            // System.out.println("Error: Seat " + seatId + " is already booked.");
            return false;
        }
        bookedSeats.add(seatId);
        // System.out.println("Seat " + seatId + " booked successfully for flight " + flightNumber + ".");
=======
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
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        return true;
    }

    public String getFlightNumber() { return flightNumber; }
    public double getPrice() { return price; }
    public String getArrivalLocation() { return arrivalLocation; }
    public String getDepartureLocation() { return departureLocation; }
<<<<<<< HEAD
    public Set<String> getBookedSeats() { return Collections.unmodifiableSet(bookedSeats); } // Return unmodifiable set
=======
    public Set<String> getBookedSeats() { return Collections.unmodifiableSet(bookedSeats); }
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    public static int getMaxRows() { return MAX_ROWS; }
    public static char[] getSeatLetters() { return SEAT_LETTERS; }

    @Override
    public String toString() {
        return flightNumber + " (" + departureLocation + " -> " + arrivalLocation + ")";
    }
}

<<<<<<< HEAD
// S - Single Responsibility: Abstract base for payment processing.
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
// S - Single Responsibility: Handles cash payments.
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
// S - Single Responsibility: Handles card payments.
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
        // Extended validation for card details
        return super.validatePaymentDetails() &&
                cardNumber != null && Pattern.matches("\d{16}", cardNumber) &&
                expiryDate != null && Pattern.matches("\d{2}/\d{2}", expiryDate) &&
                cvv != null && Pattern.matches("\d{3}", cvv);
=======
        return super.validatePaymentDetails() &&
                cardNumber != null && Pattern.matches("\\d{16}", cardNumber) &&
                expiryDate != null && Pattern.matches("\\d{2}/\\d{2}", expiryDate) &&
                cvv != null && Pattern.matches("\\d{3}", cvv);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
            writer.println("Card details provided: " + (cardNumber != null ? "Card#: " + cardNumber.replaceAll("\d(?=\d{4})", "*") : "N/A") +
=======
            writer.println("Card details provided: " + (cardNumber != null ? "Card#: " + cardNumber.replaceAll("\\d(?=\\d{4})", "*") : "N/A") +
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
                    ", Expiry: " + expiryDate + ", CVV: " + (cvv != null ? "***" : "N/A"));
            booking.setPaymentStatus("Failed");
        }
    }
}

<<<<<<< HEAD
// GUI-specific InputReader (simplified, as GUI uses direct component access more)
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
class GUITextFieldReader implements InputReader {
    private JTextField textField;

    public GUITextFieldReader(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public String readLine(String prompt) {
<<<<<<< HEAD
        // In a real GUI, this would typically involve a modal dialog or pre-filled field.
        // For simplicity, we'll just return the current text field value.
        // This makes it unsuitable for sequential prompts unless managed externally.
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        return textField.getText();
    }

    @Override
    public int readInt(String prompt) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
<<<<<<< HEAD
            return -1; // Indicate error
=======
            return -1;
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        }
    }

    @Override
    public double readDouble(String prompt) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
<<<<<<< HEAD
            return -1.0; // Indicate error
=======
            return -1.0;
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        }
    }

    @Override
    public void close() {
        // Not applicable for this simple GUI reader
    }
}

<<<<<<< HEAD
// GUI-specific OutputWriter that writes to a JTextArea
class GUITextAreaWriter implements OutputWriter {
    private JTextArea textArea;

    public GUITextAreaWriter(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void print(String message) {
        SwingUtilities.invokeLater(() -> textArea.append(message));
=======
class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void print(String message) {
        System.out.print(message);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    }

    @Override
    public void println(String message) {
<<<<<<< HEAD
        SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
=======
        System.out.println(message);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    }

    @Override
    public void printf(String format, Object... args) {
<<<<<<< HEAD
        SwingUtilities.invokeLater(() -> textArea.append(String.format(format, args)));
=======
        System.out.printf(format, args);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    }
}

// --- 3. Service Layer (High-level Modules) ---

<<<<<<< HEAD
// S - Single Responsibility: Manages aircraft data and operations.
class SimpleAircraftManager implements AircraftManager {
    private List<Aircraft> aircraftFleet = new ArrayList<>();

    @Override
    public void addAircraft(Aircraft aircraft) {
        aircraftFleet.add(aircraft);
    }

    @Override
    public List<Aircraft> getAllAircraft() {
        return Collections.unmodifiableList(aircraftFleet); // Return unmodifiable list for safety
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

// S - Single Responsibility: Manages airport data and operations.
class SimpleAirportManager implements AirportManager {
    private List<Airport> airportNetwork = new ArrayList<>();

    @Override
    public void addAirport(Airport airport) {
        airportNetwork.add(airport);
    }

    @Override
    public List<Airport> getAllAirports() {
        return Collections.unmodifiableList(airportNetwork); // Return unmodifiable list for safety
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

// S - Single Responsibility: Manages flight data and operations.
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
class SimpleFlightManager implements FlightManager {
    private List<Flight> availableFlights;

    public SimpleFlightManager(List<Flight> initialFlights) {
        this.availableFlights = new ArrayList<>(initialFlights);
    }

    @Override
    public List<Flight> getAvailableFlights() {
<<<<<<< HEAD
        return Collections.unmodifiableList(availableFlights); // Return unmodifiable list for safety
=======
        return Collections.unmodifiableList(availableFlights);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
        // In GUI context, this method needs to be handled differently.
        // The GUI will likely present a table/list and the selection happens via an event.
        // This method will now simply return null or be unused for direct selection.
        // The GUI will pass the selected Flight object directly.
        writer.println("This selectFlight method is not used directly in GUI interaction for selecting a flight.");
        return null;
=======
        return null; // Not used in GUI direct selection flow
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    }

    @Override
    public boolean bookSeat(Flight flight, String seatId, OutputWriter writer) {
<<<<<<< HEAD
        if (!flight.isSeatValid(seatId)) {
            writer.println("Error: Invalid seat format or seat out of bounds.");
            return false;
        }
        if (flight.bookSeat(seatId)) {
            writer.println("Seat " + seatId + " booked successfully for flight " + flight.getFlightNumber() + ".");
            return true;
        } else {
            writer.println("Error: Seat " + seatId + " is already booked for flight " + flight.getFlightNumber() + ".");
            return false;
        }
    }
}

// S - Single Responsibility: Orchestrates payment selection and processing.
class DefaultPaymentService {
    private static int nextPaymentId = 1; // Manages payment IDs

    // OCP: This method is open to new payment types without modification if they implement PaymentProcessor.
    // DIP: Depends on the PaymentProcessor interface.
    public void initiatePayment(Booking booking, InputReader reader, OutputWriter writer) {
        // This method needs to be GUI-aware, typically by showing a dialog.
        // The InputReader here is effectively used by the PaymentProcessor implementations.
        writer.println("\n--- Payment Initiation ---");
        // For GUI, the payment details are collected through a dialog outside this call
        // and passed in or handled by a GUI-specific PaymentProcessor setup.
        // The actual processPayment will then be called.
=======
        return flight.bookSeat(seatId, writer);
    }
}

class DefaultPaymentService {
    private static int nextPaymentId = 1;

    public void initiatePayment(Booking booking, InputReader reader, OutputWriter writer) {
        writer.println("\n--- Payment Initiation ---");
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    }

    public PaymentProcessor createCashPayment(double amount) {
        return new CashPayment(nextPaymentId++, amount, new Date(System.currentTimeMillis()));
    }

    public PaymentProcessor createCardPayment(double amount, String cardNum, String expiry, String cvv) {
        return new CardPayment(nextPaymentId++, amount, new Date(System.currentTimeMillis()), cardNum, expiry, cvv);
    }
}

<<<<<<< HEAD

// --- 4. Main Application GUI (High-level Orchestrator and View) ---

=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
        label.setHorizontalAlignment(CENTER);
=======
        label.setHorizontalAlignment(SwingConstants.CENTER);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        label.setOpaque(true);

        if (currentFlight == null) {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
            return label;
        }

<<<<<<< HEAD
        int seatRow = row + 1; // Table row 0 corresponds to seat row 1
=======
        int seatRow = row + 1;
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
            label.setBackground(label.getBackground().darker()); // Darken selected seat
=======
            label.setBackground(label.getBackground().darker());
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
        super(parent, "Process Payment for $" + String.format("%.2f", amount), true); // Modal dialog
=======
        super(parent, "Process Payment for $" + String.format("%.2f", amount), true);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
        cashRadio.setSelected(true); // Default selection
=======
        cashRadio.setSelected(true);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        paymentMethodChoice = "Cash";

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        radioPanel.add(new JLabel("Payment Method:"));
        radioPanel.add(cashRadio);
        radioPanel.add(cardRadio);
        gbc.gridy = 1;
        mainPanel.add(radioPanel, gbc);

<<<<<<< HEAD
        // Card details fields
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
        // Initially disable card fields if Cash is selected
=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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
<<<<<<< HEAD
            dispose(); // Close dialog
=======
            dispose();
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            paymentConfirmed = false;
<<<<<<< HEAD
            dispose(); // Close dialog
=======
            dispose();
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
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

<<<<<<< HEAD
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

=======
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    // --- Components for User Portal ---
    private JPanel userPanel;
    private JTextField userNameField, userEmailField, userPhoneField;
    private JTable flightTable;
    private DefaultTableModel flightTableModel;
    private JButton selectFlightButton, bookSeatButton;
    private JTable seatMapTable;
<<<<<<< HEAD
    private SeatTableCellRenderer seatMapRenderer;
    private JTextField selectedSeatField;

    private JTextArea outputDisplayArea;
    private GUITextAreaWriter guiOutputWriter;

    // --- Dependencies ---
    private InputReader inputReader; // Will use for specific cases or mock. Direct field access generally.
    private AircraftManager aircraftManager;
    private AirportManager airportManager;
    private FlightManager flightManager;
    private DefaultPaymentService paymentService;
=======
    private DefaultTableModel seatMapTableModel;
    private SeatTableCellRenderer seatMapRenderer;
    private JTextField selectedSeatField;

    // --- Dependencies ---
    private InputReader inputReader;
    private FlightManager flightManager;
    private DefaultPaymentService paymentService;
    private OutputWriter consoleOutputWriter; // For logging to console
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635

    private Flight currentlySelectedFlight = null;

    public PlaneBookingSystemGUI(InputReader inputReader, OutputWriter outputWriter,
<<<<<<< HEAD
                                 AircraftManager aircraftManager, AirportManager airportManager,
                                 FlightManager flightManager, DefaultPaymentService paymentService) {
        this.inputReader = inputReader; // Mostly for legacy API calls that expect it
        this.guiOutputWriter = (GUITextAreaWriter) outputWriter; // Cast to our GUI writer
        this.aircraftManager = aircraftManager;
        this.airportManager = airportManager;
=======
                                 FlightManager flightManager, DefaultPaymentService paymentService) {
        this.inputReader = inputReader;
        this.consoleOutputWriter = outputWriter; // Assign to the console writer
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        this.flightManager = flightManager;
        this.paymentService = paymentService;

        setTitle("Plane Booking System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
<<<<<<< HEAD
        setLocationRelativeTo(null); // Center the window

        setupUI();
        addDummyData(); // Add some initial data for testing
        refreshAdminTables(); // Populate admin tables with dummy data
        refreshFlightTable(); // Populate flight table with dummy data
    }

    private void setupUI() {
        // Main Panel with CardLayout
        setupRoleSelectionPanel();
        setupAdminPanel();
        setupUserPanel();

        mainPanel.add(roleSelectionPanel, "ROLE_SELECTION");
        mainPanel.add(adminPanel, "ADMIN_PORTAL");
        mainPanel.add(userPanel, "USER_PORTAL");

        // Output Console at the bottom
        outputDisplayArea = new JTextArea(10, 80);
        outputDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputDisplayArea);
        outputDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        guiOutputWriter = new GUITextAreaWriter(outputDisplayArea); // Re-initialize with the actual JTextArea

        add(mainPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        cardLayout.show(mainPanel, "ROLE_SELECTION"); // Show initial role selection
=======
        setLocationRelativeTo(null);

        setupUI();
        addDummyData(); // Add dummy flights only
        refreshFlightTable();
    }

    private void setupUI() {
        setupRoleSelectionPanel();
        setupUserPanel(); // Only user panel now

        mainPanel.add(roleSelectionPanel, "ROLE_SELECTION");
        mainPanel.add(userPanel, "USER_PORTAL");

        // The "System Output" JTextArea and JScrollPane are completely removed.
        // The mainPanel now takes up the entire center space of the JFrame.
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "ROLE_SELECTION");
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
    }

    private void setupRoleSelectionPanel() {
        roleSelectionPanel = new JPanel(new GridBagLayout());
<<<<<<< HEAD
        roleSelectionPanel.setBackground(new Color(230, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeLabel = new JLabel("Welcome to the Plane Booking System!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        roleSelectionPanel.add(welcomeLabel, gbc);

        JLabel promptLabel = new JLabel("Are you an Admin or a User?");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        roleSelectionPanel.add(promptLabel, gbc);

        JButton adminButton = new JButton("Admin Portal");
        styleButton(adminButton, new Color(70, 130, 180)); // Steel Blue
        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "ADMIN_PORTAL"));
        gbc.gridy = 2; gbc.gridwidth = 1; gbc.gridx = 0;
        roleSelectionPanel.add(adminButton, gbc);

        JButton userButton = new JButton("User Portal");
        styleButton(userButton, new Color(60, 179, 113)); // Medium Sea Green
        userButton.addActionListener(e -> cardLayout.show(mainPanel, "USER_PORTAL"));
        gbc.gridx = 1;
        roleSelectionPanel.add(userButton, gbc);

        JButton quitButton = new JButton("Quit Application");
        styleButton(quitButton, new Color(200, 50, 50)); // Red
        quitButton.addActionListener(e -> {
            guiOutputWriter.println("Thank you for using the Plane Booking System. Goodbye!");
            System.exit(0);
        });
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        roleSelectionPanel.add(quitButton, gbc);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.putClientProperty("JButton.buttonType", "roundRect"); // For some L&Fs
    }

    private void setupAdminPanel() {
        adminPanel = new JPanel(new BorderLayout(10, 10));
        adminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Admin Menu
        JPanel adminMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backToMainButton = new JButton("Back to Main Menu");
        backToMainButton.addActionListener(e -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        adminMenuPanel.add(backToMainButton);
        adminPanel.add(adminMenuPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Equal size initially

        // Left: Add Forms
        JPanel addFormsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        addFormsPanel.setBorder(BorderFactory.createTitledBorder("Add New Data"));

        // Add Airport Form
        JPanel addAirportForm = new JPanel(new GridBagLayout());
        addAirportForm.setBorder(BorderFactory.createTitledBorder("Add Airport"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; adminAirportNameField = new JTextField(15); addAirportForm.add(adminAirportNameField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("IATA Code:"), gbc);
        gbc.gridx = 1; adminAirportIataField = new JTextField(15); addAirportForm.add(adminAirportIataField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("ICAO Code:"), gbc);
        gbc.gridx = 1; adminAirportIcaoField = new JTextField(15); addAirportForm.add(adminAirportIcaoField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("City:"), gbc);
        gbc.gridx = 1; adminAirportCityField = new JTextField(15); addAirportForm.add(adminAirportCityField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("Country:"), gbc);
        gbc.gridx = 1; adminAirportCountryField = new JTextField(15); addAirportForm.add(adminAirportCountryField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("Terminals:"), gbc);
        gbc.gridx = 1; adminAirportTerminalsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1)); addAirportForm.add(adminAirportTerminalsSpinner, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("Runways:"), gbc);
        gbc.gridx = 1; adminAirportRunwaysSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); addAirportForm.add(adminAirportRunwaysSpinner, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("Latitude:"), gbc);
        gbc.gridx = 1; adminAirportLatField = new JTextField(15); addAirportForm.add(adminAirportLatField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAirportForm.add(new JLabel("Longitude:"), gbc);
        gbc.gridx = 1; adminAirportLonField = new JTextField(15); addAirportForm.add(adminAirportLonField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JButton addAirportButton = new JButton("Add Airport");
        addAirportButton.addActionListener(e -> addAirport());
        addAirportForm.add(addAirportButton, gbc);
        addFormsPanel.add(addAirportForm);

        // Add Aircraft Form
        JPanel addAircraftForm = new JPanel(new GridBagLayout());
        addAircraftForm.setBorder(BorderFactory.createTitledBorder("Add Aircraft"));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        row = 0;
        gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Registration No:"), gbc);
        gbc.gridx = 1; adminAircraftRegField = new JTextField(15); addAircraftForm.add(adminAircraftRegField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1; adminAircraftModelField = new JTextField(15); addAircraftForm.add(adminAircraftModelField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Manufacturer:"), gbc);
        gbc.gridx = 1; adminAircraftManfField = new JTextField(15); addAircraftForm.add(adminAircraftManfField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1; adminAircraftCapacitySpinner = new JSpinner(new SpinnerNumberModel(50, 10, 500, 10)); addAircraftForm.add(adminAircraftCapacitySpinner, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Max Takeoff Weight (kg):"), gbc);
        gbc.gridx = 1; adminAircraftMTOWField = new JTextField(15); addAircraftForm.add(adminAircraftMTOWField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Range (km):"), gbc);
        gbc.gridx = 1; adminAircraftRangeField = new JTextField(15); addAircraftForm.add(adminAircraftRangeField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; addAircraftForm.add(new JLabel("Year of Manf:"), gbc);
        gbc.gridx = 1; adminAircraftYearSpinner = new JSpinner(new SpinnerNumberModel(2020, 1950, 2025, 1)); addAircraftForm.add(adminAircraftYearSpinner, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JButton addAircraftButton = new JButton("Add Aircraft");
        addAircraftButton.addActionListener(e -> addAircraft());
        addAircraftForm.add(addAircraftButton, gbc);
        addFormsPanel.add(addAircraftForm);

        splitPane.setLeftComponent(new JScrollPane(addFormsPanel));

        // Right: Display Tables
        JPanel displayTablesPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        displayTablesPanel.setBorder(BorderFactory.createTitledBorder("View Data"));

        // Airport Table
        String[] airportColumnNames = {"Name", "IATA", "City", "Country", "Terminals", "Runways", "Lat", "Lon"};
        airportTableModel = new DefaultTableModel(airportColumnNames, 0);
        airportTable = new JTable(airportTableModel);
        displayTablesPanel.add(new JScrollPane(airportTable));

        // Aircraft Table
        String[] aircraftColumnNames = {"Registration", "Model", "Manufacturer", "Capacity", "MTOW", "Range", "Year"};
        aircraftTableModel = new DefaultTableModel(aircraftColumnNames, 0);
        aircraftTable = new JTable(aircraftTableModel);
        displayTablesPanel.add(new JScrollPane(aircraftTable));

        splitPane.setRightComponent(new JScrollPane(displayTablesPanel));

        adminPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void refreshAdminTables() {
        // Refresh Airport Table
        airportTableModel.setRowCount(0); // Clear existing data
        for (Airport airport : airportManager.getAllAirports()) {
            airportTableModel.addRow(new Object[]{
                    airport.getName(), airport.getIataCode(), airport.getCity(), airport.getCountry(),
                    airport.getNumberOfTerminals(), airport.getNumberOfRunways(),
                    airport.getLatitude(), airport.getLongitude()
            });
        }

        // Refresh Aircraft Table
        aircraftTableModel.setRowCount(0); // Clear existing data
        for (Aircraft aircraft : aircraftManager.getAllAircraft()) {
            aircraftTableModel.addRow(new Object[]{
                    aircraft.getID(), aircraft.getModel(), aircraft.getManufacturer(),
                    aircraft.getSeatingCapacity(), aircraft.getMaxTakeoffWeight(),
                    aircraft.getRange(), aircraft.getYearOfManufacture()
            });
        }
    }

    private void addAirport() {
        try {
            String name = adminAirportNameField.getText().trim();
            String iata = adminAirportIataField.getText().trim().toUpperCase();
            String icao = adminAirportIcaoField.getText().trim().toUpperCase();
            String city = adminAirportCityField.getText().trim();
            String country = adminAirportCountryField.getText().trim();
            int term = (int) adminAirportTerminalsSpinner.getValue();
            int runs = (int) adminAirportRunwaysSpinner.getValue();
            double lat = Double.parseDouble(adminAirportLatField.getText().trim());
            double lon = Double.parseDouble(adminAirportLonField.getText().trim());

            if (name.isEmpty() || iata.isEmpty() || icao.isEmpty() || city.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All text fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            airportManager.addAirport(new Airport(name, iata, icao, city, country, term, runs, lat, lon));
            guiOutputWriter.println("Airport '" + name + "' added successfully!");
            refreshAdminTables();
            // Clear fields
            adminAirportNameField.setText(""); adminAirportIataField.setText(""); adminAirportIcaoField.setText("");
            adminAirportCityField.setText(""); adminAirportCountryField.setText(""); adminAirportLatField.setText("");
            adminAirportLonField.setText("");
            adminAirportTerminalsSpinner.setValue(1); adminAirportRunwaysSpinner.setValue(1);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Lat/Lon.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred adding airport: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAircraft() {
        try {
            String reg = adminAircraftRegField.getText().trim().toUpperCase();
            String model = adminAircraftModelField.getText().trim();
            String manf = adminAircraftManfField.getText().trim();
            int cap = (int) adminAircraftCapacitySpinner.getValue();
            double mtow = Double.parseDouble(adminAircraftMTOWField.getText().trim());
            double rng = Double.parseDouble(adminAircraftRangeField.getText().trim());
            int year = (int) adminAircraftYearSpinner.getValue();

            if (reg.isEmpty() || model.isEmpty() || manf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Registration, Model, and Manufacturer fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            aircraftManager.addAircraft(new Aircraft(reg, model, manf, cap, mtow, rng, year));
            guiOutputWriter.println("Aircraft '" + manf + " " + model + " (" + reg + ")' added successfully!");
            refreshAdminTables();
            // Clear fields
            adminAircraftRegField.setText(""); adminAircraftModelField.setText(""); adminAircraftManfField.setText("");
            adminAircraftMTOWField.setText(""); adminAircraftRangeField.setText("");
            adminAircraftCapacitySpinner.setValue(50); adminAircraftYearSpinner.setValue(2020);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Max Takeoff Weight/Range.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred adding aircraft: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void setupUserPanel() {
        userPanel = new JPanel(new BorderLayout(10, 10));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // User Menu
        JPanel userMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backToMainButton = new JButton("Back to Main Menu");
        backToMainButton.addActionListener(e -> cardLayout.show(mainPanel, "ROLE_SELECTION"));
        userMenuPanel.add(backToMainButton);
        userPanel.add(userMenuPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Two columns for flight/seat selection

        // Left Side: Passenger Details & Flight Selection
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Book a Flight"));

        JPanel passengerDetailsPanel = new JPanel(new GridBagLayout());
        passengerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Your Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; passengerDetailsPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; userNameField = new JTextField(20); passengerDetailsPanel.add(userNameField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; passengerDetailsPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; userEmailField = new JTextField(20); passengerDetailsPanel.add(userEmailField, gbc);

        row++; gbc.gridx = 0; gbc.gridy = row; passengerDetailsPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; userPhoneField = new JTextField(20); passengerDetailsPanel.add(userPhoneField, gbc);

        leftPanel.add(passengerDetailsPanel, BorderLayout.NORTH);

        // Flight List
        JPanel flightListPanel = new JPanel(new BorderLayout());
        flightListPanel.setBorder(BorderFactory.createTitledBorder("Available Flights"));
        String[] flightColumnNames = {"Flight No.", "From", "To", "Price"};
        flightTableModel = new DefaultTableModel(flightColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Flights not editable
            }
        };
        flightTable = new JTable(flightTableModel);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one flight can be selected
        flightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && flightTable.getSelectedRow() != -1) {
                int selectedRow = flightTable.getSelectedRow();
                currentlySelectedFlight = flightManager.getAvailableFlights().get(selectedRow);
                guiOutputWriter.println("Selected Flight: " + currentlySelectedFlight.getFlightNumber());
                updateSeatMap();
            }
        });
        flightListPanel.add(new JScrollPane(flightTable), BorderLayout.CENTER);

        selectFlightButton = new JButton("Select This Flight");
        selectFlightButton.addActionListener(e -> {
            if (currentlySelectedFlight == null) {
                JOptionPane.showMessageDialog(this, "Please select a flight first.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            } else {
                guiOutputWriter.println("You have chosen flight: " + currentlySelectedFlight.getFlightNumber() + ".");
                // Optionally disable flight selection and enable seat booking
            }
        });
        flightListPanel.add(selectFlightButton, BorderLayout.SOUTH);
        leftPanel.add(flightListPanel, BorderLayout.CENTER);
        contentPanel.add(leftPanel);

        // Right Side: Seat Map & Booking
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Seat Selection"));

        // Seat Map Table
        JPanel seatMapPanel = new JPanel(new BorderLayout());
        seatMapPanel.setBorder(BorderFactory.createTitledBorder("Flight Seat Map (Green=Available, Red=Booked)"));
        String[] seatMapColHeaders = new String[Flight.getSeatLetters().length];
        for (int i = 0; i < Flight.getSeatLetters().length; i++) {
            seatMapColHeaders[i] = String.valueOf(Flight.getSeatLetters()[i]);
        }
        DefaultTableModel seatMapTableModel = new DefaultTableModel(seatMapColHeaders, Flight.getMaxRows()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Seat map is for display only
            }
        };
        seatMapTable = new JTable(seatMapTableModel);
        seatMapRenderer = new SeatTableCellRenderer();
        for (int i = 0; i < seatMapTable.getColumnCount(); i++) {
            seatMapTable.getColumnModel().getColumn(i).setCellRenderer(seatMapRenderer);
        }
        seatMapTable.setRowHeight(25);
        seatMapTable.getTableHeader().setReorderingAllowed(false);
        seatMapTable.getTableHeader().setResizingAllowed(false);
        seatMapPanel.add(new JScrollPane(seatMapTable), BorderLayout.CENTER);
        rightPanel.add(seatMapPanel, BorderLayout.CENTER);

        // Seat selection and book button
        JPanel bookActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bookActionPanel.add(new JLabel("Select Seat (e.g., 3C):"));
        selectedSeatField = new JTextField(5);
        bookActionPanel.add(selectedSeatField);

        bookSeatButton = new JButton("Book Seat & Pay");
        bookSeatButton.addActionListener(e -> bookFlightAndPay());
        bookActionPanel.add(bookSeatButton);
        rightPanel.add(bookActionPanel, BorderLayout.SOUTH);

        contentPanel.add(rightPanel);
        userPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void refreshFlightTable() {
        flightTableModel.setRowCount(0); // Clear existing data
=======
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

    private void refreshFlightTable() {
        flightTableModel.setRowCount(0);
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        for (Flight flight : flightManager.getAvailableFlights()) {
            flightTableModel.addRow(new Object[]{
                    flight.getFlightNumber(),
                    flight.getDepartureLocation(),
                    flight.getArrivalLocation(),
                    String.format("$%.2f", flight.getPrice())
            });
        }
<<<<<<< HEAD
    }

    private void updateSeatMap() {
        // Clear previous seat map display
        DefaultTableModel model = (DefaultTableModel) seatMapTable.getModel();
        for (int r = 0; r < Flight.getMaxRows(); r++) {
            for (int c = 0; c < Flight.getSeatLetters().length; c++) {
                model.setValueAt( (r+1) + "" + Flight.getSeatLetters()[c], r, c);
            }
        }
        seatMapRenderer.setFlight(currentlySelectedFlight); // Update the renderer with current flight's booked seats
        seatMapTable.repaint(); // Force repaint to apply new colors
    }


    private void bookFlightAndPay() {
=======
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
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
        if (currentlySelectedFlight == null) {
            JOptionPane.showMessageDialog(this, "Please select a flight first.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

<<<<<<< HEAD
        String name = userNameField.getText().trim();
        String email = userEmailField.getText().trim();
        String phone = userPhoneField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your full name, email, and phone number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        User currentUser = new User(name, name, phone, null, null, email);

        String selectedSeatId = selectedSeatField.getText().trim().toUpperCase();
        if (!currentlySelectedFlight.isSeatValid(selectedSeatId)) {
            JOptionPane.showMessageDialog(this, "Invalid seat ID. Please enter a valid seat (e.g., 3C).", "Seat Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Try to book the seat
        boolean seatBooked = flightManager.bookSeat(currentlySelectedFlight, selectedSeatId, guiOutputWriter);

        if (seatBooked) {
            Booking newBooking = new Booking(currentUser, currentlySelectedFlight, selectedSeatId);
            guiOutputWriter.println("Seat " + selectedSeatId + " successfully reserved for flight " + currentlySelectedFlight.getFlightNumber() + ".");

            // Initiate Payment using a dialog
            PaymentDialog paymentDialog = new PaymentDialog(this, newBooking.getTotalPrice());
            paymentDialog.setVisible(true); // This will block until dialog is closed

            if (paymentDialog.isPaymentConfirmed()) {
                PaymentProcessor processor;
                String method = paymentDialog.getPaymentMethodChoice();

                if ("Cash".equals(method)) {
                    processor = paymentService.createCashPayment(newBooking.getTotalPrice());
                } else { // Card
                    String cardNum = paymentDialog.getCardNumber();
                    String expiry = paymentDialog.getExpiryDate();
                    String cvv = paymentDialog.getCvv();
                    processor = paymentService.createCardPayment(newBooking.getTotalPrice(), cardNum, expiry, cvv);
                }

                // Process the payment
                processor.processPayment(newBooking, inputReader, guiOutputWriter); // inputReader is a dummy here

                if ("Paid".equals(newBooking.getPaymentStatus())) {
                    JOptionPane.showMessageDialog(this, "Payment successful! Your flight is booked.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    newBooking.displayBookingDetails(guiOutputWriter);
                    updateSeatMap(); // Refresh seat map to show new booked seat
=======
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
                processor.processPayment(booking, inputReader, consoleOutputWriter);
                if (booking.getPaymentStatus().equals("Paid")) {
                    if (flightManager.bookSeat(currentlySelectedFlight, seatId, consoleOutputWriter)) {
                        // Display booking details using JOptionPane for direct user feedback
                        JOptionPane.showMessageDialog(this,
                                "Booking successful!\n" +
                                "Booking ID: " + booking.getBookingId() + "\n" +
                                "Flight: " + currentlySelectedFlight.getFlightNumber() + "\n" +
                                "Seat: " + seatId + "\n" +
                                "Price: $" + String.format("%.2f", booking.getTotalPrice()),
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
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
<<<<<<< HEAD
                JOptionPane.showMessageDialog(this, "Payment cancelled. Booking is not finalized.", "Booking Cancelled", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            // Error message already printed by flightManager.bookSeat
            JOptionPane.showMessageDialog(this, "Failed to book seat " + selectedSeatId + ". It might be taken.", "Booking Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void addDummyData() {
        // Airlines (not directly used in current GUI flow, but good for completeness)
        Airlines airlines = new Airlines("Kenya Airways", "KQ", "Nairobi, Kenya", "+254711223344", "kenya-airways.com");

        // Airports
        airportManager.addAirport(new Airport("Jomo Kenyatta Intl", "NBO", "HKJK", "Nairobi", "Kenya", 2, 2, -1.3192, 36.9278));
        airportManager.addAirport(new Airport("Heathrow Airport", "LHR", "EGLL", "London", "UK", 5, 2, 51.4700, -0.4543));
        airportManager.addAirport(new Airport("Cape Town Intl", "CPT", "FACT", "Cape Town", "South Africa", 1, 2, -33.9691, 18.5996));
        airportManager.addAirport(new Airport("Bole Intl", "ADD", "HAAB", "Addis Ababa", "Ethiopia", 2, 1, 8.9779, 38.7993));

        // Aircraft
        aircraftManager.addAircraft(new Aircraft("5Y-KQM", "Boeing 787-8", "Boeing", 234, 250000.0, 15700.0, 2014));
        aircraftManager.addAircraft(new Aircraft("G-STBC", "Airbus A320", "Airbus", 180, 77000.0, 6100.0, 2018));
        aircraftManager.addAircraft(new Aircraft("ZS-SBD", "Boeing 737-800", "Boeing", 162, 79000.0, 5765.0, 2005));

        // Flights (already added in main, ensure consistency if changed)
        // new Flight("SA101", "Nairobi", "Cape Town", 120.50),
        // new Flight("KQ202", "Nairobi", "London", 900.00),
        // new Flight("ET303", "Nairobi", "Addis Ababa", 110.75)
    }

    public static void main(String[] args) {
        // --- Dependency Injection / Wiring up the application ---
        // For GUI, InputReader needs to be a dummy or adapted.
        // OutputWriter will be our custom GUI text area writer.
        InputReader dummyInputReader = new GUITextFieldReader(new JTextField()); // Dummy, as GUI handles direct input
        JTextArea initialOutputArea = new JTextArea(); // Temporary for initial guiOutputWriter
        GUITextAreaWriter guiOutputWriter = new GUITextAreaWriter(initialOutputArea);


        // Initializing managers with some dummy data
        List<Flight> initialFlights = new ArrayList<>(Arrays.asList(
                new Flight("SA101", "Nairobi", "Cape Town", 120.50),
                new Flight("KQ202", "Nairobi", "London", 900.00),
                new Flight("ET303", "Nairobi", "Addis Ababa", 110.75)
        ));

        AircraftManager aircraftManager = new SimpleAircraftManager();
        AirportManager airportManager = new SimpleAirportManager();
        FlightManager flightManager = new SimpleFlightManager(initialFlights);
        DefaultPaymentService paymentService = new DefaultPaymentService();

        // Inject all dependencies into the main application GUI class
        SwingUtilities.invokeLater(() -> {
            PlaneBookingSystemGUI app = new PlaneBookingSystemGUI(
                    dummyInputReader, guiOutputWriter,
                    aircraftManager, airportManager,
                    flightManager, paymentService
            );
            app.setVisible(true);
        });
    }
}
=======
                JOptionPane.showMessageDialog(this, "Invalid payment method selected.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            consoleOutputWriter.println("Payment cancelled by user for booking ID " + booking.getBookingId());
            JOptionPane.showMessageDialog(this, "Booking cancelled.", "Booking Status", JOptionPane.INFORMATION_MESSAGE);
        }
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
>>>>>>> 182b1fb5212b852bd925f787cac33b1939f4e635
