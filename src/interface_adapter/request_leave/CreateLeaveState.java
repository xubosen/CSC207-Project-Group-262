package interface_adapter.request_leave;

public class CreateLeaveState {

    private String leaveName = "";
    private String leaveID = "";
    private boolean leaveCreationSuccessful = false;
    private String leaveCreationResponseMessage = "";
    private String requesterID = "";
    private String startDate = "";
    private String endDate = "";

    /**
     * Initializer for CreateLeaveState when you have a pre-existing CreateLeaveState.
     * @param copy The Pre-existing CreateLeaveState.
     */
    public CreateLeaveState(CreateLeaveState copy) {
        leaveName = copy.leaveName;
        leaveID = copy.leaveID;
        leaveCreationSuccessful = copy.leaveCreationSuccessful;
        leaveCreationResponseMessage = copy.leaveCreationResponseMessage;
        requesterID = copy.requesterID;
        startDate = copy.startDate;
        endDate = copy.endDate;
    }

    /**
     * Initializer if there is no input.
     */
    public CreateLeaveState() {}

    // Getters and Setters for each attribute
    // ...

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(String leaveID) {
        this.leaveID = leaveID;
    }

    public boolean isLeaveCreationSuccessful() {
        return leaveCreationSuccessful;
    }

    public void setLeaveCreationSuccessful(boolean leaveCreationSuccessful) {
        this.leaveCreationSuccessful = leaveCreationSuccessful;
    }

    public String getLeaveCreationResponseMessage() {
        return leaveCreationResponseMessage;
    }

    public void setLeaveCreationResponseMessage(String leaveCreationResponseMessage) {
        this.leaveCreationResponseMessage = leaveCreationResponseMessage;
    }

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
