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
    private HashMap<String, ClassSession> mySessions  = new HashMap<String, ClassSession>();
    private HashMap<String, Employee> staff = new HashMap<String, Employee>();

    /**
     * Constructs a new Event object with the given name, event ID, and course. Adds the event to the course when
     * initializing
     *
     * @param name     The name of the event.
     * @param eventID  The unique identifier for the event.
     * @param course   The course associated with the event.
     */
    public Event(String name, String eventID, Course course) {
        this.name = name;
        this.eventID = eventID;
        this.course = course;
        this.course.addEvent(this);
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
     * Representational Invariant:
     * The employee must be in the course this event is in.
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
     * Remove an employee from the event's staff using their userID. When doing this, remove the employee from all
     * sessions associated with the event.
     *
     * @param employee The employee to be removed.
     * @return true if the employee was successfully removed, false if the employee is not in the staff.
     */
    public boolean removeStaff(Employee employee) {
        String userID = employee.getUID();

        // If the employee is in the staff, remove them from the staff
        if (staff.containsKey(userID)) {
            staff.remove(userID);

            // Remove the employee from all sessions associated with the event
            for (ClassSession session : mySessions.values()) {
                if (session.containsStaff(employee)) {
                    session.removeStaff(employee);
                }
            }
            return true;
        }

        // If the employee is not in the staff, return false
        return false;
    }

    /**
     * Add a class session to the event. Return false if the session is already in the event or if the session is in
     * another event.
     *
     * @param session The class session to be added.
     * @return true if the session was added successfully
     */
    public boolean addSession(ClassSession session) {
        if (session.getEvent() == this && !mySessions.containsKey(session.getSessionID())) {
            mySessions.put(session.getSessionID(), session);
            return true;
        }
        return false;
    }

    /**
     * Remove a class session from the event. Remove the session from every employee in the session's list of staff and
     * set its event attribute to null.
     *
     * @param session The session to be removed.
     * @return true if the session was successfully removed, false if the session is not in the event.
     */
    public boolean removeSession(ClassSession session) {
        if (containsSession(session)) {
            mySessions.remove(session.getSessionID());

            // Remove the session from every employee in the session's list of staff
            for (Employee employee : session.listStaff()) {
                employee.removeSession(session);
            }

            session.event = null;
            return true;
        }
        return false;
    }

    /**
     * Return whether the event contains the session
     * @param session
     * @return true if the event contains the session, false otherwise
     */
    public boolean containsSession(ClassSession session) {
        return mySessions.containsKey(session.getSessionID());
    }

    /**
     * Get a copy of the list of staff members associated with the event.
     *
     * @return A copy of the list of staff members.
     */
    public HashMap<String, Employee> listStaff() {
        return new HashMap<>(staff);
    }

    /**
     * Checks if the given session clashes with any of the sessions in the event.
     * @param session
     * @return true if the session clashes with any of the sessions in the event, false otherwise
     */
    public boolean conflictsWith(ClassSession session) {
        for (ClassSession s : mySessions.values()) {
            if (s.toCalendarEvent().clashesWith(session.toCalendarEvent())) {
                return true;
            }
        }
        return false;
    }
}