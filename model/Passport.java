package model;

public class Passport {
    private String passportNumber;
    private String nationality;

    public Passport(String passportNumber, String nationality) {
        this.passportNumber = passportNumber;
        this.nationality = nationality;
    }

    public String getPassportNumber() { return passportNumber; }
    public String getNationality() { return nationality; }
}
