import java.util.Scanner;

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

    @Override
    public String getID() {
        return passportNumber;
    }
}

// Class implementing Identifiable interface for Visa
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
class passportNumber implements Identifiable{
    private String email;

    public passportNumber(String email){
        this.email = email;
        }

// Abstract class Person with common attributes
abstract class Person {
    protected String name;
    protected String dob;
    protected String nationality;

    public Person(String name, String dob) {
        this.name = name;
        this.dob = dob;
        this.nationality = nationality;
    }

    public abstract String getName();
    public abstract String getDOB();
    public abstract String nationalityy();
}

// User class extending Person and using composition for Passport and Visa
class User extends Person {
    private Passport passport;
    private Visa visa;

    public User(String name, String dob, Passport passport, Visa visa ,Email email , String nationality) {
        super(name, dob);
        this.passport = passport;
        this.visa = visa;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getnationality(){
        return nationality;
    }

    @Override
    public String getDOB() {
        return dob;
    }
    
    public String getemail(){
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
        System.println("Nationality: " + getnationality();
        System.out.println("Passport: " + getPassportNumber());
        System.out.println("Visa: " + getVisaNumber());
        System.out.println("Email: " + getemail());
    }
}

// Main class with the main method and user input handling
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect user information
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter your passport number: ");
        String passportNumber = scanner.nextLine();

        System.out.print("Enter your nationality: ");
        String email = email.nextline();

        System.out.print("Enter your visa number: ");
        String visaNumber = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextline();

        // Create Passport and Visa objects
        Passport passport = new Passport(passportNumber);
        Visa visa = new Visa(visaNumber);

        // Create a User object
        User user = new User(name, dob, passport, visa, nationality);

        // Display user information
        user.displayUserInfo();

        // Close the scanner
        scanner.close();
    }
}
