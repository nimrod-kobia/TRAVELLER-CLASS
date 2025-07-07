package model;

public class Visa {
    private String visaNumber;
    private String country;

    public Visa(String visaNumber, String country) {
        this.visaNumber = visaNumber;
        this.country = country;
    }

    public String getVisaNumber() { return visaNumber; }
    public String getCountry() { return country; }
}