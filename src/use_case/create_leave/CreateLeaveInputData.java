package use_case.create_leave;

public class CreateLeaveInputData {
    private String leaveName;
    private String leaveID;
    private String startDate;
    private String endDate;
    private String requesterID;

    /**
     * Initializer of the input data for creating a leave request.
     * @param leaveName The name of the leave request.
     * @param leaveID The unique identifier for the leave request.
     * @param startDate The start date of the leave.
     * @param endDate The end date of the leave.
     * @param requesterID The ID of the employee requesting the leave.
     */
    public CreateLeaveInputData(String leaveName, String leaveID, String startDate, String endDate) {
        this.leaveName = leaveName;
        this.leaveID = leaveID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters for each attribute

    public String getLeaveName() {
        return leaveName;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRequesterID() {
        return requesterID;
    }
}
