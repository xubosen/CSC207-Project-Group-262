package use_case.remove_from_event;

public class RemoveFromEventOutputData {
    private boolean isSuccessful;
    private String message;

    public RemoveFromEventOutputData(boolean isSuccessful, String message) {
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
