package use_case.invite_to_session;

public class InviteToSessionInputData {
    private String inviteeID;
    private String invitorID;
    private String sessionID;

    public InviteToSessionInputData(String inviteeID, String invitorID, String sessionID) {
        this.sessionID = sessionID;
        this.inviteeID = inviteeID;
        this.invitorID = invitorID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getInviteeID() {
        return inviteeID;
    }

    public String getInvitorID() {
        return invitorID;
    }
}
