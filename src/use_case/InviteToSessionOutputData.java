package use_case;

public class InviteToSessionOutputData {
    private boolean isSuccessful;
    private String message;

    public InviteToSessionOutputData(boolean isSuccessful, String message) {
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
