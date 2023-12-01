package use_case.sign_up;

public class SignupOutputData {
    private boolean isSuccessful;
    private String message;

    public SignupOutputData(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }
}
