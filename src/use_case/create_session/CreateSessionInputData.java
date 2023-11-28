package use_case.create_session;

public class CreateSessionInputData {
    private String eventName;
    private String eventID;
    private String typeOfEvent;

    // Needs to know what course it belongs to by getting previous course button page.
    // TODO: When you create a Event do you become a staff member or is it empty. Same for Sessions

    /**
     * Initializer of the input data that the user will create when attempting to create session.
     * @param sessionName
     * @param sessionID
     * @param startTime
     * @param endTime
     * @param calEventDescriptor
     * @param calEventName
     * @param location
     * @param eventID
     */
    public CreateSessionInputData(String sessionName, String sessionID, String startTime, String endTime,
                                  String calEventDescriptor, String calEventName, String location, String eventID) {
        // adminID might just pull logged in state
        this.eventName = eventName;
        this.eventID = eventID;
        this.typeOfEvent = typeOfEvent;
    }

    /**
     * Getter for event name
     * @return event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Getter for event ID
     * @return eventID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Getter for event type
     * @return whether it is a tutorial or event
     */
    public String getTypeOfEvent() {
        return typeOfEvent;
    }

}
