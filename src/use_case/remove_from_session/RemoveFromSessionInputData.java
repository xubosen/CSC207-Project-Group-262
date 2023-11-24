package use_case.remove_from_session;

public class RemoveFromSessionInputData {
    private String employeeId;
    private String sessionId;

    public RemoveFromSessionInputData(String employeeId, String sessionId) {
        this.employeeId = employeeId;
        this.sessionId = sessionId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
