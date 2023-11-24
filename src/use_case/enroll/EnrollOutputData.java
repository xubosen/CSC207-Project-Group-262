package use_case.enroll;

public class EnrollOutputData {
    private boolean isSuccessful;
    private String message;

    public EnrollOutputData(boolean isSuccessful, String message) {
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
