package database;

import java.sql.*;

public class PaymentDAO {
    public int addPayment(int bookingId, double amount, String paymentMethod, Timestamp paymentDate, String status) {
        String sql = "INSERT INTO payments (booking_id, amount, payment_method, payment_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, bookingId);
            pst.setDouble(2, amount);
            pst.setString(3, paymentMethod);
            pst.setTimestamp(4, paymentDate);
            pst.setString(5, status);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}