package entity;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Class representing a leave request.
 * A leave request can have a name, a unique ID, start and end dates, and a list of associated employees.
 */
public class Leave {
    private String name;
    private String leaveID;
    private LocalDate startDate;
    private LocalDate endDate;
    private HashMap<String, Employee> associatedEmployees = new HashMap<>();

    /**
     * Constructs a new Leave object with the given name, leave ID, start date, and end date.
     *
     * @param name      The name of the leave request.
     * @param leaveID   The unique identifier for the leave request.
     * @param startDate The start date of the leave.
     * @param endDate   The end date of the leave.
     */
    public Leave(String name, String leaveID, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.leaveID = leaveID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters for name, leaveID, startDate, endDate

    public String getName() {
        return name;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // Additional methods as needed for the leave request process
}
