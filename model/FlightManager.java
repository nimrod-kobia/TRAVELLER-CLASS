package model;

import java.util.List;

public interface FlightManager {
    List<Flight> getAvailableFlights();
    Flight getFlightByNumber(String flightNumber);
}
