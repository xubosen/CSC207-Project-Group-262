package entity;

import entity.Calendar;

public class CourseAdmin extends Employee {

    public CourseAdmin(String userID, String name, String email, String password) {
        // TODO: implement this method & change the parameters
        super(userID, name, email, password);
    }
    public boolean addSession(Session session) {
        // TODO: implement this method
        return true;
    } // needs implementation of entity.Session class

    public boolean removeSession() {
        // TODO: implement this method
        return true;
    } // how to identify which session to remove?

    public boolean addCourse(Course course) {
        // TODO: implement this method
        return true;
    } // needs implementation of Course class

    public boolean removeCourse() {
        // TODO: implement this method
        return true;
    } // how to identify which course to remove?

    public Calendar makeCalendar() {
        // TODO: implement this method
        return new Calendar();
    }
}
