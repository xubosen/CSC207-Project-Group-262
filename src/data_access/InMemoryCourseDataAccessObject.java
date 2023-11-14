package data_access;

import entity.Course;
import java.util.HashMap;
import java.util.Set;

public class InMemoryCourseDataAccessObject {
    private HashMap<String, Course> courses;

    public InMemoryCourseDataAccessObject() {
        courses = new HashMap<String, Course>();
    }

    public InMemoryCourseDataAccessObject(HashMap<String, Course> courses) {
        courses = courses;
    }

    public void save(Course course) {
        this.courses.put(course.getCourseCode(), course);
    }

    public boolean existsByID(String courseCode) {
        return courses.containsKey(courseCode);
    }

    public Course getByID(String courseCode) {
        return courses.get(courseCode);
    }

    public Set<String> getAllIDs() {
        return courses.keySet();
    }

    public void addCourse(Course course) {
        this.courses.put(course.getCourseCode(), course);
    }

    public boolean removeCourse(Course course) {
        if (this.courses.containsKey(course.getCourseCode())){
            this.courses.remove(course.getCourseCode());
            return true;
        }
        return false;
    }
}
