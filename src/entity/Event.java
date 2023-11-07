package entity;
import java.util.HashMap;

public abstract class Event {
    private String name;
    private String eventId;
    private Course course;
    private HashMap<String, ClassSession> mySessions;
    private HashMap<String, Employee> staff;

    // Constructor
    public Event(String name, String eventId, Course course) {
        this.name = name;
        this.eventId = eventId;
        this.course = course;
        this.mySessions = new HashMap<String, ClassSession>();
        this.staff = new HashMap<String, Employee>();
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Getter method for eventId
    public String getEventId() {
        return eventId;
    }

    // Getter method for course
    public Course getCourse() {
        return course;
    }

    // Add staff to the event
    public boolean addStaff(Employee employee) {
        if (employee != null && !staff.containsKey(employee.getUserId())) {
            staff.put(employee.getUserId(), employee);
            return true;
        }
        return false;
    }

    // Remove staff from the event using userID
    public boolean removeStaff(String userId) {
        if (staff.containsKey(userId)) {
            staff.remove(userId);
            return true;
        }
        return false;
    }

    // Add a session to the event
    public boolean addSession(ClassSession session) {
        if (session != null && !mySessions.containsKey(session.getSessionId())) {
            mySessions.put(session.getSessionId(), session);
            return true;
        }
        return false;
    }

    // Remove a session from the event using session ID
    public boolean removeSession(String sessionId) {
        if (mySessions.containsKey(sessionId)) {
            mySessions.remove(sessionId);
            return true;
        }
        return false;
    }

    // List all staff members associated with the event
    public HashMap<String, Employee> listStaff() {
        return staff;
    }
}