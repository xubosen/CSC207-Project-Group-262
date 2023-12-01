package interface_adapter.get_courses;

import java.util.ArrayList;

public class GetCoursesState {
    private String userID = "";
    private ArrayList<String> courses = new ArrayList<String>();

    public GetCoursesState() {
    }

    public ArrayList<String> getCourses() {
        return new ArrayList<>(courses);
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = new ArrayList<>(courses);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
