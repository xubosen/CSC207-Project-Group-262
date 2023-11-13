package entity;

import java.util.HashMap;

public class Instructor extends Employee{
    private String userID;
    private String name;
    private String email;
    private String password;
    private Calendar myCalendar = new Calendar();
    private HashMap<String, ClassSession> mySessions = new HashMap<String, ClassSession>();
    private HashMap<String, Course> myCourses = new HashMap<String, Course>();

    /**
     * Creates instance of Instructor with no session and courses right now.
     * @param userID Is the employees username to log in
     * @param name Is the employees first name
     * @param email The email address which the employee is contacted
     * @param password The password that the employee will use to log in.
     */
    public Instructor(String userID, String name, String email, String password) {
        super(userID, name, email, password);
    }

    /**
     * Adds this session to mySessions with the key being session id and value being the session.
     */
    public boolean addSession(ClassSession classSessions) {
        for (String sessionID : mySessions.keySet())
        {
            if (sessionID == classSessions.getSessionID()) {
                return false;
            }
        }
        mySessions.put(classSessions.getSessionID(), classSessions);
        return true;
    }

    /**
     * Method that other classes will access to add this Instrucotr into a course.
     * @param course
     * @return whether or not this Instructor is already in the course
     */
    public boolean addCourse(Course course) {
        for (String courseID : myCourses.keySet()) {
            if (courseConflict(courseID, course)) {
                return false;
            }
        }
        myCourses.put(course.getCourseCode(), course);
        return true;
    }

    /**
     * Removes the Instructor from the session.
     * @param classSessions
     * @return whether the Instructor actually had that class session.
     */
    public boolean removeSession(ClassSession classSessions) {
        for (String sessionID : mySessions.keySet())
        {
            if (sessionConflict(sessionID, classSessions)) {
            mySessions.remove(sessionID);
            return true;
        }
        }
        return false;

    }

    /**
     * Removes the Instructor from the Course.
     * @param course
     * @return whether the Instructor actually had this course.
     */
    public boolean removeCourse(Course course) {
        for (String courseID : myCourses.keySet()) {
            if (courseConflict(courseID, course)) {
                myCourses.remove(courseID);
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function for add and remove Course.
     * @param identifier
     * @param course
     * @return if the Instructor already has the course.
     */
    private boolean courseConflict(String identifier, Course course) {
        return identifier == course.getCourseCode();
    }

    /**
     * Helper function for add and remove session.
     * @param identifier
     * @param session
     * @return if the Instructor already has the class session.
     */
    private boolean sessionConflict(String identifier, ClassSession session) {
        return identifier == session.getSessionID();
    }

    public Calendar makeCalendar() {
        // Todo: Implement this method in both Instructor and TeachingAssistant.
        // Probaby for loop through the values of sessions and then making a Calendar with those values
        for (String sessionID : mySessions.keySet()) {
            myCalendar.addEvent(mySessions.get(sessionID).toCalendarEvent());
        }
        return myCalendar;
    }
}
