package use_case.add_to_event;


public class EventAdditionInputData {

    private String userID;
    private String inviteeID;
    private String eventID;

    public EventAdditionInputData(String userID, String inviteeID, String eventID) {
        this.userID = userID;
        this.inviteeID = inviteeID;
        this.eventID = eventID;
    }

    public String getUserID() {
        return userID;
    }

    public String getInviteeID() {
        return inviteeID;
    }

    public String getEventID() {
        return eventID;
    }
}