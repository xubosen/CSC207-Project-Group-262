package use_case.enroll;

public class EnrollInputData {
    private String invitorID;
    private String inviteeID;
    private String courseCode;


    public EnrollInputData(String invitorID, String inviteeID, String courseCode) {
        this.invitorID = invitorID;
        this.inviteeID = inviteeID;
        this.courseCode = courseCode;
    }

    public String getInvitorID() {
        return invitorID;
    }

    public String getInviteeID() {
        return inviteeID;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
