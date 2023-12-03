package interface_adapter.invite_to_session;

import use_case.invite_to_session.InviteToSessionInputBoundary;
import use_case.invite_to_session.InviteToSessionInputData;

public class InviteToSessionController {
    private InviteToSessionInputBoundary inviteToSessionInteractor;

    public InviteToSessionController(InviteToSessionInputBoundary inviteToSessionInteractor) {
        this.inviteToSessionInteractor = inviteToSessionInteractor;
    }

    public void invite(String inviteeID, String invitorID, String sessionID) {
        InviteToSessionInputData inputData = new InviteToSessionInputData(inviteeID, invitorID, sessionID);
        inviteToSessionInteractor.invite(inputData);
    }
}
