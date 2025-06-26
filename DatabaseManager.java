import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3309/traveller_class";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";
    
    private Connection connection;
    
    public DatabaseManager() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Generic method to execute update queries (INSERT, UPDATE, DELETE)
    private int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        }
    }
    
    // Generic method to execute queries that return a ResultSet
    private ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        return statement.executeQuery();
    }
    
    // ========== Passport Operations ==========
    public boolean addPassport(String passportNumber, Date issueDate, Date expiryDate, String issuingCountry) {
        String query = "INSERT INTO passports (passport_number, issue_date, expiry_date, issuing_country) VALUES (?, ?, ?, ?)";
        try {
            return executeUpdate(query, passportNumber, issueDate, expiryDate, issuingCountry) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ========== Visa Operations ==========
    public boolean addVisa(String visaNumber, String visaType, Date issueDate, Date expiryDate, String issuingCountry) {
        String query = "INSERT INTO visas (visa_number, visa_type, issue_date, expiry_date, issuing_country) VALUES (?, ?, ?, ?, ?)";
        try {
            return executeUpdate(query, visaNumber, visaType, issueDate, expiryDate, issuingCountry) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ========== User Operations ==========
    /**
 * Adds a new user to the database with proper validation and duplicate checking
 * 
 * @param fullname User's full name (required)
 * @param dob Date of birth (required)
 * @param nationality Nationality (required)
 * @param passportNumber Passport number (optional)
 * @param visaNumber Visa number (optional)
 * @param email Email address (required, must be unique)
 * @return The generated user ID if successful, -1 if failed
 * @throws SQLException if database error occurs
 * @throws IllegalArgumentException if validation fails
 */
public int addUser(String fullname, Date dob, String nationality, 
                  String passportNumber, String visaNumber, String email) 
                  throws SQLException, IllegalArgumentException {
    
    // Validate required fields
    if (fullname == null || fullname.trim().isEmpty()) {
        throw new IllegalArgumentException("Full name cannot be empty");
    }
    if (dob == null) {
        throw new IllegalArgumentException("Date of birth cannot be null");
    }
    if (nationality == null || nationality.trim().isEmpty()) {
        throw new IllegalArgumentException("Nationality cannot be empty");
    }
    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("Email cannot be empty");
    }

    // Check for duplicate email
    if (isEmailExists(email)) {
        throw new IllegalArgumentException("Email '" + email + "' already exists");
    }

    // Validate passport if provided
    if (passportNumber != null && !passportNumber.trim().isEmpty()) {
        if (!isPassportExists(passportNumber)) {
            throw new IllegalArgumentException("Passport '" + passportNumber + "' does not exist in passports table");
        }
        if (isPassportInUse(passportNumber)) {
            throw new IllegalArgumentException("Passport '" + passportNumber + "' is already associated with another user");
        }
    }

    // Validate visa if provided
    if (visaNumber != null && !visaNumber.trim().isEmpty()) {
        if (!isVisaExists(visaNumber)) {
            throw new IllegalArgumentException("Visa '" + visaNumber + "' does not exist in visas table");
        }
        if (isVisaInUse(visaNumber)) {
            throw new IllegalArgumentException("Visa '" + visaNumber + "' is already associated with another user");
        }
    }

    String query = "INSERT INTO users (fullname, dob, nationality, passport_number, visa_number, email) " +
                  "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, fullname);
        statement.setDate(2, dob);
        statement.setString(3, nationality);
        statement.setString(4, passportNumber);
        statement.setString(5, visaNumber);
        statement.setString(6, email);

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained");
            }
        }
    } catch (SQLException e) {
        // Handle specific constraint violation errors
        if (e.getSQLState() != null && e.getSQLState().equals("23000")) {
            if (e.getMessage().contains("email")) {
                throw new IllegalArgumentException("Email '" + email + "' already exists", e);
            } else if (e.getMessage().contains("passport_number")) {
                throw new IllegalArgumentException("Passport '" + passportNumber + "' is already in use", e);
            } else if (e.getMessage().contains("visa_number")) {
                throw new IllegalArgumentException("Visa '" + visaNumber + "' is already in use", e);
            }
        }
        throw e; // Re-throw other SQL exceptions
    }
}

// Helper methods for validation
private boolean isEmailExists(String email) throws SQLException {
    String query = "SELECT COUNT(*) FROM users WHERE email = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, email);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}

private boolean isPassportExists(String passportNumber) throws SQLException {
    if (passportNumber == null) return false;
    String query = "SELECT COUNT(*) FROM passports WHERE passport_number = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, passportNumber);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}

private boolean isPassportInUse(String passportNumber) throws SQLException {
    if (passportNumber == null) return false;
    String query = "SELECT COUNT(*) FROM users WHERE passport_number = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, passportNumber);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}

private boolean isVisaExists(String visaNumber) throws SQLException {
    if (visaNumber == null) return false;
    String query = "SELECT COUNT(*) FROM visas WHERE visa_number = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, visaNumber);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}

private boolean isVisaInUse(String visaNumber) throws SQLException {
    if (visaNumber == null) return false;
    String query = "SELECT COUNT(*) FROM users WHERE visa_number = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, visaNumber);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
    
    // ========== Aircraft Operations ==========
    public boolean addAircraft(String registrationNumber, String model, String manufacturer, 
                             int seatingCapacity, double maxTakeoffWeight, double aircraftRange, int yearOfManufacture) {
        String query = "INSERT INTO aircraft (registrationNumber, model, manufacturer, seatingCapacity, " +
                      "maxTakeoffWeight, aircraft_range, yearOfManufacture) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return executeUpdate(query, registrationNumber, model, manufacturer, seatingCapacity, 
                                maxTakeoffWeight, aircraftRange, yearOfManufacture) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ========== Flight Operations ==========
    public int addFlight(String flightNumber, String departureLocation, String arrivalLocation, 
                       Timestamp departureTime, Timestamp arrivalTime, double price) {
        String query = "INSERT INTO flight (flightNumber, departureLocation, arrivalLocation, " +
                      "departureTime, arrivalTime, price) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, flightNumber);
            statement.setString(2, departureLocation);
            statement.setString(3, arrivalLocation);
            statement.setTimestamp(4, departureTime);
            statement.setTimestamp(5, arrivalTime);
            statement.setDouble(6, price);
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // ========== Booking Operations ==========
    public int createBooking(int passengerId, String flightId, String seatId, Timestamp bookingTime, 
                           double totalPrice, String bookingStatus, String paymentStatus) {
        String query = "INSERT INTO booking (passenger_id, flightId, seatId, bookingTime, " +
                      "totalPrice, bookingStatus, paymentStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, passengerId);
            statement.setString(2, flightId);
            statement.setString(3, seatId);
            statement.setTimestamp(4, bookingTime);
            statement.setDouble(5, totalPrice);
            statement.setString(6, bookingStatus);
            statement.setString(7, paymentStatus);
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // ========== Payment Operations ==========
    public boolean recordPayment(int bookingId, double amount, String paymentMethod, Timestamp paymentDate) {
        String query = "INSERT INTO payments (bookingId, amount, paymentMethod, paymentDate) VALUES (?, ?, ?, ?)";
        try {
            return executeUpdate(query, bookingId, amount, paymentMethod, paymentDate) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ========== Getter Methods ==========
    public ResultSet getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        return executeQuery(query, userId);
    }
    
    public ResultSet getFlightById(int flightId) throws SQLException {
        String query = "SELECT * FROM flight WHERE id = ?";
        return executeQuery(query, flightId);
    }
    
    public ResultSet getBookingById(int bookingId) throws SQLException {
        String query = "SELECT * FROM booking WHERE BookingId = ?";
        return executeQuery(query, bookingId);
    }
    
    public ResultSet getPaymentsForBooking(int bookingId) throws SQLException {
        String query = "SELECT * FROM payments WHERE bookingId = ?";
        return executeQuery(query, bookingId);
    }
    
    // ========== Update Methods ===========
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        String query = "UPDATE booking SET bookingStatus = ? WHERE BookingId = ?";
        try {
            return executeUpdate(query, newStatus, bookingId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatePaymentStatus(int bookingId, String newStatus) {
        String query = "UPDATE booking SET paymentStatus = ? WHERE BookingId = ?";
        try {
            return executeUpdate(query, newStatus, bookingId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ========== Close Connection ==========
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Additional helper methods can be added as needed
    
    // Example of a more complex query
    public List<Integer> getBookingsByUser(int userId) {
        List<Integer> bookings = new ArrayList<>();
        String query = "SELECT BookingId FROM booking WHERE passenger_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                bookings.add(rs.getInt("BookingId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bookings;
    }

// Main.java
public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        
        try {
            // 1. Add passport if not exists
            if (!dbManager.isPassportExists("P12345678")) {
                dbManager.addPassport("P12345675", Date.valueOf("2020-01-15"), 
                                    Date.valueOf("2030-01-15"), "USA");
            }
            
            // 2. Add user
            int userId = dbManager.addUser("John ", Date.valueOf("1985-05-20"), 
                                         "American", null, null, "john@example.com");
            
            System.out.println("Created user with ID: " + userId);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbManager.close();
        }
    }
}
  

}
