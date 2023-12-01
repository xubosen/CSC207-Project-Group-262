package use_case.create_course;

public class CreateCourseInputData {
    private String courseCode;
    private String courseName;
    private String adminID;

    /**
     * Initializer of the input data that the user will create when attempting to create course.
     * @param courseCode The course code
     * @param courseName The course name
     * @param adminID The course admin's ID
     */
    public CreateCourseInputData(String courseCode, String courseName, String adminID) {
        // adminID might just pull logged in state
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.adminID = adminID;
    }

    /**
     * Getter for course code
     * @return course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Getter for course name
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * getter for admin's ID
     * @return admin ID
     */
    public String getAdminID() {
        return adminID;
    }
}
