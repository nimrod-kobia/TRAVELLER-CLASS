package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Booking;

public class BookingDAO {
    public int addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, flight_id, seat_id, booking_time, total_price, booking_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, booking.getUserId());
            pst.setString(2, booking.getFlightId());
            pst.setString(3, booking.getFlightId());
            pst.setTimestamp(4, Timestamp.valueOf(booking.getBookingStatus()));
            pst.setDouble(5, booking.getTotalPrice());
            pst.setString(6, booking.getBookingStatus());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(null, sql, sql, 0, sql)
                    // rs.getInt("booking_id"),
                    // rs.getInt("user_id"),
                    // rs.getInt("flight_id"),
                    // rs.getString("seat_id"),
                    // rs.getTimestamp("booking_time").toLocalDateTime(),
                    // rs.getDouble("total_price"),
                    // rs.getString("booking_status")
                    // rs.getString("Payment_status");
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}