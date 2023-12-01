package interface_adapter.invite_to_session;


public class InviteToSessionState {
    private String userInvited = "";
    private boolean inviteSuccessful = false;
    private String inviteResponseMessage = "";

    public InviteToSessionState(InviteToSessionState copy) {
        userInvited = copy.userInvited;
        inviteSuccessful = copy.inviteSuccessful;
        inviteResponseMessage = copy.inviteResponseMessage;
    }

    public InviteToSessionState () {}

    public String getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(String userInvited) {
        this.userInvited = userInvited;
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
