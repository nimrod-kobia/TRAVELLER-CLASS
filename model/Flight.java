package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Flight {
    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private double price;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Aircraft aircraft; // Link to Aircraft
    private Set<String> bookedSeats;

    // Simple constructor for dummy/test data
    public Flight(String flightNumber, String departureLocation, String arrivalLocation, double price) {
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.price = price;
        this.departureTime = LocalDateTime.now();
        this.arrivalTime = LocalDateTime.now().plusHours(2);
        this.aircraft = null;
        this.bookedSeats = new HashSet<>();
    }

    // Full constructor
    public Flight(String flightNumber, String departureLocation, String arrivalLocation, double price,
                  LocalDateTime departureTime, LocalDateTime arrivalTime, Aircraft aircraft) {
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.aircraft = aircraft;
        this.bookedSeats = new HashSet<>();
    }

    // Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public int getAvailableSeats() {
        return aircraft.getCapacity() - bookedSeats.size();
    }

    public Set<String> getBookedSeats() {
        return new HashSet<>(bookedSeats); // Return a copy to prevent external modification
    }

    public boolean bookSeat(String seatId) {
        if (!bookedSeats.contains(seatId) && isValidSeat(seatId)) {
            bookedSeats.add(seatId);
            return true;
        }
        return false;
    }

    public boolean cancelSeat(String seatId) {
        return bookedSeats.remove(seatId);
    }

    // Helper to check if a seat ID is valid for the assigned aircraft's capacity
    private boolean isValidSeat(String seatId) {
        // This is a simplified check. A more robust check would involve parsing
        // the seatId (e.g., "1A") and comparing it against the aircraft's row/column configuration.
        // For now, we'll just assume any seatId fits if it's within the overall capacity.
        return true; // For now, always true. Refine if you implement detailed seat maps per aircraft.
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s from %s (%s) to %s (%s) - Ksh %.2f (Aircraft: %s, Cap: %d)",
                             flightNumber, departureLocation, departureTime.format(formatter),
                             arrivalLocation, arrivalTime.format(formatter), price,
                             aircraft.getModel(), aircraft.getCapacity());
    }

    public void displayInfo() {
        System.out.println("Flight: " + flightNumber +
            " | From: " + departureLocation +
            " | To: " + arrivalLocation +
            " | Price: Ksh " + price);
    }
}