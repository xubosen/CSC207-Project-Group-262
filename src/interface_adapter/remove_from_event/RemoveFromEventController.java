package interface_adapter.remove_from_event;

import use_case.remove_from_event.RemoveFromEventInputBoundary;
import use_case.remove_from_event.RemoveFromEventInputData;

public class RemoveFromEventController {
    private RemoveFromEventInputBoundary removeFromEventInteractor;

    public RemoveFromEventController(RemoveFromEventInputBoundary removeFromEventInteractor) {
        this.removeFromEventInteractor = removeFromEventInteractor;
    }

    public void removeFromEvent(String userID, String eventID) {
        RemoveFromEventInputData inputData = new RemoveFromEventInputData(userID, eventID);
        removeFromEventInteractor.removeFromEvent(inputData);
    }
}
