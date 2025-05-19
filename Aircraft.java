import java.util.ArrayList;
import java.util.List;

public class Aircraft {

    private String registrationNumber; 
    private String model;              
    private String manufacturer;       
    private int seatingCapacity;       
    private double maxTakeoffWeight;   
    private double range;              
    private int yearOfManufacture;

    public Aircraft(String registrationNumber, String model, String manufacturer,
                    int seatingCapacity, double maxTakeoffWeight, double range, int yearOfManufacture) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.manufacturer = manufacturer;
        this.seatingCapacity = seatingCapacity;
        this.maxTakeoffWeight = maxTakeoffWeight;
        this.range = range;
        this.yearOfManufacture = yearOfManufacture;
    }

    // Getters
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public double getMaxTakeoffWeight() {
        return maxTakeoffWeight;
    }

    public double getRange() {
        return range;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    // Setters
    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public void setMaxTakeoffWeight(double maxTakeoffWeight) {
        this.maxTakeoffWeight = maxTakeoffWeight;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public String toString() {
        return String.format("%s %s (%s) - Capacity: %d, Range: %.1f km",
                manufacturer, model, registrationNumber, seatingCapacity, range);
    }

    public static void main(String[] args) {
        Aircraft aircraft = new Aircraft(
            "N12345",             
            "737 MAX",            
            "Boeing",             
            180,                  
            79015,                
            6570.0,               
            2020                  
        );

        System.out.println(aircraft);
        aircraft.setRange(6700.0);

        System.out.println("Updated range: " + aircraft.getRange() + " km");
    }
}
