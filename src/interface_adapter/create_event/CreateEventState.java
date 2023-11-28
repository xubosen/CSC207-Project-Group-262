package interface_adapter.create_course;

import interface_adapter.EnrollState;

public class CreateCourseState {

    // TODO: Ask Simon if the userInvited contains the user invited or if it worked or not
    private String courseCreated = "";
    private String courseName = "";
    private boolean courseCreationSuccessful = false;
    private String courseCreationResponseMessage = "";

    /**
     * Initializer for CreateCourseState when you have a pre-existing CreateCourseState.
     * @param copy The Pre-existing CreateCourseState.
     */
    public CreateCourseState(CreateCourseState copy) {
        courseCreated = copy.courseCreated;
        courseCreationSuccessful = copy.courseCreationSuccessful;
        courseCreationResponseMessage = copy.courseCreationResponseMessage;
        courseName = copy.courseName;
    }

    public CreateCourseState () {}

    /**
     * Getter for courseCreated variable.
     * @return courseCreated
     */
    public String getCourseCreated() {
        return courseCreated;
    }

    /**
     * Setter method to change the courseCreated attribute.
     * @param courseCreated
     */
    public void setCourseCreated(String courseCreated) {
        this.courseCreated = courseCreated;
    }

    /**
     * Getter method to retrieve the course name.
     * @return course name state
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Setter method to set course name.
     * @param courseName What you want the created course's name to be
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     * Getter for whether course creation was successful.
     * @return Whether course was created or not
     */
    public boolean isCourseCreationSuccessful() {
        return courseCreationSuccessful;
    }

    /**
     * Setter method for boolean on whether course was created successfully.
     * @param courseCreationSuccessful
     */
    public void setCourseCreationSuccessful(boolean courseCreationSuccessful) {
        this.courseCreationSuccessful = courseCreationSuccessful;
    }

    /**
     * Getter for the response message.
     * @return Message on course creation status
     */
    public String getCourseCreationResponseMessage() {
        return courseCreationResponseMessage;
    }

    /**
     * Setter for changing the course creation message.
     * @param courseCreationResponseMessage The messaage you want to output for whether course creation worked.
     */
    public void setCourseCreationResponseMessage(String courseCreationResponseMessage) {
        this.courseCreationResponseMessage = courseCreationResponseMessage;
    }
}
