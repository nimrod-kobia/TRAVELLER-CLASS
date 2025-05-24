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

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    private Random random = new Random();

    public Flight(String flightNumber, String arrivalLocation, double price) {
        this.flightNumber = flightNumber;
        this.arrivalLocation = arrivalLocation;
        this.price = price;
    }

    public String seatBooking() {
        if (bookedSeats.size() >= MAX_ROWS * SEAT_LETTERS.length) return null;
        String seat;
        do {
            int row = random.nextInt(MAX_ROWS) + 1;
            char letter = SEAT_LETTERS[random.nextInt(SEAT_LETTERS.length)];
            seat = row + String.valueOf(letter);
        } while (bookedSeats.contains(seat));
        bookedSeats.add(seat);
        return seat;
    }

    public void displayInfo() {
        System.out.println("Flight: " + flightNumber + " to " + arrivalLocation + " - Price: $" + price);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getPrice() {
        return price;
    }
}

// FlightSeat class
class FlightSeat {
    private String seatNumber, seatClass;
    private boolean isBooked;
    private double price;

    public FlightSeat(String seatNumber, String seatClass, double price) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
        this.isBooked = false;
    }

    public boolean bookSeat() {
        if (!isBooked) {
            isBooked = true;
            return true;
        }
        return false;
    }

    public void displaySeatInfo() {
        System.out.println("Seat: " + seatNumber + " | Class: " + seatClass + " | Price: $" + price + " | Status: " + (isBooked ? "Booked" : "Available"));
    }
}

// Payments classes
class Payments {
    private double amount;
    private String paymentMethod;
    public Payments(int paymentId, int bookingId, double amount, String paymentMethod, Date paymentDate) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
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
        Aircraft aircraft = new Aircraft("N12345", "737 MAX", "Boeing", 180, 79015, 6570.0, 2020);
        System.out.println(aircraft);

        Airlines airline = new Airlines(1, "Kenya Airways", "KQ", "Nairobi", "+254700000000", "www.kenya-airways.com");
        System.out.println(airline.getAirlineDetails());

        Airport airport = new Airport("Jomo Kenyatta International", "NBO", "HKJK", "Nairobi", "Kenya", 2, 3, -1.3192, 36.9275);
        airport.addFlight("KQ101");
        System.out.println(airport);
        System.out.println("Flights: " + airport.getFlights());

        User user = new User("John Doe");
        Flight flight = new Flight("SA101", "Cape Town", 120.50);
        flight.displayInfo();
        String seat = flight.seatBooking();

        Booking booking = new Booking(user, flight.getFlightNumber(), seat, flight.getPrice());
        booking.displayBookingDetails();

        Payments payment = new CashPayment(1, booking.getBookingId(), flight.getPrice(), new Date(System.currentTimeMillis()));
        if (payment.validatePaymentDetails()) payment.processPayment();
        else System.out.println("Payment validation failed.");
    }
}
