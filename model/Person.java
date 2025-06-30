package model;

public abstract class Person implements PersonDetails {
    protected String name, email, phoneNumber;

    public Person(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getName() { return name; }
    @Override
    public String getEmail() { return email; }
    @Override
    public String getPhoneNumber() { return phoneNumber; }
    @Override
    public abstract String getRoleDescription();
}
