package interface_adapter;

import use_case.RemoveFromSessionInputBoundary;
import use_case.RemoveFromSessionInputData;

public class RemoveFromSessionController {
    private RemoveFromSessionInputBoundary removeFromSessionInteractor;

    public RemoveFromSessionController(RemoveFromSessionInputBoundary removeFromSessionInteractor) {
        this.removeFromSessionInteractor = removeFromSessionInteractor;
    }

    public void removeFromSession(String userID, String courseCode) {
        RemoveFromSessionInputData inputData = new RemoveFromSessionInputData(userID, courseCode);
        removeFromSessionInteractor.removeFromSession(inputData);
    }
}
