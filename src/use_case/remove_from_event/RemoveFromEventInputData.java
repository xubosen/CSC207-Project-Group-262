package use_case.remove_from_event;

public class RemoveFromEventInputData {
    private String employeeID;
    private String eventID;

    public RemoveFromEventInputData(String employeeID, String eventID) {
        this.employeeID = employeeID;
        this.eventID = eventID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEventID() {
        return eventID;
    }
}
