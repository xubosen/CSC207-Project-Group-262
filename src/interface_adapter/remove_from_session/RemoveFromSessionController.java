package interface_adapter.remove_from_session;

import use_case.remove_from_session.RemoveFromSessionInputBoundary;
import use_case.remove_from_session.RemoveFromSessionInputData;

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
