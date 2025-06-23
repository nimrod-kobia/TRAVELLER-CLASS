import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern; // For better validation

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
interface PersonDetails {
    String getName();
    String getEmail();
    String getPhoneNumber();
    String getRoleDescription();
}

// S - Single Responsibility: Defines an interface for handling user input.
interface InputReader {
    String readLine(String prompt);
    int readInt(String prompt);
    double readDouble(String prompt);
    void close();
}

// S - Single Responsibility: Defines an interface for handling output.
interface OutputWriter {
    void print(String message);
    void println(String message);
    void printf(String format, Object... args);
}

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
    Flight selectFlight(InputReader reader, OutputWriter writer);
    boolean bookSeat(Flight flight, String seatId, OutputWriter writer);
}

// S - Single Responsibility: Defines an interface for payment processing.
// O - Open/Closed: New payment methods can be added by implementing this.
// L - Liskov Substitution: Any PaymentProcessor can be used interchangeably.
interface PaymentProcessor {
    void processPayment(Booking booking, InputReader reader, OutputWriter writer);
}

// --- 2. Concrete Implementations (Low-level Modules) ---

// S - Single Responsibility: Focuses on Person attributes.
// L - Liskov Substitution: User will extend this correctly.
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

// S - Single Responsibility: Represents a user/passenger.
class User extends Person {
    // Original constructor from your example, adjusted for Person's current fields.
    // Note: The `Passport` and `Visa` objects are not part of `Person` or `User` fields in the given code,
    // so this constructor needs adjustment or fields added to User if they are to be stored.
    // For now, assuming dob, nationality are not stored here.
    public User(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
    }

    @Override
    public String getRoleDescription() { return "Passenger"; }
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

    @Override
    public String toString() {
        return String.format("Airport: %s (%s/%s)\n  Location: %s, %s\n  Terminals: %d, Runways: %d\n  Coordinates: Lat %.4f, Lon %.4f",
                name, iataCode, icaoCode, city, country, numberOfTerminals, numberOfRunways, latitude, longitude);
    }
}

// S - Single Responsibility: Represents a flight booking.
class Booking {
    private static int idCounter = 1;
    private int bookingId;
    private User passenger;
    private String flightNumber, seatId; // Changed from flightId to flightNumber for consistency with Flight class
    private LocalDateTime bookingTime;
    private double totalPrice;
    private String bookingStatus = "Confirmed", paymentStatus = "Pending";

    // Re-evaluate this constructor based on the original intent.
    // The previous constructor had `Object passenger2` and `int seatId2`, which were problematic.
    // This assumes a user and a specific flight object are passed.
    public Booking(User passenger, Flight flight, String seatId) {
        this.bookingId = idCounter++;
        this.passenger = passenger;
        this.flightNumber = flight.getFlightNumber();
        this.seatId = seatId;
        this.totalPrice = flight.getPrice(); // Price comes from the flight
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
    public String getPaymentStatus() { return paymentStatus; } // Added getter for paymentStatus
}

// S - Single Responsibility: Represents a flight with seat management.
class Flight implements IdentifiableEntity {
    private String flightNumber, departureLocation, arrivalLocation; // Added departure location
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
            for (char l : SEAT_LETTERS) {
                writer.print((bookedSeats.contains(r + "" + l) ? "XX " : (r + "" + l + " ")));
            }
            writer.println("");
        }
    }

    public boolean isSeatValid(String seatId) {
        if (seatId == null || seatId.length() < 2) return false;
        try {
            int r = Integer.parseInt(seatId.substring(0, seatId.length() - 1));
            char l = seatId.charAt(seatId.length() - 1);
            if (r < 1 || r > MAX_ROWS) return false;
            for (char sl : SEAT_LETTERS) if (sl == l) return true;
            return false;
        } catch (NumberFormatException e) { return false; }
    }

    public boolean bookSeat(String seatId) {
        if (!isSeatValid(seatId)) {
            System.out.println("Error: Invalid seat format or out of bounds."); // Direct System.out here for simplicity in this method, but generally use OutputWriter
            return false;
        }
        if (bookedSeats.contains(seatId)) {
            System.out.println("Error: Seat " + seatId + " is already booked."); // Direct System.out here for simplicity in this method, but generally use OutputWriter
            return false;
        }
        bookedSeats.add(seatId);
        System.out.println("Seat " + seatId + " booked successfully for flight " + flightNumber + "."); // Direct System.out here for simplicity in this method, but generally use OutputWriter
        return true;
    }

    public String getFlightNumber() { return flightNumber; }
    public double getPrice() { return price; }
    public String getArrivalLocation() { return arrivalLocation; }
}

// S - Single Responsibility: Abstract base for payment processing.
abstract class AbstractPayment implements PaymentProcessor {
    protected int paymentId;
    protected double amount;
    protected String paymentMethod;
    protected Date paymentDate; // java.sql.Date is typically for DB, java.util.Date or java.time.LocalDate/Instant are more common for general use. Sticking with your original.

    public AbstractPayment(int pId, double amt, String method, Date date) {
        this.paymentId = pId;
        this.amount = amt;
        this.paymentMethod = method;
        this.paymentDate = date;
    }

    public double getAmount() { return amount; }

    // SRP: Validation logic belongs here or in a separate validator.
    // OCP: Subclasses can extend this validation or add their own.
    protected boolean validatePaymentDetails() {
        return amount > 0 && paymentMethod != null && !paymentMethod.isEmpty() && paymentDate != null;
    }

    // Abstract method to be implemented by concrete payment types.
    @Override
    public abstract void processPayment(Booking booking, InputReader reader, OutputWriter writer);

    @Override
    public String toString() {
        return String.format("Payment ID: %d, Amount: $%.2f, Method: %s, Date: %s", paymentId, amount, paymentMethod, paymentDate.toString());
    }
}

// S - Single Responsibility: Handles cash payments.
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

// S - Single Responsibility: Handles card payments.
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
        // Extended validation for card details
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
            booking.setPaymentStatus("Failed");
        }
    }
}

// S - Single Responsibility: Provides console-based input.
class ConsoleInputReader implements InputReader {
    private Scanner scanner;

    public ConsoleInputReader() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        return value;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
            System.out.print(prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        return value;
    }

    @Override
    public void close() {
        scanner.close();
    }
}

// S - Single Responsibility: Provides console-based output.
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
class SimpleFlightManager implements FlightManager {
    private List<Flight> availableFlights;

    public SimpleFlightManager(List<Flight> initialFlights) {
        this.availableFlights = new ArrayList<>(initialFlights);
    }

    @Override
    public List<Flight> getAvailableFlights() {
        return Collections.unmodifiableList(availableFlights); // Return unmodifiable list for safety
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
        Flight selectedFlight = null;
        while (selectedFlight == null) {
            String choiceStr = reader.readLine("\nSelect a flight by number (1-" + availableFlights.size() + "): ");
            try {
                int choice = Integer.parseInt(choiceStr);
                if (choice >= 1 && choice <= availableFlights.size()) {
                    selectedFlight = availableFlights.get(choice - 1);
                } else {
                    writer.println("Invalid flight selection.");
                }
            } catch (NumberFormatException e) {
                writer.println("Invalid input. Enter a number.");
            }
        }
        return selectedFlight;
    }

    @Override
    public boolean bookSeat(Flight flight, String seatId, OutputWriter writer) {
        // Flight class itself has the booking logic for a seat on that specific flight
        // We can optionally add more logic here related to the FlightManager's overview of all flights
        return flight.bookSeat(seatId);
    }
}

// S - Single Responsibility: Orchestrates payment selection and processing.
class DefaultPaymentService {
    private static int nextPaymentId = 1; // Manages payment IDs

    // OCP: This method is open to new payment types without modification if they implement PaymentProcessor.
    // DIP: Depends on the PaymentProcessor interface.
    public void initiatePayment(Booking booking, InputReader reader, OutputWriter writer) {
        writer.println("\n--- Payment ---");
        writer.print("Choose payment method: (1) Cash (2) Card: ");
        String paymentChoice = reader.readLine("");

        PaymentProcessor payment;
        if ("1".equals(paymentChoice)) {
            payment = new CashPayment(nextPaymentId++, booking.getTotalPrice(), new Date(System.currentTimeMillis()));
        } else if ("2".equals(paymentChoice)) {
            String cardNum = reader.readLine("Enter card number (16 digits): ");
            String expiry = reader.readLine("Enter expiry date (MM/YY): ");
            String cvv = reader.readLine("Enter CVV (3 digits): ");
            payment = new CardPayment(nextPaymentId++, booking.getTotalPrice(), new Date(System.currentTimeMillis()), cardNum, expiry, cvv);
        } else {
            writer.println("Invalid choice. Defaulting to Cash.");
            payment = new CashPayment(nextPaymentId++, booking.getTotalPrice(), new Date(System.currentTimeMillis()));
        }
        payment.processPayment(booking, reader, writer);
    }
}


// --- 4. Main Application (High-level Orchestrator) ---

// S - Single Responsibility: Orchestrates the overall application flow and interactions.
// D - Dependency Inversion: Depends on abstractions (interfaces) for its functionality.
public class PlaneBookingSystem {
    private InputReader inputReader;
    private OutputWriter outputWriter;
    private AircraftManager aircraftManager;
    private AirportManager airportManager;
    private FlightManager flightManager;
    private DefaultPaymentService paymentService; // Can also be an interface

    // DIP: Constructor injection for all dependencies.
    public PlaneBookingSystem(InputReader inputReader, OutputWriter outputWriter,
                              AircraftManager aircraftManager, AirportManager airportManager,
                              FlightManager flightManager, DefaultPaymentService paymentService) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.aircraftManager = aircraftManager;
        this.airportManager = airportManager;
        this.flightManager = flightManager;
        this.paymentService = paymentService;
    }

    public void run() {
        outputWriter.println(" Welcome to the Plane Booking System ");

        while (true) {
            outputWriter.print("\nAre you an Admin or a User? (Enter A or U, or Q to quit): ");
            String role = inputReader.readLine("").toUpperCase();

            if (role.equals("A")) {
                runAdminPortal();
            } else if (role.equals("U")) {
                runUserPortal();
            } else if (role.equals("Q")) {
                outputWriter.println("Thank you for using the Plane Booking System. Goodbye!");
                break;
            } else {
                outputWriter.println("Invalid option. Please enter 'A', 'U', or 'Q'.");
            }
        }
        inputReader.close();
    }

    // Admin Portal
    private void runAdminPortal() {
        outputWriter.println("\nAdmin Portal");
        boolean adminRunning = true;
        while (adminRunning) {
            outputWriter.println("\nAdmin Menu:\n1. Add New Airport\n2. View All Airports\n3. Add New Aircraft\n4. View All Aircraft\n5. Add New Flight (Future Feature)\n6. Back to Main Menu");
            String choice = inputReader.readLine("Choose an option: ").trim();
            switch (choice) {
                case "1": addAirport(); break;
                case "2": airportManager.displayAllAirports(outputWriter); break;
                case "3": addAircraft(); break;
                case "4": aircraftManager.displayAllAircraft(outputWriter); break;
                case "5": outputWriter.println("Adding new flights by admin is a feature being worked on."); break;
                case "6": adminRunning = false; break;
                default: outputWriter.println("Invalid option. Please try again.");
            }
        }
    }

    private void addAirport() {
        outputWriter.println("\nAdd New Airport");
        try {
            String name = inputReader.readLine("Enter airport name: ");
            String iata = inputReader.readLine("Enter IATA code: ");
            String icao = inputReader.readLine("Enter ICAO code: ");
            String city = inputReader.readLine("Enter city: ");
            String country = inputReader.readLine("Enter country: ");
            int term = inputReader.readInt("Enter number of terminals: ");
            int runs = inputReader.readInt("Enter number of runways: ");
            double lat = inputReader.readDouble("Enter latitude: ");
            double lon = inputReader.readDouble("Enter longitude: ");
            airportManager.addAirport(new Airport(name, iata, icao, city, country, term, runs, lat, lon));
            outputWriter.println("Airport '" + name + "' added successfully!");
        } catch (Exception e) { // Catching general Exception for simplicity, but more specific catches are better.
            outputWriter.println("An error occurred: " + e.getMessage());
        }
    }

    private void addAircraft() {
        outputWriter.println("\nAdd New Aircraft");
        try {
            String reg = inputReader.readLine("Enter registration number: ");
            String model = inputReader.readLine("Enter model: ");
            String manf = inputReader.readLine("Enter manufacturer: ");
            int cap = inputReader.readInt("Enter seating capacity: ");
            double mtow = inputReader.readDouble("Enter max takeoff weight (kg): ");
            double rng = inputReader.readDouble("Enter range (km): ");
            int year = inputReader.readInt("Enter year of manufacture: ");
            aircraftManager.addAircraft(new Aircraft(reg, model, manf, cap, mtow, rng, year));
            outputWriter.println("Aircraft '" + manf + " " + model + " (" + reg + ")' added successfully!");
        } catch (Exception e) {
            outputWriter.println("An error occurred: " + e.getMessage());
        }
    }

    // User Portal for flight booking
    private void runUserPortal() {
        outputWriter.println("\n User Portal: Book a Flight");

        // Collect passenger details for User object.
        outputWriter.println("Please enter your details to proceed.");
        String name = inputReader.readLine("Enter full name: ");
        String email = inputReader.readLine("Enter email: ");
        String phone = inputReader.readLine("Enter phone number: ");
        User user = new User(name, email, phone);
        outputWriter.println("Welcome, " + user.getName() + "!");

        flightManager.displayAvailableFlights(outputWriter);

        if (flightManager.getAvailableFlights().isEmpty()) {
            // Already handled by displayAvailableFlights, but good to double check
            return;
        }

        Flight selectedFlight = flightManager.selectFlight(inputReader, outputWriter);
        if (selectedFlight == null) {
            outputWriter.println("Flight selection failed. Returning to main menu.");
            return;
        }

        outputWriter.println("\nSeat Selection for Flight " + selectedFlight.getFlightNumber() + " to " + selectedFlight.getArrivalLocation());
        selectedFlight.displayAvailableSeats(outputWriter); // Using flight's method for its own state

        String selectedSeat = null;
        boolean seatBooked = false;
        while (!seatBooked) {
            selectedSeat = inputReader.readLine("Select your seat (e.g., 3C): ").trim().toUpperCase();
            if (selectedFlight.isSeatValid(selectedSeat)) {
                if (selectedFlight.bookSeat(selectedSeat)) { // This method prints its own success/error messages
                    seatBooked = true;
                }
            } else {
                outputWriter.println("Invalid seat format or seat does not exist.");
            }
        }

        // Create Booking object after successful seat booking
        Booking booking = new Booking(user, selectedFlight, selectedSeat);

        // Handle payment process using the injected service
        paymentService.initiatePayment(booking, inputReader, outputWriter);

        // Display final booking confirmation
        booking.displayBookingDetails(outputWriter);

        outputWriter.println("Thank you for booking with us, " + user.getName() + "!");
    }

    public static void main(String[] args) {
        // --- Dependency Injection / Wiring up the application ---
        InputReader inputReader = new ConsoleInputReader();
        OutputWriter outputWriter = new ConsoleOutputWriter();

        // Initializing managers with some dummy data
        List<Flight> initialFlights = new ArrayList<>(Arrays.asList(
            new Flight("SA101", "Nairobi", "Cape Town", 120.50),
            new Flight("KQ202", "Nairobi", "London", 900.00),
            new Flight("ET303", "Nairobi", "Addis Ababa", 110.75)
        ));

        AircraftManager aircraftManager = new SimpleAircraftManager();
        AirportManager airportManager = new SimpleAirportManager();
        FlightManager flightManager = new SimpleFlightManager(initialFlights);
        DefaultPaymentService paymentService = new DefaultPaymentService(); // Can be an interface too

        // Inject all dependencies into the main application class
        PlaneBookingSystem app = new PlaneBookingSystem(
            inputReader, outputWriter,
            aircraftManager, airportManager,
            flightManager, paymentService
        );

        // Run the application
        app.run();
    }
}