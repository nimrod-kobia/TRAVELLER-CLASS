package model;

public class Plane {
    private String model;
    private String registration;
    private int capacity;

    public Plane(String model, String registration, int capacity) {
        this.model = model;
        this.registration = registration;
        this.capacity = capacity;
    }

    public String getModel() { return model; }
    public String getRegistration() { return registration; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return registration + " - " + model + " (" + capacity + " seats)";
    }
}
