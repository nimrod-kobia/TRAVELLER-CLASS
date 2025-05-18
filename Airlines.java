interface AirlineInfo {
    String getAirlineDetails();
}

public class Airlines implements AirlineInfo {
    private Integer airlineId;
    private String airlineName;
    private String airlineCode;
    private String headquarters;
    private String contactNumber;
    private String website;

    // Constructor to initialize all attributes
    public Airlines(Integer airlineId, String airlineName, String airlineCode, String headquarters, String contactNumber, String website) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.headquarters = headquarters;
        this.contactNumber = contactNumber;
        this.website = website;
    }

    // Getter methods
    public Integer getAirlineId() {
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

    public String getWebsite() {
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
