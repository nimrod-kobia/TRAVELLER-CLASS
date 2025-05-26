import java.sql.*;

public class passengerDAO {

    public int addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (user_name, date_of_birth, passport_number, nationality, email, phone, user_visa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rows = 0;

        try (Connection con = DBHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, passenger.getUserName());
            pst.setDate(2, passenger.getDateOfBirth());
            pst.setString(3, passenger.getPassportNumber());
            pst.setString(4, passenger.getNationality());
            pst.setString(5, passenger.getEmail());
            pst.setString(6, passenger.getPhone());
            pst.setString(7, passenger.getUserVisa());

            rows = pst.executeUpdate();

            if (rows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        passenger.setPassengerId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public Passenger getPassengerById(int passengerId) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        Passenger passenger = null;

        try (Connection con = DBHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, passengerId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    passenger = new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("user_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("passport_number"),
                        rs.getString("nationality"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("user_visa")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passenger;
    }
}
