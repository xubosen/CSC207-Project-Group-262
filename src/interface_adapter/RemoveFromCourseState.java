package interface_adapter;

public class RemoveFromCourseState {
    private String userRemoved = "";
    private String courseRemovedFrom = "";
    private boolean removeSuccessful = false;
    private String removeResponseMessage = "";

    public RemoveFromCourseState(){

    }

    public RemoveFromCourseState(RemoveFromCourseState copy) {
        this.userRemoved = copy.getUserRemoved();
        this.courseRemovedFrom = copy.getCourseRemovedFrom();
        this.removeSuccessful = copy.isRemoveSuccessful();
        this.removeResponseMessage = copy.getRemoveResponseMessage();
    }

    public String getCourseRemovedFrom() {
        return courseRemovedFrom;
    }

    public void setUserRemoved(String userRemoved) {
        this.userRemoved = userRemoved;
    }

    public boolean isRemoveSuccessful() {
        return removeSuccessful;
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

    public void setCourseRemovedFrom(String courseRemovedFrom) {
        this.courseRemovedFrom = courseRemovedFrom;
    }

    public String getUserRemoved() {
        return userRemoved;
    }

}
