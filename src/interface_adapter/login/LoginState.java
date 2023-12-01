package interface_adapter.login;

public class LoginState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String passwordError = null;
    private boolean loginSuccessful = false;

    private String type = "";

    private String loginError = null;

    public LoginState(LoginState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
        loginSuccessful = copy.loginSuccessful;
        loginError = copy.loginError;
        type = copy.getType();
    }

    public LoginState() {}

    // Getters and setters for all fields
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isLoginSuccessful() { return loginSuccessful; }
    public String getLoginError() { return loginError; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setLoginSuccessful(boolean loginSuccessful) { this.loginSuccessful = loginSuccessful; }
    public void setLoginError(String loginError) { this.loginError = loginError; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
