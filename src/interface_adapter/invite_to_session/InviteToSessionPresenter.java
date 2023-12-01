package interface_adapter.invite_to_session;


import use_case.invite_to_session.InviteToSessionOutputBoundary;
import use_case.invite_to_session.InviteToSessionOutputData;

public class InviteToSessionPresenter implements InviteToSessionOutputBoundary {
    private InviteToSessionViewModel inviteToSessionViewModel;

    public InviteToSessionPresenter(InviteToSessionViewModel inviteToSessionViewModel) {
        this.inviteToSessionViewModel = inviteToSessionViewModel;
    }

    public void prepareView(InviteToSessionOutputData outputData) {
        InviteToSessionState curState = inviteToSessionViewModel.getState();
        curState.setInviteSuccessful(outputData.isSuccessful());
        curState.setInviteResponseMessage(outputData.getMessage());
        inviteToSessionViewModel.setState(curState);

        inviteToSessionViewModel.firePropertyChanged();
    }
}
