package interface_adapter.add_to_event;

public class EventAdditionState {
    private String userInvited = "";
    private String eventID = "";
    private boolean inviteSuccessful = false;
    private String inviteResponseMessage = "";

    public EventAdditionState(EventAdditionState copy) {
        userInvited = copy.userInvited;
        inviteSuccessful = copy.inviteSuccessful;
        inviteResponseMessage = copy.inviteResponseMessage;
    }

    public EventAdditionState () {}

    public String getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(String userInvited) {
        this.userInvited = userInvited;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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