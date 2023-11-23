package use_case;


public class EventAdditionInputData {

    private String userID;
    private String email;
    private String eventID;

    public EventAdditionInputData(String userID, String email, String eventID) {
        this.userID = userID;
        this.email = email;
        this.eventID = eventID;
    }

    public String getUserID() {
        return userID;
    }


    public String getEmail() {
        return email;
    }

    public String getEventID() {
        return eventID;
    }
}