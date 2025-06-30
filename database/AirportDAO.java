package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Airport;

public class AirportDAO {
    public int addAirport(Airport airport) {
        String sql = "INSERT INTO airports (code, name, city) VALUES (?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, airport.getCode());
            pst.setString(2, airport.getName());
            pst.setString(3, airport.getCity());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT code, name, city FROM airports";
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                airports.add(new Airport(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getString("country"),
                    rs.getString("iata_code")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }
}