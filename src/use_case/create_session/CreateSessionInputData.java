package use_case.create_event;

public class CreateEventInputData {
    private String eventName;
    private String eventID;
    private String typeOfEvent;

    // Needs to know what course it belongs to by getting previous course button page.
    // TODO: When you create a Event do you become a staff member or is it empty. Same for Sessions

    /**
     * Initializer of the input data that the user will create when attempting to create event.
     * @param eventName The event name
     * @param eventID The event ID
     */
    public CreateEventInputData(String eventName, String eventID, String typeOfEvent) {
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
