package interface_adapter;

import java.beans.PropertyChangeSupport;
public class UserState{
    private String userID;
    private String userType;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public UserState(String userID, String userType) {
        this.userID = userID;
        this.userType = userType;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String type) {
        this.userType = type;
    }

    public void clear() {
        this.userID = "";
        this.userType = "";
    }
}
