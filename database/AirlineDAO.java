package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Airline;
import model.Airport;
import model.Flight;

public class AirlineDAO {
    public int addAirline(Airline airline) {
        String sql = "INSERT INTO airlines (code, name) VALUES (?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, airline.getCode());
            pst.setString(2, airline.getName());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Airline> getAllAirlines() {
        List<Airline> airlines = new ArrayList<>();
        String sql = "SELECT code, name FROM airlines";
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                airlines.add(new Airline(rs.getString("name"), rs.getString("code")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlines;
    }

    public static void main(String[] args) {
        AirlineDAO airlineDAO = new AirlineDAO();
        airlineDAO.addAirline(new Airline("Kenya Airways", "KQ"));
       

        AirportDAO airportDAO = new AirportDAO();
        airportDAO.addAirport(new Airport("NBO", "Jomo Kenyatta", "Nairobi", "Kenya", "NBO"));
       

        FlightDAO flightDAO = new FlightDAO();
        flightDAO.addFlight(new Flight("KQ101", "Nairobi", "London", 8500));
        
    }
}