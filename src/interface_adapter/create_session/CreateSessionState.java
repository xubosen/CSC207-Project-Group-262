package interface_adapter.create_session;

public class CreateSessionState {

    private String eventName = "";
    private String eventID = "";
    private boolean eventCreationSuccessful = false;
    private String eventCreationResponseMessage = "";
//     TODO: Want to check whether they want to create tutorial or Lecture.
    private String eventType = "";

    /**
     * Initializer for CreateEventState when you have a pre-existing CreateEventState.
     * @param copy The Pre-existing CreateEventState.
     */
    public CreateSessionState(CreateSessionState copy) {
        eventName = copy.eventName;
        eventID = copy.eventID;
        eventCreationSuccessful = copy.eventCreationSuccessful;
        eventCreationResponseMessage = copy.eventCreationResponseMessage;
        eventType = copy.eventType;
    }

    /**
     * Initializer if there is no input.
     */
    public CreateSessionState() {}

    /**
     * Getter for eventID variable.
     * @return eventID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Setter method to change the eventID attribute.
     * @param eventCreated
     */
    public void setEventID(String eventCreated) {
        this.eventID = eventID;
    }

    /**
     * Getter method to retrieve the event name.
     * @return event name state
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Setter method to set event name.
     * @param eventName What you want the created event's name to be
     */
    public void setCourseName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Getter for whether event creation was successful.
     * @return Whether course was created or not
     */
    public boolean isEventCreationSuccessful() {
        return eventCreationSuccessful;
    }

    /**
     * Setter method for boolean on whether event was created successfully.
     * @param eventCreationSuccessful
     */
    public void setEventCreationSuccessful(boolean eventCreationSuccessful) {
        this.eventCreationSuccessful = eventCreationSuccessful;
    }

    /**
     * Getter for the response message.
     * @return Message on event creation status
     */
    public String getEventCreationResponseMessage() {
        return eventCreationResponseMessage;
    }

    /**
     * Setter for changing the event creation message.
     * @param eventCreationResponseMessage The messaage you want to output for whether course creation worked.
     */
    public void setEventCreationResponseMessage(String eventCreationResponseMessage) {
        this.eventCreationResponseMessage = eventCreationResponseMessage;
    }

    /**
     * Getter for eventType variable.
     * @return eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Setter method to change the eventID attribute.
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
