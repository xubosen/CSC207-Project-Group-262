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

    /**
     * Adds a session to the employee's list of sessions. If the employee is not already in the session's list of staff,
     * add the employee to the session's list of staff.
     *
     * TODO: Discuss whether we want to allow sessions to be added even if they conflict with existing sessions
     *
     * @param curSession The session to be added.
     * @return true if the session was successfully added, false otherwise.
     */
    public boolean addSession(ClassSession curSession) {
        // If the session is already in the employee's list of sessions, don't add the session
        if (containsSession(curSession)) {
            return false;
        }


//        TODO: Discuss whether we want to allow sessions to be added even if they conflict with existing sessions
//        // Check if the session conflicts with any of the employee's existing sessions
//        for (String sessionID : mySessions.keySet()){
//            ClassSession otherSession = mySessions.get(sessionID);
//            if (curSession.conflictsWith(otherSession)) {
//                return false;
//            }
//        }

        // Add the session to the employee's list of sessions
        mySessions.put(curSession.getSessionID(), curSession);

        // If the session does not already contain the employee, add the employee to the session
        if (!curSession.containsStaff(this)) {
            curSession.addStaff(this);
        }
        return true;
    }

    /**
     * Removes a session from the employee's list of sessions and remove the employee from the session's list of staff
     * if the session still contains the employee.
     *
     * @param classSessions The session to be removed.
     * @return true if the session was successfully removed, false otherwise.
     */
    public boolean removeSession(ClassSession classSessions) {
        boolean isSuccessful = false;

        // If the session is in the employee's list of sessions, try to remove the session from the list
        if (containsSession(classSessions)) {
            mySessions.remove(classSessions.getSessionID());

            // If the session still contains the employee, remove the employee from the session
            if (classSessions.containsStaff(this)) {
                isSuccessful = classSessions.removeStaff(this);
            } else {
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    /**
     * Checks if the employee is in the session's list of staff.
     *
     * @param classSessions The session to be checked.
     * @return true if the employee is in the session's list of staff, false otherwise.
     */
    public boolean containsSession(ClassSession classSessions) {
        return mySessions.containsKey(classSessions.getSessionID());
    }

    /**
     * Adds a course to the employee's list of courses.If the course is already in the employee's list of courses, don't
     * add the course. If the employee is not already in the course's list of staff, add the employee to the course's
     * list of staff.
     *
     * @param course The course to be added.
     * @return true if the course was successfully added, false otherwise.
     */
    public boolean addCourse(Course course) {
        if (containsCourse(course)) {
            return false;
        } else {
            myCourses.put(course.getCourseCode(), course);

            // If the course does not already contain the employee, add the employee to the course
            if (!course.containsStaff(this)) {
                course.addStaff(this);
            }
            return true;
        }
    }

    /**
     * Removes a course from the employee's list of courses and remove the employee from the course's list of staff
     * as well as that of all the events in the course and all the sessions in the course.
     *
     * @param course The course to be removed.
     * @return true if the course was successfully removed, false otherwise.
     */
    public boolean removeCourse(Course course) {
        boolean isSuccessful = false;
        if (containsCourse(course)) {
            myCourses.remove(course.getCourseCode());

            // If the course still contains the employee, remove the employee from the course
            if (course.containsStaff(this)) {
                isSuccessful = course.removeStaff(this);
            } else {
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    /**
     * Checks if the employee is in the course's list of staff.
     *
     * @param course The course to be checked.
     * @return true if the employee is in the course's list of staff, false otherwise.
     */
    public boolean containsCourse(Course course) {
        return myCourses.containsKey(course.getCourseCode());
    }

    /**
     * Creates and returns a calendar for the employee.
     * @return The employee's calendar as a Calendar object.
     */
    public Calendar makeCalendar() {
        for (String sessionID : this.mySessions.keySet()) {
            myCalendar.addEvent(mySessions.get(sessionID).toCalendarEvent());
        }
        return myCalendar;
    }

}
