package use_case.create_leave;

public class CreateLeaveOutputData {
    private boolean isSuccessful;
    private String message;

    /**
     * Initialize whether leave creation was successful and the following related message.
     * @param isSuccessful Boolean on whether leave creation worked
     * @param message Message for success or failure.
     */
    public CreateLeaveOutputData(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    /**
     * Returns whether the output was successful.
     * @return Whether leave creation was successful
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Returns a success message or the type of failure that occurred if it failed to create a leave.
     * @return Error or success message
     */
    public String getMessage() {
        return message;
    }
}
