import java.util.*;

// Interface for flight information
interface FlightInfo {
    String getFlightDetails();
}

// Flight class with encapsulation and interface implementation
class Flight implements FlightInfo {
    private String flightId;
    private String flightNumber;
    private double price;

    public Flight(String flightId, String flightNumber, double price) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.price = price;
    }

    @Override
    public String getFlightDetails() {
        return "Flight ID: " + flightId + "\n" +
               "Flight Number: " + flightNumber + "\n" +
               "Price: Ksh " + price + "\n";
    }

    public void displayInfo(Object object) {
        
        throw new UnsupportedOperationException("Unimplemented method 'displayInfo'");
    }

    public String getFlightNumber() {
       
        throw new UnsupportedOperationException("Unimplemented method 'getFlightNumber'");
    }
}

// Main app class
public class FlightApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Flight> flights = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Flight Booking App.");
        System.out.print("Are you an admin or traveller? (admin/traveller): ");
        String userType = scanner.nextLine().trim().toLowerCase();

        if (userType.equals("admin")) {
            handleAdmin();
        } else if (userType.equals("traveller")) {
            handleTraveller();
        } else {
            System.out.println("Invalid user type. Exiting.");
        }
    }

    // Admin input flow
    public static void handleAdmin() {
        System.out.println("\nAdmin Access Granted. Enter Flight Details:");

        String choice;
        do {
            System.out.print("Flight ID: ");
            String id = scanner.nextLine();

            System.out.print("Flight Number: ");
            String number = scanner.nextLine();

            System.out.print("Ticket Price (Ksh): ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            Flight flight = new Flight(id, number, price);
            flights.add(flight);

            System.out.println("Flight added successfully.");
            System.out.print("Do you want to add another flight? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();

        } while (choice.equals("yes"));

        System.out.println("Returning to main menu...\n");
        main(null); // Return to main menu for another user
    }

    // Traveller view flow
    public static void handleTraveller() {
        System.out.println("\nTraveller Access Granted.");
        if (flights.isEmpty()) {
            System.out.println("No flight information available at the moment.");
        } else {
            System.out.println("Available Flights:\n");
            for (Flight flight : flights) {
                printFlightInfo(flight);
                System.out.println("---------------------------");
            }
        }
    }

    // Polymorphic method to print flight info
    public static void printFlightInfo(FlightInfo flight) {
        System.out.println(flight.getFlightDetails());
    }
}
