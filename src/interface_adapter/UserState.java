package interface_adapter;

public class UserState {
    private String userID;
    private String userType;

    public UserState(String userID, String userType) {
        this.userID = userID;
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public String setUserID() {
        return userID;
    }

    public String getUserType() {
        return userType;
    }

    public String setUserType() {
        return userType;
    }
}
