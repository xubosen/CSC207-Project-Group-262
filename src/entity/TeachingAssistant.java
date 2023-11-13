package entity;

import java.util.HashMap;
import java.util.Objects;

public class TeachingAssistant extends Employee {
    private String userID;
    private String name;
    private String email;
    private String password;
    private Calendar myCalendar = new Calendar();
    private HashMap<String, ClassSession> mySessions = new HashMap<String, ClassSession>();
    private HashMap<String, Course> myCourses = new HashMap<String, Course>();

    /**
     * Creates instance of Teaching Assistant with no session and courses right now.
     * @param userID
     * @param name
     * @param email
     * @param password
     */
    public TeachingAssistant(String userID, String name, String email, String password) {
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

    // Should the TA be allowed to add their own courses or is it up to the instructor with the course admin var.

    /**
     * Method that other classes will access to add this TA into a course.
     * @param course
     * @return whether or not this TA is already in the course
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
     * Removes the Teaching Assistant from the session.
     * @param classSessions
     * @return whether the TA actually had that class session.
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
     * Removes the Teaching Assistant from the Course.
     * @param course
     * @return whether the TA actually had this course.
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
     * @return if the TA already has the course.
     */
    private boolean courseConflict(String identifier, Course course) {
        return identifier == course.getCourseCode();
    }

    /**
     * Helper function for add and remove session.
     * @param identifier
     * @param session
     * @return if the TA already has the class session.
     */
    private boolean sessionConflict(String identifier, ClassSession session) {
        return Objects.equals(identifier, session.getSessionID());
    }

    public Calendar makeCalendar() {
        for (String sessionID : this.mySessions.keySet()) {
            myCalendar.addEvent(mySessions.get(sessionID).toCalendarEvent());
        }
        return myCalendar;
    }
}