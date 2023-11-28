package interface_adapter.create_event;

import use_case.create_event.CreateEventInputBoundary;
import use_case.create_event.CreateEventInputData;

public class CreateEventController {
    private CreateEventInputBoundary createEventInteractor;

    /**
     * Initializer for the createEventController to take in user input and transfer data.
     * @param createEventInteractor
     */
    public CreateEventController(CreateEventInputBoundary createEventInteractor) {
        this.createEventInteractor = createEventInteractor;
    }

    /**
     * Creates input data after taking inputs eventName and eventID.
     * @param eventName The created course's code
     * @param eventID The created course's name
     * @param typeOfEvent Whether it is a Lecture or a Tutorial
//     * @param courseCode The Course's code that this is creating an event on
     */
    public void createEvent(String eventName, String eventID, String typeOfEvent,
                            String creatorID, String courseCode) {
        // May or may not need employee parameter, it should be logged in state thing.
        // The adminID might not be required because it would just check logged in state potentially.
        CreateEventInputData inputData = new CreateEventInputData(eventName, eventID, typeOfEvent,
                creatorID, courseCode);
        createEventInteractor.createEvent(inputData);
    }
}
