package interface_adapter;

import use_case.EnrollOutputBoundary;
import use_case.EnrollOutputData;

public class EnrollPresenter implements EnrollOutputBoundary{
    private EnrollViewModel enrollViewModel;
    public EnrollPresenter(EnrollViewModel enrollViewModel) {
        this.enrollViewModel = enrollViewModel;
    }

    public void prepareView(EnrollOutputData outputData) {
        System.out.println(outputData.getMessage());
        EnrollState curState = enrollViewModel.getState();
        curState.setInviteSuccessful(outputData.isSuccessful());
        curState.setInviteResponseMessage(outputData.getMessage());
        enrollViewModel.setState(curState);

        enrollViewModel.firePropertyChanged();
    }
}
