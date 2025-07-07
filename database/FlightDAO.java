package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Flight;

public class FlightDAO {
    public int addFlight(Flight flight) {
        String sql = "INSERT INTO flights (flight_number, departure_location, arrival_location, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, flight.getFlightNumber());
            pst.setString(2, flight.getDepartureLocation());
            pst.setString(3, flight.getArrivalLocation());
            pst.setDouble(4, flight.getPrice());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT flight_number, departure_location, arrival_location, price FROM flights";
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                flights.add(new Flight(
                    rs.getString("flight_number"),
                    rs.getString("departure_location"),
                    rs.getString("arrival_location"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}