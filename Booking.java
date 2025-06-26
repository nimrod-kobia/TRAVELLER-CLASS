import java.time.LocalDateTime;

public class Booking {
    private static int idCounter = 1;

    private int BookingId;
    private User passenger;
    private String flightId;
    private String seatId;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private String bookingStatus;
    private String paymentStatus;

    public Booking(User passenger, String flightId, String seatId, double totalPrice) {
        this.BookingId = idCounter++;
        this.passenger = passenger;
        this.flightId = flightId;
        this.seatId = seatId;
        this.bookingTime = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.bookingStatus = "Confirmed";
        this.paymentStatus = "Pending";
    }

    public int getBookingId() {
        return BookingId;
    }

    public User getPassenger() {
        return passenger;
    }

    public String getFlightId() {
        return flightId;
    }

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

    public String getPaymentStatus() {
        return paymentStatus;
    }
      public void setPaymentStatus(String status) {
        this.paymentStatus = status;
    }

    public void updatePaymentStatus(String status) {
        this.paymentStatus = status;
    }

    public void updateBookingStatus(String status) {
        this.bookingStatus = status;
    }

    public void displayBookingDetails() {
        System.out.println("\nBooking Details:");
        System.out.println("Booking ID: " + BookingId);
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Flight ID: " + flightId);
        System.out.println("Seat ID: " + seatId);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Total Price: ksh" + totalPrice);
        System.out.println("Booking Status: " + bookingStatus);
        System.out.println("Payment Status: " + paymentStatus);
    }
}
