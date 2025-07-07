package model;

public class User {
    private String name;
    private String email;
    private String phone;
    private String passportNumber;
    private String nationality;

    public User(String name, String email, String phone, String passportNumber, String nationality) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassportNumber() { return passportNumber; }
    public String getNationality() { return nationality; }
    public String getPhoneNumber() { return phone; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}