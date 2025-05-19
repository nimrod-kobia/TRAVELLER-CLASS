import java.util.ArrayList;
import java.util.List;

public class Airport {

    private String name;
    private String iataCode;     
    private String icaoCode;     
    private String city;
    private String country;
    private int numberOfTerminals;
    private int numberOfRunways;
    private double latitude;
    private double longitude;
    private List<String> flights; 

    public Airport(String name, String iataCode, String icaoCode, String city, String country,
                   int numberOfTerminals, int numberOfRunways, double latitude, double longitude) {
        this.name = name;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.city = city;
        this.country = country;
        this.numberOfTerminals = numberOfTerminals;
        this.numberOfRunways = numberOfRunways;
        this.latitude = latitude;
        this.longitude = longitude;
        this.flights = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getNumberOfTerminals() {
        return numberOfTerminals;
    }

    public int getNumberOfRunways() {
        return numberOfRunways;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getFlights() {
        return flights;
    }

    public void addFlight(String flight) {
        flights.add(flight);
    }

    public void removeFlight(String flight) {
        flights.remove(flight);
    }

    public String toString() {
        return String.format("Airport: %s (%s), %s, %s", name, iataCode, city, country);
    }

    public static void main(String[] args) {
        Airport lax = new Airport(
            "Los Angeles International Airport",
            "LAX",
            "KLAX",
            "Los Angeles",
            "USA",
            9,
            4,
            33.9416,
            -118.4085
        );

        lax.addFlight("AA123");
        lax.addFlight("DL456");

        System.out.println(lax);
        System.out.println("Flights: " + lax.getFlights());
    }
}
