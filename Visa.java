public class Visa implements Identifiable {  // Make sure it implements Identifiable
    private String visaNumber;
    
    public Visa(String visaNumber) {
        this.visaNumber = visaNumber;
    }
    
    @Override
    public String getID() {
        return visaNumber;
    }
    
    public static void main(String[] args) {
        Visa v1 = new Visa("rwer");
        System.out.println(v1.getID());  // Print the ID instead of the object
    }
}