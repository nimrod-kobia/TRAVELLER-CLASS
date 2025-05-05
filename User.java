import java.util.ArrayList;
import java.util.List;

// Part 1: User Class
class User {
    // User details
    private String userName;
    private String userDOB;
    private String userVisa;
    private String userPassport;

    // Constructor to initialize user details
    public User(String userName, String userDOB, String userVisa, String userPassport) {
        this.userName = userName;
        this.userDOB = userDOB;
        this.userVisa = userVisa;
        this.userPassport = userPassport;
    }

    // Getter methods to return user's information
    public String userName() {
        return userName;
    }

    public String userDOB() {
        return userDOB;
    }

    public String userVisa() {
        return userVisa;
    }

    public String userPassport() {
        return userPassport;
    }
}

// Part 2: Flight Class
class Flight {
    private String flightNumber;
    private String seatNumber;
    private String arrivalLocation;
    private String currentLocation;
    private String bagNumber;
    private double bagWeight;

    private List<String> availableSeats;

    // Constructor initializes current location and generates seat list
    public Flight(String currentLocation) {
        this.currentLocation = currentLocation;
        availableSeats = new ArrayList<>();

        // Generate seat map: Rows 1–30, seats A–F
        char[] seatLetters = {'A', 'B', 'C', 'D', 'E', 'F'};
        for (int row = 1; row <= 30; row++) {
            for (char letter : seatLetters) {
                availableSeats.add(row + String.valueOf(letter));
            }
        }
    }

    // Getter methods
    public String flightNumber() {
        return flightNumber;
    }

    public String seatNumber() {
        return seatNumber;
    }

    public String arrivalLocation() {
        return arrivalLocation;
    }

    public String currentLocation() {
        return currentLocation;
    }

    public String bagNumber() {
        return bagNumber;
    }

    public double bagWeight() {
        return bagWeight;
    }

    public List<String> availableSeats() {
        return availableSeats;
    }

    // Setters / actions
    public void flightPicked(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void seatBooking(String seatNumber) {
        if (availableSeats.contains(seatNumber)) {
            this.seatNumber = seatNumber;
            availableSeats.remove(seatNumber);
        } else {
            System.out.println("Seat " + seatNumber + " is not available.");
        }
    }

    public void arrivalLocation(String destination) {
        this.arrivalLocation = destination;
    }

    public void baggageInfo(String bagNumber, double weight) {
        this.bagNumber = bagNumber;
        this.bagWeight = weight;
    }
}
