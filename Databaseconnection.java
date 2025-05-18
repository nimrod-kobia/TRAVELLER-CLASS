//     // Method to save user to database
//    public int saveToDatabase() throws SQLException {
//     String sql = "INSERT INTO passengers (user_name, date_of_birth, passport_number, user_visa) " +
//                  "VALUES (?, ?, ?, ?)";
    
//     try (Connection conn = DatabaseHelper.getConnection();
//          PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
//         stmt.setString(1, this.userName);
//         stmt.setDate(2, Date.valueOf(this.userDOB));
//         stmt.setString(3, this.userPassport);
//         stmt.setString(4, this.userVisa);
        
//         stmt.executeUpdate();
        
//         try (ResultSet rs = stmt.getGeneratedKeys()) {
//             if (rs.next()) {
//                 return rs.getInt(1); // Return generated passenger_id
//             }
//         }
//     }
//     return -1;
// }


 // Create and complete booking
        // Traveller traveller = new Traveller(user, flight);
        // if (traveller.completeBooking()) {
        //     System.out.println("\nBooking completed successfully!");
        //     traveller.displayFullBookingInfo();
        // } else {
        //     System.out.println("Booking failed. Please try again.");
        // }



          // Method to complete booking but work in progress
    // public boolean completeBooking() {
    //     try {
    //         int passengerId = user.saveToDatabase();
    //         this.booking_id = DatabaseHelper.saveBooking(
    //             passengerId, 
    //             flight.flightNumber(), 
    //             flight.seatNumber(), 
    //             flight.bagNumber(), 
    //             flight.bagWeight()
    //         );
    //         return booking_id > 0;
    //     } catch (SQLException e) {
    //         System.err.println("Booking failed: " + e.getMessage());
    //         return false;
    //     }
    // }



    
