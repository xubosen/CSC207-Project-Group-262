package use_case;

public class PositionCheckingInputData {
    private String userID;
    private String courseCode;

    public PositionCheckingInputData(String userID, String courseCode) {
        this.userID = userID;
        this.courseCode = courseCode;
    }

    public String getUserID() {
        return userID;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
