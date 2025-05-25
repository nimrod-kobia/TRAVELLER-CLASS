import java.util.Scanner;

public class FlightOptions {
    private Flight[] flights;

    public FlightOptions() {
        loadFlightsFromDatabase(); // Placeholder for future DB integration
    }

    private void loadFlightsFromDatabase() {
        // Simulating a database - you can replace this with actual DB fetch later
        flights = new Flight[] {
            new Flight("SA101", "Cape Town", 120.50),
            new Flight("SA202", "Durban", 99.99),
            new Flight("SA303", "Johannesburg", 150.75),
            new Flight("SA404", "Port Elizabeth", 80.00)
        };
    }

    public void displayAllFlights() {
        System.out.println("Available Flights:");
        for (Flight flight : flights) {
            flight.displayInfo();
        }
    }

    public Flight selectFlight(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return flight;
            }
        }
        System.out.println("Flight with number " + flightNumber + " not found.");
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightOptions flightOptions = new FlightOptions();

        flightOptions.displayAllFlights();

        System.out.print("\nEnter the flight number you want to select: ");
        String inputFlightNumber = scanner.nextLine();

        Flight selectedFlight = flightOptions.selectFlight(inputFlightNumber);

        if (selectedFlight != null) {
            System.out.println("\nYou have selected:");
            selectedFlight.displayInfo();
        }

        scanner.close();
    }
}
