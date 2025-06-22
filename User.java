

// Interface for identifiable entities
interface Identifiable {
    String getID();
}

// Class implementing Identifiable interface for Passport
class Passport implements Identifiable {
    private String passportNumber;

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    // Implementation of abstract method from Identifiable interface
    @Override
    public String getID() {
        return passportNumber;
    }
}

// Demonstrates POLYMORPHISM: same interface method (getID) is used differently for Visa
class Visa implements Identifiable {
    private String visaNumber; // Encapsulated data

    public Visa(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    @Override
    public String getID() {
        return visaNumber;
    }
}

// Abstract class Person with common attributes to be inherited by User class
// Shows abstraction and inheritance
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

    // Abstract methods to be implemented by subclasses
    public abstract String getName();
    public abstract String getDOB();
    public abstract String getNationality();
}

// Class shows inheritance and composition
class User extends Person { // User "is a" Person
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
    public String getDOB() {
        return dob;
    }

    @Override
    public String getNationality() {
        return nationality;
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


