package model;

public class Aircraft {
    private String registrationNumber;
    private String model;
    private int capacity;

    public Aircraft(String registrationNumber, String model, int capacity) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.capacity = capacity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return model + " (" + registrationNumber + ") - Cap: " + capacity;
    }
}