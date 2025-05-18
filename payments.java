//WE USE paymethods e.g;; cash,debit,credit,bank transfer
//all this are methods of payment that can use inheritance or interface to make it more flexible
//this will be updated to the database that has payment_id,booking_id,amount,payment_method,payment_date

import java.util.*;
import java.text.*;
import java.sql.*;
import java.sql.Date;
import java.io.*;





public class payments {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private Date paymentDate;

    // Constructor
    public payments(int paymentId, int bookingId, double amount, String paymentMethod, Date paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public double getAmount() {
        return amount;
        }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public Date getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    // Method to process payment

    public void processPayment() {
        // Logic to process payment
        System.out.println("Processing payment of " + amount + " using " + paymentMethod);
        // Here you can add code to update the database with payment details
    }
    // Method to validate payment details
    public boolean validatePaymentDetails() {
        // Logic to validate payment details
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return false;
        }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            System.out.println("Invalid payment method");
            return false;
        }
        return true;
    }
}
//types of payments
class CashPayment extends payments {
    public CashPayment(int paymentId, int bookingId, double amount, Date paymentDate) {
        super(paymentId, bookingId, amount, "Cash", paymentDate);
    }
}
class DebitPayment extends payments {
    public DebitPayment(int paymentId, int bookingId, double amount, Date paymentDate) {
        super(paymentId, bookingId, amount, "Debit", paymentDate);
    }
}
class CreditPayment extends payments {
    public CreditPayment(int paymentId, int bookingId, double amount, Date paymentDate) {
        super(paymentId, bookingId, amount, "Credit", paymentDate);
    }
}

class BankTransferPayment extends payments {
    public BankTransferPayment(int paymentId, int bookingId, double amount, Date paymentDate) {
        super(paymentId, bookingId, amount, "Bank Transfer", paymentDate);
    }
}
// Example usage
class Main {
    public static void main(String[] args) {
        // Create a new payment
        payments p1 = new payments(1, 101, 150.00, "Credit", new Date(System.currentTimeMillis()));
        
        // Validate payment details
        if (p1.validatePaymentDetails()) {
            // Process payment
            p1.processPayment();
        } else {
            System.out.println("Payment validation failed");
        }
    }
}


