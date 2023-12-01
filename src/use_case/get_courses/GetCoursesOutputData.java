package use_case.get_courses;

import java.util.ArrayList;

public class GetCoursesOutputData {
    private ArrayList<String> courses;

    public GetCoursesOutputData(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getCourses() {
        return new ArrayList<>(courses);
    }
}
