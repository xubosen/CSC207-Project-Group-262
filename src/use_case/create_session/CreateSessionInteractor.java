package use_case.create_session;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.CalendarEvent;
import entity.ClassSession;
import entity.DateTimeSpan;
import entity.Event;

public class CreateSessionInteractor implements CreateSessionInputBoundary {
    private CreateSessionOutputBoundary createSessionPresenter;
    private InMemoryEventDataAccessObject eventsDAO;
    private InMemorySessionDataAccessObject sessionsDAO;

    /**
     * Initializes the event creator interactor.
     * @param createSessionPresenter Create Session output boundary.
     * @param eventsDAO The In memory DAO for events
     * @param sessionsDAO The In memory DAO for sessions
     */
    public CreateSessionInteractor(CreateSessionOutputBoundary createSessionPresenter,
                                   InMemoryEventDataAccessObject eventsDAO,
                                   InMemorySessionDataAccessObject sessionsDAO) {
        this.createSessionPresenter = createSessionPresenter;
        this.eventsDAO = eventsDAO;
        this.sessionsDAO = sessionsDAO;
    }

    /**
     * Tries to create the course, creates output data to input into our presenter
     * @param inputData
     */
    public void createSession(CreateSessionInputData inputData) {
        CreateSessionOutputData output;

        // If the session already exists return false and the corresponding message in output data
        if (!doesSessionExist(inputData)) {
            output = new CreateSessionOutputData(false, "Session already exists.");
        } else {
            // Create the session
            ClassSession newSession = makeSession(inputData);
            sessionsDAO.save(newSession);
            output = new CreateSessionOutputData(true, "Session created successfully");
        }

        // Call the presenter to present the output data
        createSessionPresenter.prepareView(output);
    }

    private boolean doesSessionExist(CreateSessionInputData inputData) {
        return sessionsDAO.existsByID(inputData.getSessionID());
    }

    private ClassSession makeSession(CreateSessionInputData inputData) {
        String sessionID = inputData.getSessionID();
        String sessionName = inputData.getSessionName();
        String description = inputData.getDescription();
        String location = inputData.getLocation();
        String parentEventID = inputData.getParentEventID();
        DateTimeSpan dateTimeSpan = inputData.getDateTimeSpan();

        CalendarEvent calEvent = new CalendarEvent(sessionName, description, dateTimeSpan);
        Event parentEvent = eventsDAO.getByID(parentEventID);
        ClassSession session = new ClassSession(sessionID, sessionName, calEvent, location, parentEvent);
        return session;
    }
}
