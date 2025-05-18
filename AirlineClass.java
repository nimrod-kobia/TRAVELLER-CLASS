import java.net.URL;

// Interface for airline-related information
interface AirlineInfo {
    String getAirlineDetails();
}

public class Airlines implements AirlineInfo {
    private String airlineId;
    private String airlineName;
    private String airlineCode;
    private String headquarters;
    private String contactNumber;
    private URL website;

    // Constructor to initialize all attributes
    public Airlines(String airlineId, String airlineName, String airlineCode, String headquarters, String contactNumber, URL website) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.headquarters = headquarters;
        this.contactNumber = contactNumber;
        this.website = website;
    }

    // Getter methods
    public String getAirlineId() {
        return airlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public URL getWebsite() {
        return website;
    }

    // Overridden method from AirlineInfo interface
    @Override
    public String getAirlineDetails() {
        return "Airline Name: " + airlineName + "\n" +
               "Airline Code: " + airlineCode + "\n" +
               "Headquarters: " + headquarters + "\n" +
               "Contact: " + contactNumber + "\n" +
               "Website: " + website;
    }
}
