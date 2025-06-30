package model;

import java.sql.Timestamp;

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

    // Concrete getters that all payment types will use
    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    // Abstract method that subclasses must implement
    public abstract void processPayment(Booking booking);
}

