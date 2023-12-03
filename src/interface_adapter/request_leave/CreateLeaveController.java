package interface_adapter.request_leave;

import use_case.create_leave.CreateLeaveInputBoundary;
import use_case.create_leave.CreateLeaveInputData;

public class CreateLeaveController {
    private CreateLeaveInputBoundary createLeaveInteractor;

    /**
     * Initializer for the CreateLeaveController to take in user input and transfer data.
     * @param createLeaveInteractor The interactor that handles the leave request creation logic.
     */
    public CreateLeaveController(CreateLeaveInputBoundary createLeaveInteractor) {
        this.createLeaveInteractor = createLeaveInteractor;
    }

    /**
     * Creates input data for a leave request after taking inputs.
     * @param leaveName The name of the leave request.
     * @param leaveID The unique identifier for the leave request.
     * @param startDate The start date of the leave.
     * @param endDate The end date of the leave.
     * @param requesterID The ID of the employee requesting the leave.
     */
    public void createLeave(String leaveName, String leaveID, String startDate, String endDate, String requesterID) {
        CreateLeaveInputData inputData = new CreateLeaveInputData(leaveName, leaveID, startDate, endDate);
        createLeaveInteractor.createLeave(inputData);
    }

    // Additional methods or logic as needed for the leave creation process
}
