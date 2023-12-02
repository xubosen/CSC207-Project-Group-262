package use_case.invite_to_session;

public class InviteToSessionInputData {
    private String userID;
    private String email;
    private String sessionID;

    public InviteToSessionInputData(String userID, String email, String sessionID) {
        this.sessionID = sessionID;
        this.email = email;
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }
}
