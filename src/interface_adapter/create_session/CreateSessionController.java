package interface_adapter.create_session;

import use_case.create_session.CreateSessionInputBoundary;
import use_case.create_session.CreateSessionInputData;

public class CreateSessionController {
    private CreateSessionInputBoundary createSessionInteractor;

    /**
     * Initializer for the createSessionController to take in user input and transfer data.
     * @param createSessionInteractor
     */
    public CreateSessionController(CreateSessionInputBoundary createSessionInteractor) {
        this.createSessionInteractor = createSessionInteractor;
    }

    /**
     * Creates input data after taking inputs eventName and eventID.
     * @param sessionName The created course's code
     * @param sessionID The created course's name
//     * @param eventID The Events's code that this is creating a session on
     */
    public void createSession(String sessionName, String sessionID) {
        // To create session we need sessionID, sessionName, calEvent (start and end time as well as description and name), event, location
        // May or may not need employee parameter, it should be logged in state thing.
        // The adminID might not be required because it would just check logged in state potentially.
        CreateSessionInputData inputData = new CreateSessionInputData(eventName, eventID); // previous event clicked on
        createSessionInteractor.createSession(inputData);
    }
}
