import java.util.Scanner;

// Abstraction through interface
interface AirlineInfo {
    String getAirlineDetails();
}

// Encapsulation + Inheritance + Polymorphism
class Airlines implements AirlineInfo {
    private Integer airlineId;
    private String airlineName;
    private String airlineCode;
    private String headquarters;
    private String contactNumber;
    private String website;

    public Airlines(Integer airlineId, String airlineName, String airlineCode,
                    String headquarters, String contactNumber, String website) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.headquarters = headquarters;
        this.contactNumber = contactNumber;
        this.website = website;
    }

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

public class AirlineApp{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Admin check first
        System.out.println("Are you an admin? (yes/no)");
        String response = scanner.nextLine().toLowerCase();

        if (!response.equals("yes")) {
            System.out.println("Access denied. Only admins can input airline details.");
            System.exit(0);
        }
else{


        //Allow admin to input airline info
        System.out.println("\nAccess granted. Please enter Full-Service Airline Details:");

        System.out.print("Airline ID: ");
        int id = scanner.nextInt(); scanner.nextLine();

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

        // Create object and display info
        Airlines airline = new Airlines(id, name, code, hq, contact, website);
        printAirlineInfo(airline);

        scanner.close();
    }
    }
    // Polymorphic method (works for any class that implements AirlineInfo)
    public static void printAirlineInfo(AirlineInfo airline) {
        System.out.println("Airline Information:");
        System.out.println(airline.getAirlineDetails());
    }
}
