package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Flight {
    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private double price;
    private Set<String> bookedSeats;

    public Flight(String flightNumber, String departureLocation, String arrivalLocation, double price) {
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.price = price;
        this.bookedSeats = new HashSet<>();
    }

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

    public Set<String> getBookedSeats() {
        return bookedSeats;
    }

    public void bookSeat(String seatId) {
        if (seatId != null && !seatId.trim().isEmpty()) {
            this.bookedSeats.add(seatId);
        }
    }

    public void unbookSeat(String seatId) {
        if (seatId != null && !seatId.trim().isEmpty()) {
            this.bookedSeats.remove(seatId);
        }
    }

    public boolean isSeatBooked(String seatId) {
        return bookedSeats.contains(seatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber);
    }

    @Override
    public String toString() {
        return flightNumber + " (" + departureLocation + " to " + arrivalLocation + ")";
    }

    public void displayInfo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayInfo'");
    }
}