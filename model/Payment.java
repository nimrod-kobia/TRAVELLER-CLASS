package model;

import java.sql.Timestamp;

//  SRP: Abstract Base
public abstract class Payment {
    protected int paymentId;
    protected int bookingId;
    protected double amount;
    protected String paymentMethod;
    protected String status;
    protected Timestamp paymentDate;

    public Payment(int paymentId, int bookingId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "Pending";
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }

    public int getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public Timestamp getPaymentDate() { return paymentDate; }

    // LSP-compatible abstract method
    public abstract void processPayment(Booking booking);
}

// OCP: CashPayment Class 
class CashPayment extends Payment {

    public CashPayment(int paymentId, int bookingId, double amount) {
        super(paymentId, bookingId, amount, "Cash");
    }

    @Override
    public void processPayment(Booking booking) {
        
        status = "Completed";
        System.out.println("Cash payment of Ksh " + amount + " processed.");
    }
}
//  OCP: CardPayment Class 
class CardPayment extends Payment implements Validatable {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CardPayment(int paymentId, int bookingId, double amount, String cardNumber, String expiryDate, String cvv) {
        super(paymentId, bookingId, amount, "Card");
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void processPayment(Booking booking) {
        if (!validate()) {
            status = "Failed";
            System.out.println("Card payment failed due to validation.");
            return;
        }
        status = "Completed";
        System.out.println("Card payment of Ksh " + amount + " processed.");
    }

    @Override
    public boolean validate() {
        return cardNumber.matches("\\d{16}") &&
               expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}") &&
               cvv.matches("\\d{3,4}");
    }
}


interface Validatable {
    boolean validate();
}

// PaymentProcessor Depends on Abstraction 
class PaymentProcessor {
    public void handlePayment(Payment payment, Booking booking) {
        payment.processPayment(booking);
        System.out.println("Status: " + payment.getStatus());
    }
}

//  Dummy Booking Class
class Booking {
    private int bookingId;
    private String customerName;

    public Booking(int bookingId, String customerName) {
        this.bookingId = bookingId;
        this.customerName = customerName;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }
}


class Main {
    public static void main(String[] args) {
        Booking booking = new Booking(1, "John Doe");

        Payment cash = new CashPayment(101, booking.getBookingId(), 5000);
        Payment card = new CardPayment(102, booking.getBookingId(), 7000, "1234567812345678", "12/25", "123");

        PaymentProcessor processor = new PaymentProcessor();
        processor.handlePayment(cash, booking);
        processor.handlePayment(card, booking);
    }
}
