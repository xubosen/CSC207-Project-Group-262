package entity;
import java.util.HashMap;

public class Course {
    private String name;
    private String courseCode;
    private HashMap<String, Employee> staff;
    private HashMap<String, Event> myEvents;
    private Employee admin;

    public Course(String name, String courseCode, Employee admin) {
        this.name = name;
        this.courseCode = courseCode;
        this.admin = admin;
        this.staff = new HashMap<>();
        this.myEvents = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Employee getAdmin() {
        return admin;
    }

    public boolean addStaff(Employee employee) {
        if (employee == null || staff.containsKey(employee.getUID())) {
            return false;
        }
        staff.put(employee.getUID(), employee);
        return true;
    }

    public boolean removeStaff(String userID) {
        if (staff.containsKey(userID)) {
            staff.remove(userID);
            return true;
        }
        return false;
    }

    public boolean addEvent(Event event) {
        if (event == null || myEvents.containsKey(event.getEventID())) {
            return false;
        }
        myEvents.put(event.getEventID(), event);
        return true;
    }

    public boolean removeEvent(String eventID) {
        if (myEvents.containsKey(eventID)) {
            myEvents.remove(eventID);
            return true;
        }
        return false;
    }

    public HashMap<String, Employee> listStaff() {
        return new HashMap<>(staff);
    }

    public HashMap<String, Event> listEvents() {
        return new HashMap<>(myEvents);
    }
}
