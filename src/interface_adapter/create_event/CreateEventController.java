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
        CreateEventInputData inputData = new CreateEventInputData(eventName, eventID, typeOfEvent,
                creatorID, courseCode);
//        viewInputData(inputData);
        createEventInteractor.createEvent(inputData);
    }

    // TODO: Delete this method after testing is done
    private void viewInputData(CreateEventInputData inputData) {
        System.out.println("Event Name: " + inputData.getEventName());
        System.out.println("Event ID: " + inputData.getEventID());
        System.out.println("Type of Event: " + inputData.getTypeOfEvent());
        System.out.println("Creator ID: " + inputData.getCreatorID());
        System.out.println("Course Code: " + inputData.getCourseCode());
    }
}
