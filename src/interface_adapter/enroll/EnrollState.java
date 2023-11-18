package interface_adapter.enroll;

public class EnrollState {
    private String userInvited = "";
    private boolean inviteSuccessful = false;
    private String inviteResponseMessage = "";

    public EnrollState(EnrollState copy) {
        userInvited = copy.userInvited;
        inviteSuccessful = copy.inviteSuccessful;
        inviteResponseMessage = copy.inviteResponseMessage;
    }

    public EnrollState () {}

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
