package interface_adapter;


import use_case.InviteToSessionOutputBoundary;
import use_case.InviteToSessionOutputData;

public class InviteToSessionPresenter implements InviteToSessionOutputBoundary {
    private InviteToSessionViewModel inviteToSessionViewModel;

    public InviteToSessionPresenter(InviteToSessionViewModel inviteToSessionViewModel) {
        this.inviteToSessionViewModel = inviteToSessionViewModel;
    }

    public void prepareView(InviteToSessionOutputData outputData) {
        System.out.println(outputData.getMessage());
        InviteToSessionState curState = inviteToSessionViewModel.getState();
        curState.setInviteSuccessful(outputData.isSuccessful());
        curState.setInviteResponseMessage(outputData.getMessage());
        inviteToSessionViewModel.setState(curState);

        inviteToSessionViewModel.firePropertyChanged();
    }
}
