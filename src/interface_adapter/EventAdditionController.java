package interface_adapter;

// Import necessary classes
import use_case.EventAdditionInputBoundary;
import use_case.EventAdditionInputData;



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