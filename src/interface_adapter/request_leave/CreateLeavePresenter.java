package interface_adapter.create_leave;

import interface_adapter.request_leave.LeaveRequestViewModel;
import use_case.create_leave.CreateLeaveOutputBoundary;
import use_case.create_leave.CreateLeaveOutputData;

public class CreateLeavePresenter implements CreateLeaveOutputBoundary {
    private LeaveRequestViewModel leaveRequestViewModel;

    /**
     * Initializer for the presenter of the create leave use case.
     * @param leaveRequestViewModel The view model of the leave request use case.
     */
    public CreateLeavePresenter(LeaveRequestViewModel leaveRequestViewModel) {
        this.leaveRequestViewModel = leaveRequestViewModel;
    }

    /**
     * Prepare view with the current output data.
     * @param outputData The data that will be output when createLeave has been tried.
     */
    public void prepareView(CreateLeaveOutputData outputData) {
        // TODO: Delete this line after testing
        System.out.println(outputData.getMessage());

        // Assuming LeaveRequestViewModel has methods to set the state related to leave creation
        // These methods need to be implemented in LeaveRequestViewModel
        leaveRequestViewModel.setLeaveCreationSuccessful(outputData.isSuccessful());
        leaveRequestViewModel.setLeaveCreationResponseMessage(outputData.getMessage());

        leaveRequestViewModel.firePropertyChanged();
    }
}
