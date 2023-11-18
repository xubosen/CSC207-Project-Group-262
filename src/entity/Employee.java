package entity;

import java.util.HashMap;

// TODO : Implement the abstract methods here so the subclasses inherits them easily.

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
        return new HashMap<String, Course>(myCourses);
    }
    public HashMap<String, ClassSession> getSessions() {
        return new HashMap<String, ClassSession>(mySessions);
    }
    public boolean addSession(ClassSession curSession) {
        for (String sessionID : mySessions.keySet()){
            ClassSession otherSession = mySessions.get(sessionID);
            if (curSession.conflictsWith(otherSession)) {
                return false;
            }
        }
        mySessions.put(curSession.getSessionID(), curSession);
        return true;
    }

    public boolean removeSession(ClassSession classSessions) {
        for (String sessionID : mySessions.keySet())
        {
            if (sessionID == classSessions.getSessionID()) {
                mySessions.remove(sessionID);
                return true;
            }
        }
        return false;
    }


    public boolean addCourse(Course course) {
        for (String courseID : myCourses.keySet()) {
            if (courseID == course.getCourseCode()) {
                return false;
            }
        }
        myCourses.put(course.getCourseCode(), course);
        return true;
    };

    public boolean removeCourse(Course course) {
        for (String courseID : myCourses.keySet()) {
            if (courseID == course.getCourseCode()) {
                myCourses.remove(courseID);
                return true;
            }
        }
        return false;
    }

    public Calendar makeCalendar() {
        for (String sessionID : this.mySessions.keySet()) {
            myCalendar.addEvent(mySessions.get(sessionID).toCalendarEvent());
        }
        return myCalendar;
    }

}
