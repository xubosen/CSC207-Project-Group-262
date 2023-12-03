package use_case.get_events;

public class GetEventInputData {
    private String userID;

    public GetEventInputData(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
