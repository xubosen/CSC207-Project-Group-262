package interface_adapter;

import use_case.InviteToSessionInputBoundary;
import use_case.InviteToSessionInputData;

public class InviteToSessionController {
    private InviteToSessionInputBoundary inviteToSessionInteractor;

    public InviteToSessionController(InviteToSessionInputBoundary inviteToSessionInteractor) {
        this.inviteToSessionInteractor = inviteToSessionInteractor;
    }

    public void invite(String userID, String sessionID) {
        InviteToSessionInputData inputData = new InviteToSessionInputData(userID, "", sessionID);
        inviteToSessionInteractor.invite(inputData);
    }
}
