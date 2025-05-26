import java.util.Scanner;

// This interface defines a method without implementation.
// Any class that implements this must define how to return airline details.
interface AirlineInfo {
    String getAirlineDetails(); 
}

// encapsulation: all fields are private and accessed through public methods.
// polymorphism: it overrides getAirlineDetails() from the interface.
class Airlines implements AirlineInfo {
    private Integer airlineId;
    private String airlineName;
    private String airlineCode;
    private String headquarters;
    private String contactNumber;
    private String website;

    // Constructor: used to initialize all airline details
    public Airlines(Integer airlineId, String airlineName, String airlineCode,
                    String headquarters, String contactNumber, String website) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.headquarters = headquarters;
        this.contactNumber = contactNumber;
        this.website = website;
    }

    // Overriding the method from the interface, polymorphism
    @Override
    public String getAirlineDetails() {
        return "Airline:\n" +
               "ID: " + airlineId + "\n" +
               "Name: " + airlineName + "\n" +
               "Code: " + airlineCode + "\n" +
               "Headquarters: " + headquarters + "\n" +
               "Contact: " + contactNumber + "\n" +
               "Website: " + website;
    }
}

public class AirlineApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user if they are an admin before proceeding
        System.out.println("Are you an admin? (yes/no)");
        String response = scanner.nextLine().toLowerCase();

        // If not admin, program exits
        if (!response.equals("yes")) {
            System.out.println("Access denied. Only admins can input airline details.");
            System.exit(0);
        } else {
            // If admin, allow input of airline details
            System.out.println("\nAccess granted. Please enter Airline Details:");

            System.out.print("Airline ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Airline Name: ");
            String name = scanner.nextLine();

            System.out.print("Airline Code: ");
            String code = scanner.nextLine();

            System.out.print("Headquarters: ");
            String hq = scanner.nextLine();

            System.out.print("Contact Number: ");
            String contact = scanner.nextLine();

            System.out.print("Website: ");
            String website = scanner.nextLine();

            // Create Airlines object using the provided details
            Airlines airline = new Airlines(id, name, code, hq, contact, website);

            // Display the airline info using a polymorphic method
            printAirlineInfo(airline);

            scanner.close();
        }
    }

    // This method works for any object that implements AirlineInfo e.g new cargoairline class
    public static void printAirlineInfo(AirlineInfo airline) {
        System.out.println("Airline Information:");
        System.out.println(airline.getAirlineDetails());
    }
}
