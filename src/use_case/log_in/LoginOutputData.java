package use_case.log_in;

public class LoginOutputData {

    private final String username;
    private final String type;
    private boolean loginSuccessful;

    public LoginOutputData(String username, String type, boolean loginSuccessful) {
        this.username = username;
        this.type = type;
        this.loginSuccessful = loginSuccessful;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}
