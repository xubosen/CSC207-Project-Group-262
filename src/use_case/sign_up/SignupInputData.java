package use_case.sign_up;

public class SignupInputData {
    private String userID;
    private String name;
    private String email;
    private String password;
    private String type;

    public SignupInputData(String userID, String name, String email, String password, String type) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
}
