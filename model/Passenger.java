package model;

public class Passenger extends User {
    private Passport passport;
    private Visa visa;
    private Baggage baggage;

    public Passenger(String name, String email, String phoneNumber, String passportNumber, String nationality) {
        super(name, email, phoneNumber, passportNumber, nationality);
    }

    public Passport getPassport() { return passport; }
    public Visa getVisa() { return visa; }
    public Baggage getBaggage() { return baggage; }
}
