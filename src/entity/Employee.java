package entity;

import java.util.HashMap;

/**
 *
 */
public abstract class Employee {
    private String userID;
    private String name;
    private String email;
    private String password;
    private HashMap<String, Course> myCourses = new HashMap<String, Course>();
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

    public String getPassword() {
        return this.password;
    }

    public HashMap<String, Course> getCourses() {
        return this.myCourses;
    }
    public HashMap<String, ClassSession> getSessions() {
        return this.mySessions;
    }
    abstract boolean addSession(ClassSession session);
    abstract boolean removeSession(ClassSession classSession);

    abstract boolean addCourse(Course course);

    abstract boolean removeCourse(Course course);

    abstract Calendar makeCalendar();

}
