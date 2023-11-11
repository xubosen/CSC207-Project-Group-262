package entity;
import java.util.HashMap;

/**
 * Abstract class representing an event.
 * An event can have a name, an event ID, a course, associated sessions,
 * and a list of staff members.
 */
public abstract class Event {
    private String name;
    private String eventID;
    private Course course;
    private HashMap<String, ClassSession> mySessions;
    private HashMap<String, Employee> staff;

    /**
     * Constructs a new Event object with the given name, event ID, and course.
     *
     * @param name     The name of the event.
     * @param eventID  The unique identifier for the event.
     * @param course   The course associated with the event.
     */
    public Event(String name, String eventID, Course course) {
        this.name = name;
        this.eventID = eventID;
        this.course = course;
        this.mySessions = new HashMap<String, ClassSession>();
        this.staff = new HashMap<String, Employee>();
    }

    /**
     * Get the name of the event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the event ID of the event.
     *
     * @return The event ID of the event.
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Get the course associated with the event.
     *
     * @return The course associated with the event.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Add an employee to the event's staff.
     *
     * @param employee The employee to be added to the staff.
     * @return true if the employee was added successfully, false if the employee is already in the staff.
     */
    public boolean addStaff(Employee employee) {
        if (!staff.containsKey(employee.getUID())) {
            staff.put(employee.getUID(), employee);
            return true;
        }
        return false;
    }

    /**
     * Remove an employee from the event's staff using their userID.
     *
     * @param userID The userID of the employee to be removed.
     * @return true if the employee was successfully removed, false if the employee is not in the staff.
     */
    public boolean removeStaff(String userID) {
        if (staff.containsKey(userID)) {
            staff.remove(userID);
            return true;
        }
        return false;
    }

    /**
     * Add a class session to the event.
     *
     * @param session The class session to be added.
     * @return true if the session was added successfully, false if the session already exists in the event.
     */
    public boolean addSession(ClassSession session) {
        if (!mySessions.containsKey(session.getSessionID())) {
            mySessions.put(session.getSessionID(), session);
            return true;
        }
        return false;
    }

    /**
     * Remove a class session from the event using its session ID.
     *
     * @param sessionID The session ID of the class session to be removed.
     * @return true if the session was successfully removed, false if the session is not in the event.
     */
    public boolean removeSession(String sessionID) {
        if (mySessions.containsKey(sessionID)) {
            mySessions.remove(sessionID);
            return true;
        }
        return false;
    }

    /**
     * Get a copy of the list of staff members associated with the event.
     *
     * @return A copy of the list of staff members.
     */
    public HashMap<String, Employee> listStaff() {
        return new HashMap<>(staff);
    }
}