package interface_adapter.invite_to_session;


public class InviteToSessionState {
    private String userInvited = "";
    private String sessionID = "";
    private boolean inviteSuccessful = false;
    private String inviteResponseMessage = "";

    public InviteToSessionState(InviteToSessionState copy) {
        userInvited = copy.userInvited;
        inviteSuccessful = copy.inviteSuccessful;
        inviteResponseMessage = copy.inviteResponseMessage;
        sessionID = copy.sessionID;
    }

    public InviteToSessionState () {}

    public String getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(String userInvited) {
        this.userInvited = userInvited;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public boolean isInviteSuccessful() {
        return inviteSuccessful;
    }

    public void setInviteSuccessful(boolean inviteSuccessful) {
        this.inviteSuccessful = inviteSuccessful;
    }

    public String getInviteResponseMessage() {
        return inviteResponseMessage;
    }

    public void setInviteResponseMessage(String inviteResponseMessage) {
        this.inviteResponseMessage = inviteResponseMessage;
    }
}
