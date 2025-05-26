// Independent FlightSeat class
public class FlightSeat {
    private String seatNumber;
    private String seatClass; // e.g., "Economy", "Business", "First"
    private boolean isBooked;
    private double price;

    // Constructor
    public FlightSeat(String seatNumber, String seatClass, double price) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
        this.isBooked = false; // Seat is available by default
    }

    // Getters
    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public double getPrice() {
        return price;
    }

    // Book the seat
    public boolean bookSeat() {
        if (!isBooked) {
            isBooked = true;
            System.out.println("Seat " + seatNumber + " booked successfully.");
            return true;
        } else {
            System.out.println("Seat " + seatNumber + " is already booked.");
            return false;
        }
    }

    // Display seat details
    public void displaySeatInfo() {
        System.out.println("Seat: " + seatNumber +
                           " | Class: " + seatClass +
                           " | Price: Ksh " + price +
                           " | Status: " + (isBooked ? "Booked" : "Available"));
    }
}
