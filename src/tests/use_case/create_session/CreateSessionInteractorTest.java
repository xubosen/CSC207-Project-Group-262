package tests.use_case.create_session;

import org.junit.Before;
import org.junit.Test;
import use_case.create_session.CreateSessionInputData;
import use_case.create_session.CreateSessionInteractor;
import use_case.create_session.CreateSessionOutputBoundary;
import use_case.create_session.CreateSessionOutputData;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.*;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CreateSessionInteractorTest {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();
    private InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject();

    // Example setup, adapt as necessary
    @Before
    public void setUp() {
        // Initialize your DAOs and any other necessary setup
    }

    @Test
    public void testCreateSessionFail() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);
        CreateSessionInputData inputData = new CreateSessionInputData("session123", "Test Session", "This is a test session", "session123","Room 101", startTime, endTime, "123");
        CreateSessionOutputSpy mockPresenter = new CreateSessionOutputSpy();
        CreateSessionInteractor createSessionInteractor = new CreateSessionInteractor(mockPresenter, eventDAO, sessionDAO, employeeDAO);
        createSessionInteractor.createSession(inputData);

        CreateSessionOutputData outputData = mockPresenter.getOutputData();

        assertFalse(outputData.isSuccessful());
        assertNotEquals("Session created successfully", outputData.getMessage());
        assertNull(sessionDAO.getByID("session123"));
    }

    // Additional tests for failure scenarios

    private class CreateSessionOutputSpy implements CreateSessionOutputBoundary {
        private CreateSessionOutputData outputData;

        @Override
        public void prepareView(CreateSessionOutputData outputData) {
            this.outputData = outputData;
        }

        public CreateSessionOutputData getOutputData() {
            return outputData;
        }
    }
}
