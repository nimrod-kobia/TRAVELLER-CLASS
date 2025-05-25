import java.util.Scanner;

// Interface for identification entities
interface Identification {
    String getID();
}

// Class implementing Identifiable interface for Passport
class Passport implements Identification {
    private String passportNumber;

    public Passport(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String getID() {
        return passportNumber;
    }
}

// Class implementing Identifiable interface for Visa
class Visa implements Identification {
    private String visaNumber;

    public Visa(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    @Override
    public String getID() {
        return visaNumber;
    }
}

// Abstract class Person with common attributes
abstract class Person {
    protected String name;
    protected String dob;
    protected String nationality;

    public Person(String name, String dob, String nationality) {
        this.name = name;
        this.dob = dob;
        this.nationality = nationality;
    }

    public abstract String getName();
    public abstract String getDOB();
    public abstract String getNationality();
}

// User class extending Person and using composition for Passport and Visa
class User extends Person {
    private Passport passport;
    private Visa visa;
    private String email;

    public User(String name, String dob, String nationality, Passport passport, Visa visa, String email) {
        super(name, dob, nationality);
        this.passport = passport;
        this.visa = visa;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    public String getPassportNumber() {
        return passport.getID();
    }

    public String getVisaNumber() {
        return visa.getID();
    }

    public void displayUserInfo() {
        System.out.println("\nUser Information:");
        System.out.println("Name: " + getName());
        System.out.println("Date of Birth: " + getDOB());
        System.out.println("Nationality: " + getNationality());
        System.out.println("Passport: " + getPassportNumber());
        System.out.println("Visa: " + getVisaNumber());
        System.out.println("Email: " + getEmail());
    }

    // Main method to collect user input and create a User
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = in.nextLine();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dob = in.nextLine();

        System.out.print("Enter nationality: ");
        String nationality = in.nextLine();

        System.out.print("Enter passport number: ");
        String passportNumber = in.nextLine();

        System.out.print("Enter visa number: ");
        String visaNumber = in.nextLine();

        System.out.print("Enter email: ");
        String email = in.nextLine();

        Passport passport = new Passport(passportNumber);
        Visa visa = new Visa(visaNumber);
        User user = new User(name, dob, nationality, passport, visa, email);

        user.displayUserInfo();

        in.close();
    }
}
