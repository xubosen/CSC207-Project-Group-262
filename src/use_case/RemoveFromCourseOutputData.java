package use_case;

public class RemoveFromCourseOutputData {
    private boolean isSuccessful;
    private String message;

    public RemoveFromCourseOutputData(boolean isSuccessful, String message) {
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
