package interface_adapter.get_sessions;

import use_case.get_courses.GetCoursesInputBoundary;
import use_case.get_sessions.GetSessionsInputBoundary;
import use_case.get_sessions.GetSessionsInputData;

public class GetSessionsController {
    private GetSessionsInputBoundary getSessionsInteractor;

    public GetSessionsController(GetSessionsInputBoundary getSessionsInteractor) {
        this.getSessionsInteractor = getSessionsInteractor;
    }

    public void getSessions(String userID) {
        GetSessionsInputData inputData = new GetSessionsInputData(userID);
        getSessionsInteractor.getSessions(inputData);
    }
}
