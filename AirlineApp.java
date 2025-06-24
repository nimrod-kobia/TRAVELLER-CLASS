import java.util.Scanner;
// S - Single Responsibility Principle (SRP): This interface is solely responsible for defining.
interface AirlineInfo {
    String getAirlineDetails();
}

// S - Single Responsibility Principle (SRP): This interface is for managing user input.
interface InputHandler {
    String promptForString(String message);
    int promptForInt(String message);
    void close();
}

// S - Single Responsibility Principle (SRP): This interface is for handling output.

interface OutputHandler {
    void displayMessage(String message);
    void displayAirlineInfo(AirlineInfo airline);
}

// S - Single Responsibility Principle (SRP): This interface is for the core business logic of

interface AirlineService {
    AirlineInfo createAirline(int id, String name, String code, String headquarters, String contact, String website);
}

// S - Single Responsibility Principle (SRP): This class is solely responsible for
// representing an Airlines entity and providing its details.
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

    // Overriding the method from the interface, demonstrating polymorphism
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

// S - Single Responsibility Principle (SRP): Handles all console input.
class ConsoleInputHandler implements InputHandler {
    private Scanner scanner;

    public ConsoleInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String promptForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    @Override
    public int promptForInt(String message) {
        System.out.print(message);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    @Override
    public void close() {
        scanner.close();
    }
}

// S - Single Responsibility Principle (SRP): Handles all console output.
// O - Open/Closed Principle (OCP): The `displayAirlineInfo` method is open for extension
// because it accepts any `AirlineInfo` implementation, allowing new types of airline
// information to be displayed without modifying this class.
class ConsoleOutputHandler implements OutputHandler {
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayAirlineInfo(AirlineInfo airline) {
        System.out.println("Airline Information:");
        System.out.println(airline.getAirlineDetails());
    }
}

// S - Single Responsibility Principle (SRP): Responsible for creating AirlineInfo objects.
// D - Dependency Inversion Principle (DIP): High-level modules depend on this abstraction.
class DefaultAirlineService implements AirlineService {
    @Override
    public AirlineInfo createAirline(int id, String name, String code, String headquarters, String contact, String website) {
        return new Airlines(id, name, code, headquarters, contact, website);
    }
}

// --- Main Application Class ---

// S - Single Responsibility Principle (SRP): Its main job is to orchestrate the application flow.
// D - Dependency Inversion Principle (DIP): It depends on abstractions (interfaces)
// for input, output, and airline service, not concrete implementations.
public class AirlineApp {
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    private AirlineService airlineService;

    // D - Dependency Inversion Principle (DIP): Constructor Injection.
    // The AirlineApp depends on abstractions (interfaces) rather than concrete classes.
    // This makes the class more flexible and testable.
    public AirlineApp(InputHandler inputHandler, OutputHandler outputHandler, AirlineService airlineService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.airlineService = airlineService;
    }

    public void run() {
        outputHandler.displayMessage("Are you an admin? (yes/no)");
        String response = inputHandler.promptForString("").toLowerCase();

        if (!response.equals("yes")) {
            outputHandler.displayMessage("Access denied. Only admins can input airline details.");
            // System.exit(0); // Consider throwing an exception instead of exiting directly in production code
        } else {
            outputHandler.displayMessage("\nAccess granted. Please enter Airline Details:");

            int id = inputHandler.promptForInt("Airline ID: ");
            String name = inputHandler.promptForString("Airline Name: ");
            String code = inputHandler.promptForString("Airline Code: ");
            String hq = inputHandler.promptForString("Headquarters: ");
            String contact = inputHandler.promptForString("Contact Number: ");
            String website = inputHandler.promptForString("Website: ");

            // Using the AirlineService abstraction to create the airline object
            AirlineInfo airline = airlineService.createAirline(id, name, code, hq, contact, website);

            // Display the airline info using the OutputHandler abstraction
            outputHandler.displayAirlineInfo(airline);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new ConsoleInputHandler(scanner);
        OutputHandler outputHandler = new ConsoleOutputHandler();
        AirlineService airlineService = new DefaultAirlineService();

        // Instantiate the application with injected dependencies
        AirlineApp app = new AirlineApp(inputHandler, outputHandler, airlineService);
        app.run(); // Run the main application logic

        inputHandler.close(); // Close the scanner when done
    }
}