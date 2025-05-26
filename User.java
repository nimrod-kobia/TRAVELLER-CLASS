// Interface for identification entities
// This shows ABSTRACTION by defining a common behavior (getID) without implementation.
interface Identification {
    String getID(); // Abstract method to be implemented by specific ID types like Passport and Visa
}
// class uses encapsulation: passportNumber is private and accessed via getID() method
class Passport implements Identification {
    private String passportNumber; // Encapsulated data

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    // Implementation of abstract method from Identification interface
    @Override
    public String getID() {
        return passportNumber;
    }

// POLYMORPHISM: same interface method (getID) is used differently for Visa
class Visa implements Identification {
    private String visaNumber; // Encapsulated data

    public Visa(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    @Override
    public String getID() {
        return visaNumber;
    }
}
// Abstract class Person with common attributes to be inherited by user class
// class shows abstraction and inheritance
abstract class Person {
    protected String name;
    protected String dob;
    protected String nationality;

    // Constructor to initialize common attributes
    public Person(String name, String dob, String nationality) {
        this.name = name;
        this.dob = dob;
        this.nationality = nationality;
    }

    // Abstract methods to be implemented by subclasses (e.g., User)
    public abstract String getName();
    public abstract String getDOB();
    public abstract String getNationality();

//class shows inheritance and composition
class User extends Person { //User "is a" person
    private Passport passport; // COMPOSITION: User "has a" Passport
    private Visa visa;       
    private String email; 

    public User(String name, String dob, String nationality, Passport passport, Visa visa, String email) {
        super(name, dob, nationality); // Calling the superclass constructor
        this.passport = passport;
        this.visa = visa;
        this.email = email;
    }

    // Implementing abstract methods from Person
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public String getDOB() {
        return dob;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Using polymorphism
    public String getPassportNumber() {
        return passport.getID();
    }

    public String getVisaNumber() {
        return visa.getID();
    }

    // Display method to show user info
    public void displayUserInfo() {
        System.out.println("\nUser Information:");
        System.out.println("Name: " + getName());
        System.out.println("Date of Birth: " + getDOB());
        System.out.println("Nationality: " + getNationality());
        System.out.println("Passport: " + getPassportNumber());
        System.out.println("Visa: " + getVisaNumber());
        System.out.println("Email: " + getEmail());
    }

}
}
}