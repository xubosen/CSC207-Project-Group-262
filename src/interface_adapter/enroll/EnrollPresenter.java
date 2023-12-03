package interface_adapter.enroll;

import use_case.enroll.EnrollOutputBoundary;
import use_case.enroll.EnrollOutputData;

public class EnrollPresenter implements EnrollOutputBoundary{
    private EnrollViewModel enrollViewModel;
    public EnrollPresenter(EnrollViewModel enrollViewModel) {
        this.enrollViewModel = enrollViewModel;
    }

    public void prepareView(EnrollOutputData outputData) {
        EnrollState curState = enrollViewModel.getState();
        curState.setInviteSuccessful(outputData.isSuccessful());
        curState.setInviteResponseMessage(outputData.getMessage());
        enrollViewModel.setState(curState);

        enrollViewModel.firePropertyChanged();
    }
}
