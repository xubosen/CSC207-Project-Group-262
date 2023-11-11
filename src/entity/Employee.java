package entity;

import java.util.HashMap;

abstract class Employee {
    private String userID;
    private String name;
    private String email;
    private String password;
    // private ? myCourses; TODO: need to figure out what data structure to use
    private HashMap<String, Courses> myCourses = new HashMap<String, Courses>();
    // private ? mySessions; TODO: need to figure out what data structure to use
    private HashMap<String, ClassSession> mySessions = new HashMap<String, ClassSession>();

    private Calendar myCalendar;

    public Employee(String userID, String name, String email, String password) {
        // TODO: implement this method & change the parameters

        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUID() {
        return this.userID;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    // abstract ? getCourses(); TODO: need to figure out what type of data structure to use
    public HashMap<String, Course> getCourses() {
        return this.myCourses;
    }
    // abstract ? getSessions(); TODO: need to figure out what type of data structure to use
    public HashMap<String, ClassSession> getSessions() {
        return this.mySessions;
    }
    abstract boolean addSession(ClassSession session);
    abstract boolean removeSession(); // TODO: how to identify which session to remove?

    abstract boolean addCourse(Course course);

    abstract boolean removeCourse(); // TODO: how to identify which course to remove?

    abstract Calendar makeCalendar();

}
