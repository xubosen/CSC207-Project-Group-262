package use_case.create_course;

public class CreateCourseOutputData {
    private boolean isSuccessful;
    private String message;

    /**
     * Initialize whether course creation was successful and the following related message.
     * @param isSuccessful Boolean on whether course creation worked
     * @param message Message for success or failure.
     */
    public CreateCourseOutputData(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    /**
     * Returns whether the output was successful.
     * @return Whether course creation was successful
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Returns a success message or the type of failure that occurred if it failed to create a course.
     * @return Error or success message
     */
    public String getMessage() {
        return message;
    }
}
