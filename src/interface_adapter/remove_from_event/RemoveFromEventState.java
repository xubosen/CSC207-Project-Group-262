package interface_adapter.remove_from_event;

public class RemoveFromEventState {
    private String userRemoved = "";
    private String eventRemovedFrom = "";
    private boolean removeSuccessful = false;
    private String removeResponseMessage = "";

    public RemoveFromEventState() {
    }

    public RemoveFromEventState(RemoveFromEventState copy) {
        this.userRemoved = copy.getUserRemoved();
        this.eventRemovedFrom = copy.getEventRemovedFrom();
        this.removeSuccessful = copy.isRemoveSuccessful();
        this.removeResponseMessage = copy.getRemoveResponseMessage();
    }

    public String getUserRemoved() {
        return userRemoved;
    }

    public void setUserRemoved(String userRemoved) {
        this.userRemoved = userRemoved;
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

    public String getEventRemovedFrom() {
        return eventRemovedFrom;
    }

    public void setEventRemovedFrom(String eventRemovedFrom) {
        this.eventRemovedFrom = eventRemovedFrom;
    }
}
