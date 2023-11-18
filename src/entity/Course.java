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
        staff.put(admin.getUID(), admin);

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

    /**
     * Add an employee to the course's staff. If the employee is already in the staff, return false. If the course is
     * not already in the employee's list of courses, add the course to the employee's list of courses.
     *
     * @param employee
     * @return
     */
    public boolean addStaff(Employee employee) {
        // If the employee is null or already in the staff, return false.
        if (employee == null || containsStaff(employee)) {
            return false;
        } else {
            // Otherwise, add the employee to the staff
            staff.put(employee.getUID(), employee);

            // If the course is not already in the employee's list of courses, add the course to the list
            if (!employee.containsCourse(this)) {
                employee.addCourse(this);
            }
            return true;
        }

    }


    /**
     * Remove an employee from the course's staff. When doing so, remove the employee from all events associated with
     * the course and all the sessions associated with those events.
     *
     * @param employee The employee to be removed.
     * @return true if the employee was successfully removed, false if the employee is not in the staff of the course.
     */
    public boolean removeStaff(Employee employee) {
        String userID = employee.getUID();
        if (staff.containsKey(userID)) {
            staff.remove(userID);
            for (Event event : myEvents.values()) {
                event.removeStaff(employee); // event.removeStaff() will remove the employee from all its sessions
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean containsStaff(Employee employee) {
        return staff.containsKey(employee.getUID());
    }

    public boolean addEvent(Event event) {
        if (event == null || myEvents.containsKey(event.getEventID())) {
            return false;
        }
        myEvents.put(event.getEventID(), event);
        return true;
    }

    public boolean removeEvent(Event event) {
        if (containsEvent(event)) {
            myEvents.remove(event.getEventID());
            return true;
        }
        return false;
    }

    public boolean containsEvent(Event event) {
        return myEvents.containsKey(event.getEventID());
    }

    public HashMap<String, Employee> listStaff() {
        return new HashMap<>(staff);
    }

    public HashMap<String, Event> listEvents() {
        return new HashMap<>(myEvents);
    }
}
