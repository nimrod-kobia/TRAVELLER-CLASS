

// Interface for identifiable entities

import java.util.Map;

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

    public Map<String, String> getAllUserData() {
        Map<String, String> userData = new java.util.HashMap<>();
        userData.put("name", getName());
        userData.put("dob", getDOB());
        userData.put("nationality", getNationality());
        userData.put("email", getEmail());
        userData.put("passport", getPassportNumber());
        userData.put("visa", getVisaNumber());
        return userData;
    }

     public static void main(String[] args) {
        // Create a new user with all attributes
        Passport passport = new Passport("AB1234567");
        Visa visa = new Visa("US20230001");
        User user = new User("John Doe", "1990-05-15", "American", passport, visa, "john.doe@example.com");
        
        // Save complete user to database
        DatabaseManager.saveUser(user);
        
        // Load complete user from database by email
        User loadedUser = DatabaseManager.loadUser("john.doe@example.com");
        if (loadedUser != null) {
            loadedUser.displayUserInfo();
        }
        
        // Or load by ID if you have it
        // User loadedById = DatabaseManager.loadUser(1);
    }
}