package entity;
import java.util.HashMap;

public class Course {
    private String name;
    private String courseCode;
    private HashMap<String, Employee> staff;
    private HashMap<String, Event> myEvents;
    private Instructor admin;

    public Course(String name, String courseCode, Instructor admin) {
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

    public Instructor getAdmin() {
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
     * the course and all the sessions associated with those events. If the course is still in the employee's list of
     * courses, remove the course from the employee's list of courses.
     *
     * @param employee The employee to be removed.
     * @return true if the employee was successfully removed, false if the employee is not in the staff of the course.
     */
    public boolean removeStaff(Employee employee) {
        String userID = employee.getUID();

        // If the employee is in the staff, remove the employee from the staff.
        if (staff.containsKey(userID)) {
            staff.remove(userID);

            // Remove the employee from all events associated with the course and all the sessions associated with
            // those events.
            for (Event event : myEvents.values()) {
                event.removeStaff(employee); // event.removeStaff() will remove the employee from all its sessions
            }

            // If the course is still in the employee's list of courses, remove the course from the employee's list of
            // courses.
            if (employee.containsCourse(this)) {
                employee.removeCourse(this);
            }
            return true;
        }
        return false;
    }

    public boolean containsStaff(Employee employee) {
        return staff.containsKey(employee.getUID());
    }

    public boolean addEvent(Event event) {
        if (event == null || containsEvent(event)) {
            return false;
        }
        myEvents.put(event.getEventID(), event);
        return true;
    }

    /**
     * Remove an event from the course. When doing so, change the event's course attribute to null and remove all its
     * sessions.
     * @param event
     * @return true iff the event was successfully removed
     */
    public boolean removeEvent(Event event) {
        // If the event is in the course, remove the event from the course
        if (containsEvent(event)) {
            myEvents.remove(event.getEventID());

            // If the event is still associated to the course, delete the event.
            if (event.getCourse() == this) {
                event.delete();
            }

            return true;
        }
        return false;
    }

    public boolean containsEvent(Event event) {
        return myEvents.containsKey(event.getEventID());
    }

    public HashMap<String, Employee> getStaff() {
        return new HashMap<>(staff);
    }

    public HashMap<String, Event> getEvents() {
        return new HashMap<>(myEvents);
    }
}
