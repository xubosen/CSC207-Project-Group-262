package tests.use_case.create_session;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import interface_adapter.create_session.CreateSessionPresenter;
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
import use_case.invite_to_session.InviteToSessionOutputData;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CreateSessionInteractorTest {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();
    private InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();

    @Before
    public void setUp() {
        // Create some employees
        employeeDAO.addEmployee(new Instructor("admin", "test admin", "admin@test.com", "1"));
        employeeDAO.addEmployee(new Instructor("instructor1", "instructor 1", "ins1@test.com", "1"));
        employeeDAO.addEmployee(new TeachingAssistant("ta1", "TA 1", "t1@test.com", "1"));
        employeeDAO.addEmployee(new TeachingAssistant("ta2", "TA 2", "t2@test.com", "1"));

        // Create some courses & add some employees to them
        Course course1 = new Course("Test Course1", "TEST1", (Instructor) employeeDAO.getByID("admin"));
        Course course2 = new Course("Test Course2", "TEST2", (Instructor) employeeDAO.getByID("admin"));

        // Add the courses to the courseDAO
        courseDAO.addCourse(course1);
        courseDAO.addCourse(course2);

        // Create some events
        Lecture lecture = new Lecture("Test Lecture", "TEST_LEC", course1);
        Tutorial tutorial = new Tutorial("Test Tutorial", "TEST_TUT", course1);

        // Add the events to the eventDAO
        eventDAO.addEvent(lecture);
        eventDAO.addEvent(tutorial);
        lecture.addStaff((Instructor) employeeDAO.getByID("instructor1"));
    }

    @Test
    public void testCreateSessionFailure_ParentEventDoesNotExist() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        // Create an event with the ID "session123" to simulate an existing session
        Course course1 = new Course("Test Course1", "TEST1", (Instructor) employeeDAO.getByID("admin"));
        Event existingEvent = new Lecture("Existing Session", "session123", course1);
        eventDAO.addEvent(existingEvent);

        CreateSessionInputData inputData = new CreateSessionInputData("session123", "Test Session", "This is a test session", "session123", "Room 101", startTime, endTime, "123");
        CreateSessionOutputSpy mockPresenter = new CreateSessionOutputSpy();
        CreateSessionInteractor createSessionInteractor = new CreateSessionInteractor(mockPresenter, eventDAO, sessionDAO, employeeDAO);
        createSessionInteractor.createSession(inputData);

        CreateSessionOutputData outputData = mockPresenter.getOutputData();

        assertFalse(outputData.isSuccessful());
        assertEquals("Parent event does not exist.", outputData.getMessage());
        assertNull(sessionDAO.getByID("session123"));
    }

    @Test
    public void testCreateSessionSuccess() {
        // Time setup
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);
        
        // Create an employee
        Instructor admin = new Instructor("admin123", "Admin Name", "admin@example.com", "password");
        employeeDAO.addEmployee(admin);

        // Create an event with the ID "session1234" to simulate an existing session
        Course course1 = new Course("Test Course1", "TEST1", (Instructor) employeeDAO.getByID("admin123"));
        Event newEvent = new Lecture("New Session", "session1234", course1);
        newEvent.addStaff(admin);
        eventDAO.addEvent(newEvent);
        CreateSessionPresenterSpy presenterSpy = new CreateSessionPresenterSpy();


        // Create the input data
        CreateSessionInputData inputData = new CreateSessionInputData(
                "admin123", "1", "session1", "description",
                "location", startTime, endTime, "session1234");

        // Create the Interactor
        CreateSessionInteractor interactor = new CreateSessionInteractor(presenterSpy, eventDAO, sessionDAO, employeeDAO);

        // Call the Interactor
        interactor.createSession(inputData);

        // Check the output data
        CreateSessionOutputData outputData = presenterSpy.getOutputData();
        assertTrue(outputData.isSuccessful());
        assertEquals("Session created successfully", outputData.getMessage());
    }

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

    public class CreateSessionPresenterSpy implements CreateSessionOutputBoundary {
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
