import java.util.*;

//creating the flight class
public class flight {
    private String flightNumber;
    private String seatNumber;
    private String arrivalLocation;
    private double price; // <-- Added price
    private Set<String> bookedSeats = new HashSet<>(); // stores the booked seats
    private Random random = new Random();

    // total amount of seats that can be taken in any flight
    private static final int MAX_ROWS = 14;
    private static final char[] SEAT_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F'};

    // constructor for the flight class
    public flight() {
        this.flightNumber = "";
        this.seatNumber = "";
        this.arrivalLocation = "";
        this.price = 0.0;
    }

    // setters and getters for the flight class
    public void flightPicked(String flightNumber) {
        this.flightNumber = flightNumber;
        System.out.println("Flight " + flightNumber + " has been selected.");
    }

    public String seatBooking() {
        if (bookedSeats.size() >= MAX_ROWS * SEAT_LETTERS.length) {
            System.out.println("No more seats available on this flight.");
            return null;
        }

        // Generate a random seat number
        String seat;
        do {
            int row = random.nextInt(MAX_ROWS) + 1; // 1-14
            char letter = SEAT_LETTERS[random.nextInt(SEAT_LETTERS.length)];
            seat = row + String.valueOf(letter).toUpperCase();
        } while (bookedSeats.contains(seat));

        bookedSeats.add(seat);
        this.seatNumber = seat;
        System.out.println("Seat " + seat + " has been assigned.");
        return seat;
    }

    public void arrivalLocation(String destination) {
        this.arrivalLocation = destination;
        System.out.println("Arrival location set to: " + destination);
    }

    // Price setter
    public void setPrice(double price) {
        this.price = price;
        System.out.println("Ticket price set to: $" + price);
    }

    // Price getter
    public double getPrice() {
        return price;
    }

    // Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public static void main(String[] args) {
        flight flight = new flight();

        flight.flightPicked("SA220");
        flight.setPrice(199.99); // <-- Set the price
        flight.seatBooking(); // book a seat
        flight.arrivalLocation("O.R. Tambo International Airport");

        System.out.println("\nFlight Details:");
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Seat Number: " + flight.getSeatNumber());
        System.out.println("Arrival Location: " + flight.getArrivalLocation());
        System.out.println("Ticket Price: $" + flight.getPrice()); // <-- Display price
    }
}
