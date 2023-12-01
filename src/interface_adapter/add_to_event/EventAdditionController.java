package interface_adapter.add_to_event;

// Import necessary classes
import use_case.add_to_event.EventAdditionInputBoundary;
import use_case.add_to_event.EventAdditionInputData;



public class EventAdditionController {
    private EventAdditionInputBoundary eventAdditionInteractor;

    public EventAdditionController(EventAdditionInputBoundary eventAdditionInteractor) {
        this.eventAdditionInteractor = eventAdditionInteractor;
    }

    public void addEmployeeToEvent(String userID, String eventID) {
        EventAdditionInputData inputData = new EventAdditionInputData(userID, "", eventID);
        eventAdditionInteractor.addEmployeeToEvent(inputData);

    }
}