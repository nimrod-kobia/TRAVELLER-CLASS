import java.sql.Date;

public class Passenger {
    private int passengerId;
    private String userName;
    private Date dateOfBirth;
    private String passportNumber;
    private String nationality;
    private String email;
    private String phone;
    private String userVisa;

    public Passenger(int passengerId, String userName, Date dateOfBirth, String passportNumber, String nationality, String email, String phone, String userVisa) {
        this.passengerId = passengerId;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.email = email;
        this.phone = phone;
        this.userVisa = userVisa;
    }

    // Getters and setters
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUserVisa() { return userVisa; }
    public void setUserVisa(String userVisa) { this.userVisa = userVisa; }
}
