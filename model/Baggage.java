package model;

public class Baggage {
    private double weight;
    private String description;

    public Baggage(double weight, String description) {
        this.weight = weight;
        this.description = description;
    }

    public double getWeight() { return weight; }
    public String getDescription() { return description; }
}
