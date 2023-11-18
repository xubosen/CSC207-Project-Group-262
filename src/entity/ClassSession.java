package entity;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassSession {
    String SessionID;
    String name;
    CalendarEvent calEvent;
    HashMap<String, Employee> staff;
    String location;
    Event event;

    public ClassSession(String SessionID, String name, CalendarEvent calEvent, String location, Event event) {
        this.SessionID = SessionID;
        this.name = name;
        this.calEvent = calEvent;
        this.location = location;
        this.event = event;

        this.staff = new HashMap<String, Employee>();
    }

    public String getSessionID() {
        return SessionID;
    }

    public String getName() {
        return name;
    }

    public CalendarEvent toCalendarEvent() {
        return calEvent;
    }

    public String getLocation() {
        return location;
    }

    public Event getEvent() {
        return event;
    }

    /**
     * Change the location of the class session
     * @param location The new location of the class session
     */
    public void changeLocation(String location) {
        this.location = location;
    }

    /**
     * Add staff to the class session
     * @param staff The staff to add
     * @return true if staff is added, false if staff is already in the class session
     */
    public boolean addStaff(Employee staff) {
        // If staff is already in the staff hashmap, return false
        if (containsStaff(staff)) {
            return false;
        }

        // Otherwise, add staff to the hashmap and return true
        this.staff.put(staff.getUID(), staff);
        return true;
    }

    /**
     * Remove staff from the class session and remove the class session from the staff's list of class sessions
     * @param employee The staff to remove
     * @return true if staff is removed, false if staff is not in the class session
     */
    public boolean removeStaff(Employee employee) {
        // If staff is not in the staff hashmap, return false
        if (!this.staff.containsKey(employee.getUID())) {
            return false;
        }

        // Otherwise, remove staff from the hashmap and remove the class session from the staff's list of class sessions
        this.staff.remove(employee.getUID());

        // If the staff is still in the class session, remove the staff from the class session
        if (employee.containsSession(this)) {
            employee.removeSession(this);
        }
        return true;
    }

    /**
     * Check if staff is in the class session
     * @param staff The staff to check for
     * @return true if staff is in the class session, false otherwise
     */
    public boolean containsStaff(Employee staff) {
        return this.staff.containsKey(staff.getUID());
    }

    /**
     * Reschedule the time of the class session
     * @param newCalEvent The new time of the class session
     * @return true if the class session is rescheduled, false if the new time clashes with other events
     */
    public boolean reschedule(CalendarEvent newCalEvent) {
        // Check if new time conflicts with other class sessions in the event. If it does, return false
        ClassSession tempSession = new ClassSession(this.SessionID, this.name, newCalEvent, this.location, this.event);
        if (this.event.conflictsWith(tempSession)) {
            return false;
        // Otherwise, set the new time and return true
        } else {
            this.calEvent = newCalEvent;
            return true;
        }
    }

    /**
     * Get a list of staff in the class session
     * @return The list of staff in the class session
     */
    public ArrayList<Employee> listStaff() {
        return new ArrayList<Employee>(this.staff.values());
    }

    /**
     * Check if the class session clashes with another class session
     * @param otherSession The class session to check against
     * @return true if the class session clashes with another class session, false otherwise
     */
    public boolean conflictsWith(ClassSession otherSession) {
        return calEvent.clashesWith(otherSession.toCalendarEvent());
    }
}

