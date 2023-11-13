package use_case;

public class EnrollInputData {
    private String userID;
    private String email;
    private String courseCode;


    public EnrollInputData(String userID, String email, String courseCode) {
        this.userID = userID;
        this.email = email;
        this.courseCode = courseCode;
    }

    public String getUserID() {
        return userID;
    }

    // TODO: Discuss using email instead of userID at next meeting
    public String getEmail() {
        return email;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
