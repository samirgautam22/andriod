package aayush.com.collegeproject.Dtos;

public class SignupDto {
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String username;
    private String password;
    private String cpassword;
    private String phoneNo;

    public SignupDto(String email, String firstName, String lastName, String username, String gender, String password, String cpassword, String phoneNo) {
        //this.addressList=addressList;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
