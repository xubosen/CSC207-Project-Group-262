package use_case.create_session;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.*;

public class CreateSessionInteractor implements CreateSessionInputBoundary {
    private CreateSessionOutputBoundary createSessionPresenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;
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
                                   InMemorySessionDataAccessObject sessionsDAO,
                                   InMemoryEmployeeDataAccessObject employeeDAO) {
        this.createSessionPresenter = createSessionPresenter;
        this.eventsDAO = eventsDAO;
        this.sessionsDAO = sessionsDAO;
        this.employeeDAO = employeeDAO;
    }

    /**
     * Tries to create the course, creates output data to input into our presenter
     * @param inputData
     */
    public void createSession(CreateSessionInputData inputData) {
        CreateSessionOutputData output;
        Employee curUser = employeeDAO.getByID(inputData.getUserID());
        Event curEvent = eventsDAO.getByID(inputData.getParentEventID());

        // If the session already exists return false and the corresponding message in output data
        if (doesSessionExist(inputData)) {
            output = new CreateSessionOutputData(false, "Session already exists.");
        } else if (!eventsDAO.existsByID(inputData.getParentEventID())) {
            output = new CreateSessionOutputData(false, "Parent event does not exist.");
        } else if (!isTimeSpanValid(inputData)) {
            output = new CreateSessionOutputData(false, "Invalid time span.");
        } else if (!doesEventExist(inputData)) {
            output = new CreateSessionOutputData(false, "Parent event does not exist.");
        } else if (!curEvent.containStaff(curUser)) {
            output = new CreateSessionOutputData(false, "Access Denied: User is not staff of parent " +
                    "event.");
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

    private boolean doesEventExist(CreateSessionInputData inputData) {
        return eventsDAO.existsByID(inputData.getParentEventID());
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

    private boolean isTimeSpanValid(CreateSessionInputData inputData) {
        DateTimeSpan dateTimeSpan = inputData.getDateTimeSpan();
        if (dateTimeSpan.getStart() == null || dateTimeSpan.getEnd() == null) {
            return false;
        } else if (dateTimeSpan.getStart().isAfter(dateTimeSpan.getEnd())) {
            return false;
        }
        return true;
    }
}
