// S - Single Responsibility Principle (SRP)
// Interface for identifiable entities, focusing solely on providing an ID.
interface Identifiable {
    String getID();
}

// S - Single Responsibility Principle (SRP)
// Class solely responsible for Passport details and providing its ID.
class Passport implements Identifiable {
    private String passportNumber;

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String getID() {
        return passportNumber;
    }
}

// S - Single Responsibility Principle (SRP)
// Class solely responsible for Visa details and providing its ID.
// This also demonstrates POLYMORPHISM as getID() is used differently but through a common interface.
class Visa implements Identifiable {
    private String visaNumber;

    public Visa(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    @Override
    public String getID() {
        return visaNumber;
    }
}

// S - Single Responsibility Principle (SRP)
// This interface defines the core properties of a Person.
// I - Interface Segregation Principle (ISP)
// We could have separate interfaces for different aspects if the 'Person'
// became too complex, e.g., 'Nameable', 'DOBReadable', etc. For this small example,
// one interface for basic person details is acceptable.
interface PersonDetails {
    String getName();
    String getDOB();
    String getNationality();
}

// O - Open/Closed Principle (OCP) & L - Liskov Substitution Principle (LSP)
// Person is now an interface, which makes it open for extension (new implementations)
// but closed for modification. Any class implementing Person will be substitutable
// where a PersonDetails is expected.
interface Person extends PersonDetails {
    // We can add methods specific to a Person here if needed,
    // keeping PersonDetails focused on just the basic properties.
    // For now, it just extends PersonDetails.
}


// S - Single Responsibility Principle (SRP)
// Class responsible for user-specific attributes and behavior.
// D - Dependency Inversion Principle (DIP)
// User depends on the 'Identifiable' abstraction for Passport and Visa,
// not on the concrete 'Passport' or 'Visa' classes directly for their IDs.
// Similarly, it depends on the 'Person' interface for core person details.
class User implements Person { // User "is a" Person
    private String name;
    private String dob;
    private String nationality;
    private Identifiable passport; // COMPOSITION: User "has a" Passport (through Identifiable interface)
    private Identifiable visa;     // COMPOSITION: User "has a" Visa (through Identifiable interface)
    private String email;

    // Constructor injected dependencies
    public User(String name, String dob, String nationality, Identifiable passport, Identifiable visa, String email) {
        this.name = name;
        this.dob = dob;
        this.nationality = nationality;
        this.passport = passport;
        this.visa = visa;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    // Using polymorphism and DIP here: we call getID() on the Identifiable reference.
    public String getPassportNumber() {
        return passport.getID();
    }

    public String getVisaNumber() {
        return visa.getID();
    }

    // S - Single Responsibility Principle (SRP) - This method's sole responsibility
    // is to display user information.
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

// Example Usage
public class UserTest {
    public static void main(String[] args) {
        // Create concrete implementations
        Passport userPassport = new Passport("AB1234567");
        Visa userVisa = new Visa("V-987654321");

        // Create a User instance, injecting dependencies
        User newUser = new User(
            "Alice Smith",
            "1990-05-15",
            "Canadian",
            userPassport, // Passport object
            userVisa,     // Visa object
            "alice.smith@example.com"
        );

        newUser.displayUserInfo();

        // Demonstrating Liskov Substitution Principle (LSP)
        // We can treat any Identifiable object generically
        Identifiable document1 = new Passport("XY7890123");
        Identifiable document2 = new Visa("V-112233445");

        System.out.println("\nGeneric Document IDs:");
        System.out.println("Document 1 ID: " + document1.getID());
        System.out.println("Document 2 ID: " + document2.getID());
    }
}