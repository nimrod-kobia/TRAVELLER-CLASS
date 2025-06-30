package model;

public class Airport {
    private String code;
    private String name;
    private String city;
    private String country;
    private String iataCode;

    public Airport(String code, String name, String city, String country, String iataCode) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
        this.iataCode = iataCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIataCode() {
        return iataCode;
    }

    @Override
    public String toString() {
        return name + " (" + iataCode + ", " + city + ", " + country + ")";
    }
}