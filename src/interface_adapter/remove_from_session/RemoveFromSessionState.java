package interface_adapter.remove_from_session;

public class RemoveFromSessionState {
    private String userRemoved = "";
    private String sessionRemovedFrom = "";
    private boolean removeSuccessful = false;
    private String removeResponseMessage = "";

    public RemoveFromSessionState() {
    }

    public RemoveFromSessionState(RemoveFromSessionState copy) {
        this.userRemoved = copy.getUserRemoved();
        this.removeSuccessful = copy.isRemoveSuccessful();
        this.removeResponseMessage = copy.getRemoveResponseMessage();
        this.sessionRemovedFrom = copy.getSessionRemovedFrom();
    }

    public String getUserRemoved() {
        return userRemoved;
    }

    public void setUserRemoved(String userRemoved) {
        this.userRemoved = userRemoved;
    }

    public String getSessionRemovedFrom() {
        return sessionRemovedFrom;
    }

    public void setSessionRemovedFrom(String sessionRemovedFrom) {
        this.sessionRemovedFrom = sessionRemovedFrom;
    }

    public boolean isRemoveSuccessful() {
        return this.removeSuccessful;
    }

    public void setRemoveSuccessful(boolean removeSuccessful) {
        this.removeSuccessful = removeSuccessful;
    }

    public String getRemoveResponseMessage() {
        return removeResponseMessage;
    }

    public void setRemoveResponseMessage(String removeResponseMessage) {
        this.removeResponseMessage = removeResponseMessage;
    }
}
