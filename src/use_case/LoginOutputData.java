package use_case;

public class LoginOutputData {

    private final String username;
    private boolean loginSuccessful;

    public LoginOutputData(String username, boolean loginSuccessful) {
        this.username = username;
        this.loginSuccessful = loginSuccessful;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}
