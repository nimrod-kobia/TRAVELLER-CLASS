import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

// Aircraft class
class Aircraft {
    private String registrationNumber, model, manufacturer;
    private int seatingCapacity;
    private double range;

    public Aircraft(String registrationNumber, String model, String manufacturer,
                    int seatingCapacity, double maxTakeoffWeight, double range, int yearOfManufacture) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.manufacturer = manufacturer;
        this.seatingCapacity = seatingCapacity;
        this.range = range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getRange() {
        return range;
    }

    public String toString() {
        return String.format("%s %s (%s) - Capacity: %d, Range: %.1f km",
                manufacturer, model, registrationNumber, seatingCapacity, range);
    }
}

// Airline interface and class
interface AirlineInfo {
    String getAirlineDetails();
}

class Airlines implements AirlineInfo {
    private String airlineName, airlineCode, headquarters, contactNumber, website;

    public Airlines(Integer airlineId, String airlineName, String airlineCode, String headquarters, String contactNumber, String website) {
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.headquarters = headquarters;
        this.contactNumber = contactNumber;
        this.website = website;
    }

    public String getAirlineDetails() {
        return "Airline Name: " + airlineName + "\n" +
               "Airline Code: " + airlineCode + "\n" +
               "Headquarters: " + headquarters + "\n" +
               "Contact: " + contactNumber + "\n" +
               "Website: " + website;
    }
}

// Airport class
class Airport {
    private String name, iataCode, city, country;
    private List<String> flights = new ArrayList<>();

    public Airport(String name, String iataCode, String icaoCode, String city, String country,
                   int numberOfTerminals, int numberOfRunways, double latitude, double longitude) {
        this.name = name;
        this.iataCode = iataCode;
        this.city = city;
        this.country = country;
    }

    public void addFlight(String flight) {
        flights.add(flight);
    }

    public String toString() {
        return String.format("Airport: %s (%s), %s, %s", name, iataCode, city, country);
    }

    public List<String> getFlights() {
        return flights;
    }
}

// User class
class User {
    private String name;
    private String email;
    private String phoneNumber;

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

// Booking class
class Booking {
    private static int idCounter = 1;
    private int bookingId;
    private User passenger;
    private String flightId, seatId;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private String bookingStatus = "Confirmed";
    private String paymentStatus = "Pending";

    public Booking(User passenger, String flightId, String seatId, double totalPrice) {
        this.bookingId = idCounter++;
        this.passenger = passenger;
        this.flightId = flightId;
        this.seatId = seatId;
        this.totalPrice = totalPrice;
        this.bookingTime = LocalDateTime.now();
    }

    public void displayBookingDetails() {
        System.out.println("\nBooking Details:");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Email: " + passenger.getEmail());
        System.out.println("Phone: " + passenger.getPhoneNumber());
        System.out.println("Flight ID: " + flightId);
        System.out.println("Seat ID: " + seatId);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Booking Status: " + bookingStatus);
        System.out.println("Payment Status: " + paymentStatus);
    }

    public int getBookingId() {
        return bookingId;
    }
}

// Flight class
class Flight {
    private String flightNumber, arrivalLocation;
    private double price;
    private Set<String> bookedSeats = new HashSet<>();
    private static final int MAX_ROWS = 14;
    private static final char[] SEAT_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F'};

    public Flight(String flightNumber, String arrivalLocation, double price) {
        this.flightNumber = flightNumber;
        this.arrivalLocation = arrivalLocation;
        this.price = price;
    }

    public void displayInfo() {
        System.out.println("Flight: " + flightNumber + " to " + arrivalLocation + " - Price: $" + price);
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats:");
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (char letter : SEAT_LETTERS) {
                String seat = row + String.valueOf(letter);
                if (!bookedSeats.contains(seat)) System.out.print(seat + " ");
            }
            System.out.println();
        }
    }

    public boolean bookSeat(String seatId) {
        if (bookedSeats.contains(seatId)) return false;
        bookedSeats.add(seatId);
        return true;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getPrice() {
        return price;
    }
}

// Payments classes
class Payments {
    private double amount;
    private String paymentMethod;
    private Date paymentDate;

    public Payments(int paymentId, int bookingId, double amount, String paymentMethod, Date paymentDate) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    public boolean validatePaymentDetails() {
        return amount > 0 && paymentMethod != null && !paymentMethod.isEmpty();
    }

    public void processPayment() {
        System.out.println("Processing payment of $" + amount + " using " + paymentMethod);
    }
}

class CashPayment extends Payments {
    public CashPayment(int paymentId, int bookingId, double amount, Date paymentDate) {
        super(paymentId, bookingId, amount, "Cash", paymentDate);
    }
}

// Main launcher class
public class PlaneBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Plane Booking System\nPlease enter your details to proceed.");
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        User user = new User(name, email, phoneNumber);

        List<Flight> flights = Arrays.asList(
            new Flight("SA101", "Cape Town", 120.50),
            new Flight("KQ202", "Nairobi", 90.00),
            new Flight("ET303", "Addis Ababa", 110.75)
        );

        System.out.println("\nAvailable Flights:");
        for (int i = 0; i < flights.size(); i++) {
            System.out.print((i + 1) + ". ");
            flights.get(i).displayInfo();
        }

        System.out.print("\nSelect a flight (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Flight selectedFlight = flights.get(choice - 1);

        selectedFlight.displayAvailableSeats();
        System.out.print("\nSelect your seat (e.g., 3C): ");
        String selectedSeat = scanner.nextLine().toUpperCase();

        while (!selectedFlight.bookSeat(selectedSeat)) {
            System.out.print("Seat already booked or invalid. Choose another seat: ");
            selectedSeat = scanner.nextLine().toUpperCase();
        }

        Booking booking = new Booking(user, selectedFlight.getFlightNumber(), selectedSeat, selectedFlight.getPrice());
        booking.displayBookingDetails();

        Payments payment = new CashPayment(1, booking.getBookingId(), selectedFlight.getPrice(), new Date(System.currentTimeMillis()));
        if (payment.validatePaymentDetails()) payment.processPayment();
        else System.out.println("Payment validation failed.");
    }
}