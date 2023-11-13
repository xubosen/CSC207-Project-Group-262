package data_access;

import entity.Course;
import java.util.HashMap;
import java.util.Set;

public class InMemoryCourseDataAccessObject {
    private final HashMap<String, Course> course = new HashMap<String, Course>();

    public void save(Course course) {
        this.course.put(course.getCourseCode(), course);
    }

    public boolean existsByID(String courseCode) {
        return course.containsKey(courseCode);
    }

    public Course getByID(String courseCode) {
        return course.get(courseCode);
    }

    public Set<String> getAllIDs() {
        return course.keySet();
    }
}
