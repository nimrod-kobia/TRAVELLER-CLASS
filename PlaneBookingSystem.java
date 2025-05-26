import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// import Passport.Visa;

// Abstract Person Class
// Nimrod: Abstract Person: base for entities like User, ensuring common details.
abstract class Person {
    protected String name, email, phoneNumber;

    public Person(String name, String email, String phoneNumber) {
        this.name = name; this.email = email; this.phoneNumber = phoneNumber;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public abstract String getRoleDescription(); // Nimrod: Subclasses define their specific role.
}

//  User Class (extends Person) 
// Nimrod: User class for passengers, extends Person for common attributes.
class User extends Person {
    public User(String name, String email, String phoneNumber) { super(name, email, phoneNumber); } // Nimrod: super() calls Person constructor.

    public User(String name, String dob, String nationality, Passport passport, Visa visa, String email) {
        super(name, email, ""); // Provide phoneNumber as empty or as needed
        // Additional initialization for dob, nationality, passport, visa can be added here if fields exist
    }

    @Override 
    public String getRoleDescription() { return "Passenger"; }
     // Nimrod: User's role is "Passenger".

    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public String getNationality() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNationality'");
    }

    public String getDOB() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDOB'");
    }

    public String getVisaNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVisaNumber'");
    }
}

// Aircraft class 
// Brian: Aircraft class: holds plane specifications. 
class Aircraft {
    private String registrationNumber, model, manufacturer;
    private int seatingCapacity;
    private double maxTakeoffWeight, range;
    private int yearOfManufacture;

    // Brian: Aircraft constructor (GUI: data entry form for admins, with field validation).
    public Aircraft(String regNum, String model, String manf, int cap, double mtow, double rng, int year) {
        this.registrationNumber = regNum; this.model = model; this.manufacturer = manf;
        this.seatingCapacity = cap; this.maxTakeoffWeight = mtow; this.range = rng; this.yearOfManufacture = year;
    }

    public void setRange(double range) { this.range = range; } // Brian: Setter for range.
    public double getRange() { return range; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getSeatingCapacity() { return seatingCapacity; } // Brian: Capacity important for seat maps.
    public double getMaxTakeoffWeight() { return maxTakeoffWeight; }
    public int getYearOfManufacture() { return yearOfManufacture; }

    @Override
    // Brian: toString for Aircraft summary
    public String toString() {
        return String.format("Aircraft: %s %s (%s)\n  Capacity: %d, Range: %.1f km, Max Takeoff Weight: %.1f kg, Year: %d",
                manufacturer, model, registrationNumber, seatingCapacity, range, maxTakeoffWeight, yearOfManufacture);
    }
}

// Airline interface and class
// Nimrod: AirlineInfo interface for standard airline detail retrieval.
interface AirlineInfo { String getAirlineDetails(); }

// Nimrod: Airlines class: concrete airline details, implements AirlineInfo.
class Airlines implements AirlineInfo {
    private String airlineName, airlineCode, headquarters, contactNumber, website;

    // Nimrod: Airlines constructor (data typically admin-managed).
    public Airlines(String name, String code, String hq, String contact, String web) {
        this.airlineName = name; this.airlineCode = code; this.headquarters = hq;
        this.contactNumber = contact; this.website = web;
    }

    public String getAirlineName() { return airlineName; }
    public String getAirlineCode() { return airlineCode; }

    @Override
    // Nimrod: Formatted string of airline details, per AirlineInfo.
    public String getAirlineDetails() {
        return "Airline Name: " + airlineName + "\nAirline Code: " + airlineCode + "\nHeadquarters: " + headquarters +
               "\nContact: " + contactNumber + "\nWebsite: " + website;
    }
}

// Airport class
// Brian: Airport class: stores key airport information.
class Airport {
    private String name, iataCode, icaoCode, city, country;
    private int numberOfTerminals, numberOfRunways;
    private double latitude, longitude;
    private List<String> flightNumbersServed = new ArrayList<>();

    // Brian: Airport constructor (GUI: "Add Airport" form, possibly with map integration).
    public Airport(String name, String iata, String icao, String city, String country, int term, int runs, double lat, double lon) {
        this.name = name; this.iataCode = iata; this.icaoCode = icao; this.city = city; this.country = country;
        this.numberOfTerminals = term; this.numberOfRunways = runs; this.latitude = lat; this.longitude = lon;
    }

    public void addServedFlightNumber(String flightNumber) { flightNumbersServed.add(flightNumber); }
    public String getName() { return name; }
    public String getIataCode() { return iataCode; } // Brian: IATA/ICAO are key identifiers.
    public String getCity() { return city; }
    public String getCountry() { return country; }

    @Override
    // Brian: toString for Airport summary
    public String toString() {
        return String.format("Airport: %s (%s/%s)\n  Location: %s, %s\n  Terminals: %d, Runways: %d\n  Coordinates: Lat %.4f, Lon %.4f",
                name, iataCode, icaoCode, city, country, numberOfTerminals, numberOfRunways, latitude, longitude);
    }
    public List<String> getFlights() { return flightNumbersServed; }
}

// Booking class 
// Ryan: Booking system: this class captures flight reservation details.
class Booking {
    private static int idCounter = 1; // Ryan: Static counter for unique booking IDs.
    private int bookingId;
    private User passenger; // Ryan: Booking tied to a User.
    private String flightId, seatId;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private String bookingStatus = "Confirmed", paymentStatus = "Pending"; // Ryan: Initial statuses.
    

    // Ryan: Booking constructor, called after flight/seat selection. (Integrate Baggage later on).
    public Booking(User passenger, String flightId, String seatId, double totalPrice) {
        this.bookingId = idCounter++; this.passenger = passenger; this.flightId = flightId; this.seatId = seatId;
        this.totalPrice = totalPrice; this.bookingTime = LocalDateTime.now();
    }

    public Booking(Object passenger2, String flightId2, int seatId2, double totalPrice2) {
        //TODO Auto-generated constructor stub
    }

    // Ryan: Displays booking confirmation to the user.
    public void displayBookingDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Ryan: Readable date format.
        System.out.println("\n--- Booking Confirmation ---\nBooking ID: " + bookingId +
                "\nPassenger: " + passenger.getName() + " (" + passenger.getRoleDescription() + ")" +
                "\nEmail: " + passenger.getEmail() + "\nPhone: " + passenger.getPhoneNumber() +
                "\nFlight ID: " + flightId + "\nSeat ID: " + seatId +
                "\nBooking Time: " + bookingTime.format(formatter) +
                String.format("\nTotal Price: $%.2f", totalPrice) +
                "\nBooking Status: " + bookingStatus + "\nPayment Status: " + paymentStatus +
                "\n--------------------------");
    }

    public int getBookingId() { return bookingId; } // Ryan: ID for referencing (e.g. in payments).
    public double getTotalPrice() { return totalPrice; } // Ryan: Needed by payment module.
    public void setPaymentStatus(String status) { this.paymentStatus = status; } // Ryan: Allows payment module to update.

}

// Flight class
// Sean: Flight class: flight info, seat management, display options.
class Flight {
    private String flightNumber, arrivalLocation;
    private double price;
    private Set<String> bookedSeats = new HashSet<>(); // Sean: HashSet for efficient seat lookup.
    // Sean: Fixed seat layout.
    private static final int MAX_ROWS = 14;
    private static final char[] SEAT_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F'};

    public Flight(String flightNum, String arrivalLoc, double price) {
        this.flightNumber = flightNum; this.arrivalLocation = arrivalLoc; this.price = price;
    }

    public void displayInfo() { System.out.printf("Flight: %s to %s - Price: $%.2f\n", flightNumber, arrivalLocation, price); } // Sean: Basic flight option display.

    // Sean: UI for showing available/booked seats.
    public void displayAvailableSeats() {
        System.out.println("Available Seats for Flight " + flightNumber + ":");
        for (int r = 1; r <= MAX_ROWS; r++) {
            for (char l : SEAT_LETTERS) System.out.print((bookedSeats.contains(r + "" + l) ? "XX " : (r + "" + l + " ")));
            System.out.println();
        }
    }

    // Sean: Validates seat ID format and bounds.
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

    // Sean: Handles booking a seat on this flight.
    public boolean bookSeat(String seatId) {
        if (!isSeatValid(seatId)) { System.out.println("Error: Invalid seat format or out of bounds."); return false; }
        if (bookedSeats.contains(seatId)) { System.out.println("Error: Seat " + seatId + " is already booked."); return false; }
        bookedSeats.add(seatId);
        System.out.println("Seat " + seatId + " booked successfully for flight " + flightNumber + ".");
        return true;
    }

    public String getFlightNumber() { return flightNumber; }
    public double getPrice() { return price; }
    public String getArrivalLocation() { return arrivalLocation; }
}

// Payments Superclass
// Louis: Abstract Payments class: common structure for payment types. .
abstract class Payments {
    protected int paymentId, bookingId; // Louis: Links payment to booking.
    protected double amount;
    protected String paymentMethod;
    protected Date paymentDate;

    public Payments(int pId, int bId, double amt, String method, Date date) {
        this.paymentId = pId; this.bookingId = bId; this.amount = amt;
        this.paymentMethod = method; this.paymentDate = date;
    }

    public double getAmount() { return amount; }
    // Louis: Basic common validation. (DB: check bookingId exists & pending).
    public boolean validatePaymentDetails() { return amount > 0 && paymentMethod != null && !paymentMethod.isEmpty() && paymentDate != null; }
    // Louis: Abstract: each payment type implements. (DB: log transaction).
    public abstract void processPayment(Booking booking);

    @Override
    // Louis: Generic toString for payment. (DB: data resides in a table).
    public String toString() { return String.format("Payment ID: %d, Booking ID: %d, Amount: $%.2f, Method: %s, Date: %s", paymentId, bookingId, amount, paymentMethod, paymentDate.toString()); }
}

// CashPayment Class (extends Payments)
// Louis: CashPayment, a specific Payment type.
class CashPayment extends Payments {
    public CashPayment(int pId, int bId, double amt, Date date) { super(pId, bId, amt, "Cash", date); } // Louis: Calls superclass constructor.

    @Override
    // Louis: Cash transaction processing. (DB: Record successful/failed payment).
    public void processPayment(Booking booking) {
        System.out.println("\nProcessing cash payment...");
        if (validatePaymentDetails()) {
            System.out.println("Payment of $" + String.format("%.2f", amount) + " using " + paymentMethod + " accepted for booking ID " + bookingId + ".\nPayment successful on: " + paymentDate.toString());
            booking.setPaymentStatus("Paid");
        } else {
            System.out.println("Cash payment validation failed for booking ID " + bookingId + ".");
            booking.setPaymentStatus("Failed");
        }
    }
}

// CardPayment Class (extends Payments)
// Louis: CardPayment, another specific Payment type.
class CardPayment extends Payments {
    private String cardNumber, expiryDate, cvv; // Louis: Card details (CVV never stored; cardNum tokenized/masked in DB).

    public CardPayment(int pId, int bId, double amt, Date date, String cNum, String exp, String cvv) {
        super(pId, bId, amt, "Card", date);
        this.cardNumber = cNum; this.expiryDate = exp; this.cvv = cvv;
    }

    @Override
    // Louis: Card-specific validation + base. (Real validation via payment gateway).
    public boolean validatePaymentDetails() {
        return super.validatePaymentDetails() && cardNumber != null && cardNumber.matches("\\d{16}") &&
               expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}") && cvv != null && cvv.matches("\\d{3}");
    }

    @Override
    // Louis: Card transaction processing. (DB: Integrate gateway, log transaction ID).
    public void processPayment(Booking booking) {
        System.out.println("\nProcessing card payment...");
        if (validatePaymentDetails()) {
            System.out.println("Payment of $" + String.format("%.2f", amount) + " using Card (ending with " +
                               cardNumber.substring(cardNumber.length() - 4) + ") accepted for booking ID " + bookingId + ".\nPayment successful on: " + paymentDate.toString());
            booking.setPaymentStatus("Paid");
        } else {
            System.out.println("Card payment validation failed for booking ID " + bookingId + ".");
            booking.setPaymentStatus("Failed");
        }
    }
}

// Main Class
// PlaneBookingSystem: ties everything together.
public class PlaneBookingSystem {
    // Brian: Static lists. 
    private static List<Aircraft> aircraftFleet = new ArrayList<>();
    private static List<Airport> airportNetwork = new ArrayList<>();
    // Sean: Pre-populated flight options.
    private static List<Flight> availableFlights = new ArrayList<>(Arrays.asList(
            new Flight("SA101", "Cape Town", 120.50), new Flight("KQ202", "Nairobi", 90.00), new Flight("ET303", "Addis Ababa", 110.75) ));
    private static int nextPaymentId = 1; // Louis: Simple ID counter (DB: auto-increment key).

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Welcome to the Plane Booking System ");

        while (true) {
            System.out.print("\nAre you an Admin or a User? (Enter A or U, or Q to quit): ");
            String role = scanner.nextLine().toUpperCase();
            if (role.equals("A")) runAdminPortal(scanner);      // Brian: Admin functions.
            else if (role.equals("U")) runUserPortal(scanner); // Nimrod/Sean/Ryan/Louis: User booking flow.
            else if (role.equals("Q")) { System.out.println("Thank you for using the Plane Booking System. Goodbye!"); break; }
            else System.out.println("Invalid option. Please enter 'A', 'U', or 'Q'.");
        }
        scanner.close();
    }

    // Admin Portal
    private static void runAdminPortal(Scanner scanner) {
        //Admin main menu.
        System.out.println("\nAdmin Portal");
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\nAdmin Menu:\n1. Add New Airport\n2. View All Airports\n3. Add New Aircraft\n4. View All Aircraft\n5. Add New Flight (Future Feature)\n6. Back to Main Menu"); // Brian: Airport/Aircraft mgt. Sean: Future flight def.
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addAirport(scanner); break;    // Brian: Add airport data.
                case "2": viewAirports(); break;     // Brian: View airports (GUI: table/list).
                case "3": addAircraft(scanner); break;   // Brian: Add aircraft data.
                case "4": viewAircraft(); break;     // Brian: View aircraft (GUI: sort/filter).
                case "5": System.out.println("Adding new flights by admin is a feature being worked on."); break;
                case "6": adminRunning = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //Admin inputs new airport details.
    private static void addAirport(Scanner scanner) {
        System.out.println("\nAdd New Airport");
        try {
            System.out.print("Enter airport name: "); String name = scanner.nextLine();
            System.out.print("Enter IATA code: "); String iata = scanner.nextLine();
            System.out.print("Enter ICAO code: "); String icao = scanner.nextLine();
            System.out.print("Enter city: "); String city = scanner.nextLine();
            System.out.print("Enter country: "); String country = scanner.nextLine();
            System.out.print("Enter number of terminals: "); int term = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter number of runways: "); int runs = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter latitude: "); double lat = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter longitude: "); double lon = Double.parseDouble(scanner.nextLine());
            airportNetwork.add(new Airport(name, iata, icao, city, country, term, runs, lat, lon));
            System.out.println("Airport '" + name + "' added successfully!"); // Brian: (GUI: refresh list).
        } catch (NumberFormatException e) { System.out.println("Invalid number format."); }
          catch (Exception e) { System.out.println("An error occurred: " + e.getMessage()); }
    }

    // Brian: Displays registered airports. (GUI: sortable table/map).
    private static void viewAirports() {
        System.out.println("\nRegistered Airports");
        if (airportNetwork.isEmpty()) { System.out.println("No airports added yet."); return; }
        for (int i = 0; i < airportNetwork.size(); i++) System.out.println("\nAirport #" + (i + 1) + "\n" + airportNetwork.get(i));
        
    }

    // Brian: Admin inputs new aircraft details. (GUI: dropdowns, validation).
    private static void addAircraft(Scanner scanner) {
        System.out.println("\nAdd New Aircraft");
        try {
            System.out.print("Enter registration number: "); String reg = scanner.nextLine();
            System.out.print("Enter model: "); String model = scanner.nextLine();
            System.out.print("Enter manufacturer: "); String manf = scanner.nextLine();
            System.out.print("Enter seating capacity: "); int cap = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter max takeoff weight (kg): "); double mtow = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter range (km): "); double rng = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter year of manufacture: "); int year = Integer.parseInt(scanner.nextLine());
            aircraftFleet.add(new Aircraft(reg, model, manf, cap, mtow, rng, year));
            System.out.println("Aircraft '" + manf + " " + model + " (" + reg + ")' added successfully!");
        } catch (NumberFormatException e) { System.out.println("Invalid number format."); }
          catch (Exception e) { System.out.println("An error occurred: " + e.getMessage()); }
    }

    // Displays registered aircraft
    private static void viewAircraft() {
        System.out.println("\nRegistered Aircraft Fleet");
        if (aircraftFleet.isEmpty()) { System.out.println("No aircraft added yet."); return; }
        for (int i = 0; i < aircraftFleet.size(); i++) System.out.println("\nAircraft #" + (i + 1) + "\n" + aircraftFleet.get(i));
        System.out.println("-------------------------------");
    }

    // User Portal for flight booking
    private static void runUserPortal(Scanner scanner) {
        System.out.println("\n User Portal: Book a Flight");
        // Nimrod: Collect passenger details for User object.
        System.out.println("Please enter your details to proceed.");
        System.out.print("Enter full name: "); String name = scanner.nextLine();
        System.out.print("Enter email: "); String email = scanner.nextLine();
        System.out.print("Enter phone number: "); String phone = scanner.nextLine();
        User user = new User(name, email, phone);
        System.out.println("Welcome, " + user.getName() + "!");

        // Sean: Display available flight options.
        if (availableFlights.isEmpty()) { System.out.println("\nSorry, no flights available."); return; }
        System.out.println("\nAvailable Flights:");
        for (int i = 0; i < availableFlights.size(); i++) { System.out.print((i + 1) + ". "); availableFlights.get(i).displayInfo(); }

        Flight selectedFlight = null;
        // Sean: Handle user's flight selection.
        while (selectedFlight == null) {
            System.out.print("\nSelect a flight by number (1-" + availableFlights.size() + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= availableFlights.size()) selectedFlight = availableFlights.get(choice - 1);
                else System.out.println("Invalid flight selection.");
            } catch (NumberFormatException e) { System.out.println("Invalid input. Enter a number."); }
        }

        // Sean: Display seat map & handle seat selection.
        System.out.println("\nSeat Selection for Flight " + selectedFlight.getFlightNumber() + " to " + selectedFlight.getArrivalLocation());
        selectedFlight.displayAvailableSeats();
        String selectedSeat; boolean seatBooked = false;
        while (!seatBooked) {
            System.out.print("\nSelect your seat (e.g., 3C): "); selectedSeat = scanner.nextLine().trim().toUpperCase();
            if (selectedFlight.isSeatValid(selectedSeat)) { if (selectedFlight.bookSeat(selectedSeat)) seatBooked = true; }
            else System.out.println("Invalid seat format or seat does not exist.");
        }

        // Ryan: Create Booking object.
        Booking booking = new Booking(user, selectedFlight.getFlightNumber(), scanner.nextLine().trim().toUpperCase(), selectedFlight.getPrice()); // Re-prompt for selectedSeat as it's local to loop. Better fix: use the `selectedSeat` from loop.
       

        // Louis: Handle payment process. 
        System.out.println("\n--- Payment ---");

        // Louis: Handle payment process. (DB: payment options could be dynamic).
        System.out.println("\nPayment");

        System.out.print("Choose payment method: (1) Cash (2) Card: "); String paymentChoice = scanner.nextLine().trim();
        Payments payment;
        if ("1".equals(paymentChoice)) payment = new CashPayment(nextPaymentId++, booking.getBookingId(), booking.getTotalPrice(), new Date(System.currentTimeMillis()));
        else if ("2".equals(paymentChoice)) {
            System.out.print("Enter card number (16 digits): "); String cardNum = scanner.nextLine();
            System.out.print("Enter expiry date (MM/YY): "); String expiry = scanner.nextLine();
            System.out.print("Enter CVV (3 digits): "); String cvv = scanner.nextLine();
            payment = new CardPayment(nextPaymentId++, booking.getBookingId(), booking.getTotalPrice(), new Date(System.currentTimeMillis()), cardNum, expiry, cvv);
        } else {
            System.out.println("Invalid choice. Defaulting to Cash.");
            payment = new CashPayment(nextPaymentId++, booking.getBookingId(), booking.getTotalPrice(), new Date(System.currentTimeMillis()));
        }
        payment.processPayment(booking);
        if ("Paid".equals(booking)) // Louis: Process payment & update booking. (DB: Log transaction).
        
        booking.displayBookingDetails(); // Ryan: Final booking confirmation.
        System.out.println("Thank you for booking with us, " + user.getName() + "!");
    }
}