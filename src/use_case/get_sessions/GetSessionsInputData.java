package use_case.get_sessions;

public class GetSessionsInputData {
    private String employeeId;

    public GetSessionsInputData(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}