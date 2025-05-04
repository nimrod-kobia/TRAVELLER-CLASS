class User{
    //user details
    private String userName;
    private String userDOB;
    private String userVisa;
    private String userPassport;
    //constructor to initialize user details
    public User(String userName, String userDoB, String userVisa, String userPassport){
        this.userName = userName;
        this.userDOB = userDoB;
        this.userVisa = userVisa;
        this.userPassport = userPassport;
    }
//getter methods to return users information
 public String userName(){
    return userName;
 }
 public String userDOB(){
    return userDOB;
 }
 public String userVisa(){
    return userVisa;
 }
 public String userPassport(){
    return userPassport;
 }
}
