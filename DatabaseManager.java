import java.sql.*;


public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3309/traveller_class";
    private static final String USER = "root";
    private static final String PASS = "12345678";

    // Method to save a complete User to the database
    public static void saveUser(User user) {
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // First save passport if not exists
            savePassport(conn, user.getPassportNumber());
            
            // Then save visa if not exists
            saveVisa(conn, user.getVisaNumber());
            
            // Prepare SQL for User with all attributes
            String sql = "INSERT INTO users (fullname, dob, nationality,passport_number, visa_number, email ) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getName());
                 // Validate and convert DOB
            String dobString = user.getDOB();
            if (dobString == null || dobString.isEmpty()) {
                throw new IllegalArgumentException("Date of Birth cannot be null or empty");
            }



                pstmt.setDate(2, Date.valueOf(user.getDOB())); // Convert String to SQL Date
                pstmt.setString(3, user.getNationality());
                pstmt.setString(4, user.getPassportNumber());
                pstmt.setString(5, user.getVisaNumber());
                pstmt.setString(6, user.getEmail());
                
                int affectedRows = pstmt.executeUpdate();
                
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            System.out.println("User saved successfully with ID: " + id);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
    
    private static void savePassport(Connection conn, String passportNumber) throws SQLException {
        String sql = "INSERT IGNORE INTO passports (passport_number) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, passportNumber);
            pstmt.executeUpdate();
        }
    }
    
    private static void saveVisa(Connection conn, String visaNumber) throws SQLException {
        String sql = "INSERT IGNORE INTO visas (visa_number) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, visaNumber);
            pstmt.executeUpdate();
        }
    }
    
    // Method to load a complete User from the database by email
    public static User loadUser(String email) {
        String sql = "SELECT * FROM users u " +
                     "LEFT JOIN passports p ON u.passport_number = p.passport_number " +
                     "LEFT JOIN visas v ON u.visa_number = v.visa_number " +
                     "WHERE u.email = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Get all fields from the database
                int id = rs.getInt("id");
                String name = rs.getString("fullname");
                String dob = rs.getDate("dob").toString();
                String nationality = rs.getString("nationality");
                String passportNumber = rs.getString("passport_number");
                String visaNumber = rs.getString("visa_number");
                
                // Create passport and visa objects
                Passport passport = new Passport(passportNumber);
                Visa visa = new Visa(visaNumber);
                
                // Create and return user with all attributes
                User user = new User(name, dob, nationality, passport, visa, email);
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error loading user: " + e.getMessage());
        }
        return null;
    }
    
    // Additional method to load user by ID
    public static User loadUser(int id) {
        String sql = "SELECT * FROM users u " +
                     "LEFT JOIN passports p ON u.passport_number = p.passport_number " +
                     "LEFT JOIN visas v ON u.visa_number = v.visa_number " +
                     "WHERE u.id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                String dob = rs.getDate("dob").toString();
                String nationality = rs.getString("nationality");
                String email = rs.getString("email");
                String passportNumber = rs.getString("passport_number");
                String visaNumber = rs.getString("visa_number");
                
                Passport passport = new Passport(passportNumber);
                Visa visa = new Visa(visaNumber);
                
                return new User(name,dob, nationality,  passport, visa, email);
        }} catch (SQLException e) {
            System.err.println("Error loading user: " + e.getMessage());
        }
        return null;
    }

    //testing connectivity to database by inputting a user
    public static void main(String[] args) {

    //     Passport p1=new Passport("123567");
    //     Visa v1=new Visa("456789");

    //     User u1=new User("maiek","1999-10-10", "American",  p1, v1, "moale@gmail.com");
    //     System.out.println("DOB received: " + u1.getDOB());
    //     saveUser(u1);
    //     loadUser("moale@gmail.com");
    //     User user12 = new User("John Doe", "1990-01-01", "American",
    //                          new Passport("A12345678"), 
    //                          new Visa("V98765432"),
    //                          "louisngatia47@gmail.com");

    // saveUser(user12);

    //     User user2=new User("MArk", "1999-10-10", "Kenyan", new Passport("A66532345fff"), new Visa("ANIQNS1233"), "jamesmaiwdn@gmail.com");                     
    //     saveUser(user2);
        User loadedUser = loadUser("moale@gmail.com");
        if (loadedUser != null) {
            System.out.println("User loaded successfully: " + loadedUser.getName());
            }
        else {
            System.out.println("User not found.");
    //         }
        

            loadUser(15);

    }                             
}
}


                    

