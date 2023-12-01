package use_case.get_courses;

public class GetCoursesInputData {
    private String userID;

    public GetCoursesInputData(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
