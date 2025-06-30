package model;

import java.time.LocalDateTime;

public class Booking {
    private static int idCounter = 1;

    private int bookingId;
    private User passenger;
    private String flightId;
    private String seatId;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private String bookingStatus;
    private int userId; // Added userId field

    public Booking(User passenger, String flightId, String seatId, double totalPrice) {
        this.bookingId = idCounter++;
        this.passenger = passenger;
        this.flightId = flightId;
        this.seatId = seatId;
        this.bookingTime = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.bookingStatus = "CONFIRMED";
    }

    public Booking(int int1, int int2, int int3, String string, LocalDateTime localDateTime, double double1,
            String string2) {
        //TODO Auto-generated constructor stub
    }

    public int getBookingId() {
        return bookingId;
    }

    public User getPassenger() {
        return passenger;
    }

    public String getFlightId() { return flightId; }

    public String getSeatId() {
        return seatId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void updateBookingStatus(String status) {
        this.bookingStatus = status;
    }

    public void displayBookingDetails() {
        System.out.println("\nBooking Details:");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Flight ID: " + flightId);
        System.out.println("Seat ID: " + seatId);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Total Price: ksh" + totalPrice);
        System.out.println("Booking Status: " + bookingStatus);
    }

    public void setPaymentStatus(String status) {
        this.bookingStatus = status;
    }

    public int getUserId() { return userId; }
}
