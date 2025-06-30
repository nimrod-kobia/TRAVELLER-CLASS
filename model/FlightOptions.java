package model;

import java.util.ArrayList;
import java.util.List;

public class FlightOptions {
    private List<Flight> flights;

    public FlightOptions() {
        loadFlights();
    }

    private void loadFlights() {
        flights = new ArrayList<>();
        flights.add(new Flight("SA101", "Cape Town", "Johannesburg", 120.50));
        flights.add(new Flight("SA202", "Durban", "Cape Town", 99.99));
        flights.add(new Flight("SA303", "Johannesburg", "Durban", 150.75));
        flights.add(new Flight("SA404", "Port Elizabeth", "Nairobi", 80.00));
    }

    public List<Flight> getAllFlights() {
        return flights;
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

    // Optional: For console testing
    public static void main(String[] args) {
        FlightOptions flightOptions = new FlightOptions();
        flightOptions.displayAllFlights();
    }
}
