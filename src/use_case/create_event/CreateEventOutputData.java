package use_case.create_event;

public class CreateEventOutputData {
    private boolean isSuccessful;
    private String message;

    /**
     * Initialize whether event creation was successful and the following related message.
     * @param isSuccessful Boolean on whether event creation worked
     * @param message Message for success or failure.
     */
    public CreateEventOutputData(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    /**
     * Returns whether the output was successful.
     * @return Whether event creation was successful
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Returns a success message or the type of failure that occurred if it failed to create a event.
     * @return Error or success message
     */
    public String getMessage() {
        return message;
    }
}
