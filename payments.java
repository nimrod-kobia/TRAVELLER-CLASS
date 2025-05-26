import java.sql.Date;

<<<<<<< HEAD
abstract class Payment {
    protected int paymentId;
    protected String bookingId;
    protected double amount;
    protected Date paymentDate;
    
    public Payment(int paymentId, String bookingId, double amount, Date paymentDate) {
=======
public class payments {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private Date paymentDate;

    // Constructor
    public payments(int paymentId, int bookingId, double amount, String paymentMethod, Date paymentDate) {
>>>>>>> afdb3c432c50f398a5269f4dddc8b6080d46e931
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
    
    public abstract void processPayment(Booking booking);
}

class CashPayment extends Payment {
    public CashPayment(int paymentId, String bookingId, double amount, Date paymentDate) {
        super(paymentId, bookingId, amount, paymentDate);
    }

    @Override
    public void processPayment(Booking booking) {
        System.out.println("\nProcessing cash payment...");
        System.out.println("Payment of $" + String.format("%.2f", amount) + 
                         " using Cash accepted for booking ID " + bookingId + 
                         ".\nPayment successful on: " + paymentDate.toString());
        booking.setPaymentStatus("Paid");
    }
}

class CardPayment extends Payment {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CardPayment(int paymentId, String bookingId, double amount, Date paymentDate, 
                      String cardNumber, String expiryDate, String cvv) {
        super(paymentId, bookingId, amount, paymentDate);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void processPayment(Booking booking) {
        System.out.println("\nProcessing card payment...");
        if (validatePaymentDetails()) {
            System.out.println("Payment of $" + String.format("%.2f", amount) + 
                             " using Card (ending with " + 
                             cardNumber.substring(cardNumber.length() - 4) + 
                             ") accepted for booking ID " + bookingId + 
                             ".\nPayment successful on: " + paymentDate.toString());
            booking.setPaymentStatus("Paid");
        } else {
            System.out.println("Card payment validation failed for booking ID " + bookingId + ".");
            booking.setPaymentStatus("Failed");
        }
    }

    private boolean validatePaymentDetails() {
        // Basic validation - expand as needed
        return cardNumber != null && cardNumber.matches("\\d{16}") &&
               expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}") &&
               cvv != null && cvv.matches("\\d{3}");
    }
}        