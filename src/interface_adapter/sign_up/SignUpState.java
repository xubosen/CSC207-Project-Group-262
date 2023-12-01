package interface_adapter.sign_up;

public class SignUpState {
    private String username;
    private String name;
    private String email;
    private String password;

    private boolean signupSuccessful;
    private String signupResponseMessage;
    private String role;

public SignUpState() {
        username = "";
        name = "";
        email = "";
        password = "";
        signupSuccessful = false;
        signupResponseMessage = "";
        role = "";
    }

    public SignUpState(SignUpState copy) {
        this.username = copy.getUsername();
        this.name = copy.getName();
        this.email = copy.getEmail();
        this.password = copy.getPassword();
        this.signupSuccessful = copy.isSignupSuccessful();
        this.signupResponseMessage = copy.getSignupResponseMessage();
    }

    public String getUsername() {
        return username;
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

    public boolean isSignupSuccessful() {
        return signupSuccessful;
    }

    public String getSignupResponseMessage() {
        return signupResponseMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSignupSuccessful(boolean signupSuccessful) {
        this.signupSuccessful = signupSuccessful;
    }

    public void setSignupResponseMessage(String signupResponseMessage) {
        this.signupResponseMessage = signupResponseMessage;
    }
}
