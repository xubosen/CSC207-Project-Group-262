package interface_adapter.get_events;

import use_case.get_events.GetEventInputBoundary;
import use_case.get_events.GetEventInputData;

public class GetEventController {
    private GetEventInputBoundary getEventInteractor;

    public GetEventController(GetEventInputBoundary getEventInteractor) {
        this.getEventInteractor = getEventInteractor;
    }

    public void getEvent(String userID) {
        GetEventInputData inputData = new GetEventInputData(userID);
        getEventInteractor.getEvent(inputData);
    }
}
