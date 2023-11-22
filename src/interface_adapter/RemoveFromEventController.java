package interface_adapter;

import use_case.RemoveFromEventInputBoundary;
import use_case.RemoveFromEventInputData;

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
