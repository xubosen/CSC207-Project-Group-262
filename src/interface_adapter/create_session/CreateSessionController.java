package interface_adapter.create_session;

import use_case.create_session.CreateSessionInputBoundary;
import use_case.create_session.CreateSessionInputData;

import java.time.LocalDateTime;

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
     * @param sessionID The created course's name
     * @param sessionName The created course's code
     * @param description The created course's description
     * @param location The created course's location
     * @param startTime The created course's start time
     * @param endTime The created course's end time
     * @param eventID The event code that the session is being created in
     */
    public void createSession(String userID, String sessionID, String sessionName, String description, String location,
                              LocalDateTime startTime, LocalDateTime endTime, String eventID) {
        CreateSessionInputData inputData = new CreateSessionInputData(userID, sessionID, sessionName, description, location,
                startTime, endTime, eventID);
        createSessionInteractor.createSession(inputData);
    }
}
