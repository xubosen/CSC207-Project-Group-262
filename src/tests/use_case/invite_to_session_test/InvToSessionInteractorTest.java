package tests.use_case.invite_to_session_test;

// import entities
import entity.*;

// import use cases
import use_case.invite_to_session.InviteToSessionInputData;
import use_case.invite_to_session.InviteToSessionInteractor;
import use_case.invite_to_session.InviteToSessionOutputBoundary;
import use_case.invite_to_session.InviteToSessionOutputData;

// import data access objects
import data_access.in_memory_dao.*;

// junit imports
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// java imports
import java.time.LocalDateTime;


public class InvToSessionInteractorTest {
    // Data access objects
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();
    private InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject();

    // Employees
    private Instructor admin;
    private Instructor instructor1;
    private TeachingAssistant ta1;
    private TeachingAssistant ta2;

    // Courses
    private Course course1;

    // Events
    private Lecture lecture;

    // Sessions
    private ClassSession session;


    @Before
    public void setUp() {
        // Create some employees
        employeeDAO.addEmployee(new Instructor("admin", "test admin", "admin@test.com", "1"));
        employeeDAO.addEmployee(new Instructor("instructor1", "instructor 1", "ins1@test.com", "1"));
        employeeDAO.addEmployee(new TeachingAssistant("ta1", "TA 1", "t1@test.com", "1"));
        employeeDAO.addEmployee(new TeachingAssistant("ta2", "TA 2", "t2@test.com", "1"));

        admin = (Instructor) employeeDAO.getByID("admin");
        instructor1 = (Instructor) employeeDAO.getByID("instructor1");
        ta1 = (TeachingAssistant) employeeDAO.getByID("ta1");
        ta2 = (TeachingAssistant) employeeDAO.getByID("ta2");

        // Create some courses & add some employees to them
        courseDAO.addCourse(new Course("Test Course1", "TEST1", admin));
        courseDAO.addCourse(new Course("Test Course2", "TEST2", admin));

        course1 = courseDAO.getByID("TEST1");

        course1.addStaff(instructor1);
        course1.addStaff(ta1);
        course1.addStaff(ta2);

        // Create some events
        lecture = new Lecture("Test Lecture", "TEST_LEC", course1);
        eventDAO.addEvent(lecture);
        lecture.addStaff(instructor1);
        lecture.addStaff(ta2);

        // Create some sessions
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 01, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 1, 02, 0);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(start, end);
        CalendarEvent calendarEvent = new CalendarEvent("Test Cal Session", "Test",
                dateTimeSpan);
        session = new ClassSession("Test", "Test Session", calendarEvent, "Test Location", lecture);
        session.addStaff(instructor1);

        sessionDAO.addSession(session);

    }

    @Test
    public void testInviteToSessionInvalidInviteeID(){
        // Create the input data
        InviteToSessionInputData inputData = new InviteToSessionInputData("invalid id", instructor1.getUID(),
                session.getSessionID());

        // Create the presenter spy
        InvToSessionPresenterSpy presenterSpy = new InvToSessionPresenterSpy();

        // Create the interactor
        InviteToSessionInteractor interactor = new InviteToSessionInteractor(presenterSpy, employeeDAO, sessionDAO);

        // Call the interactor
        interactor.invite(inputData);

        // Check the output data
        InviteToSessionOutputData outputData = presenterSpy.getOutputData();
        assertFalse(outputData.isSuccessful());
        assertEquals("Invitee does not exist", outputData.getMessage());
    }

    @Test
    public void testInviteToSessionInviteeNotStaffOfParentEvent() {
        // Create the input data
        InviteToSessionInputData inputData = new InviteToSessionInputData(ta1.getUID(), instructor1.getUID(),
                session.getSessionID());

        // Create the presenter spy
        InvToSessionPresenterSpy presenterSpy = new InvToSessionPresenterSpy();

        // Create the interactor
        InviteToSessionInteractor interactor = new InviteToSessionInteractor(presenterSpy, employeeDAO, sessionDAO);

        // Call the interactor
        interactor.invite(inputData);

        // Check the output data
        InviteToSessionOutputData outputData = presenterSpy.getOutputData();
        assertFalse(outputData.isSuccessful());
        assertEquals("Invitee is not staff of the event", outputData.getMessage());
    }

    @Test
    public void testInviteToSessionInvitorNotStaffOfParentEvent() {
        // Create the input data
        InviteToSessionInputData inputData = new InviteToSessionInputData(ta2.getUID(), ta1.getUID(),
                session.getSessionID());

        // Create the presenter spy
        InvToSessionPresenterSpy presenterSpy = new InvToSessionPresenterSpy();

        // Create the interactor
        InviteToSessionInteractor interactor = new InviteToSessionInteractor(presenterSpy, employeeDAO, sessionDAO);

        // Call the interactor
        interactor.invite(inputData);

        // Check the output data
        InviteToSessionOutputData outputData = presenterSpy.getOutputData();
        assertFalse(outputData.isSuccessful());
        assertEquals("Invitor is not staff of the event", outputData.getMessage());
    }

    @Test
    public void testInviteToSessionInvalidSessionID() {
        // Create the input data
        InviteToSessionInputData inputData = new InviteToSessionInputData(ta2.getUID(), instructor1.getUID(),
                "invalid id");

        // Create the presenter spy
        InvToSessionPresenterSpy presenterSpy = new InvToSessionPresenterSpy();

        // Create the interactor
        InviteToSessionInteractor interactor = new InviteToSessionInteractor(presenterSpy, employeeDAO, sessionDAO);

        // Call the interactor
        interactor.invite(inputData);

        // Check the output data
        InviteToSessionOutputData outputData = presenterSpy.getOutputData();
        assertFalse(outputData.isSuccessful());
        assertEquals("Session does not exist", outputData.getMessage());
    }

    @Test
    public void testInviteToSessionInviteeAlreadyEnrolled() {
        // Create the input data
        InviteToSessionInputData inputData = new InviteToSessionInputData(instructor1.getUID(), instructor1.getUID(),
                session.getSessionID());

        // Create the presenter spy
        InvToSessionPresenterSpy presenterSpy = new InvToSessionPresenterSpy();

        // Create the interactor
        InviteToSessionInteractor interactor = new InviteToSessionInteractor(presenterSpy, employeeDAO, sessionDAO);

        // Call the interactor
        interactor.invite(inputData);

        // Check the output data
        InviteToSessionOutputData outputData = presenterSpy.getOutputData();
        assertFalse(outputData.isSuccessful());
        assertEquals("Employee is already enrolled in the session", outputData.getMessage());
    }

    @Test
    public void testInviteToSessionSuccess() {
        // Create the input data
        InviteToSessionInputData inputData = new InviteToSessionInputData(ta2.getUID(), instructor1.getUID(),
                session.getSessionID());

        // Create the presenter spy
        InvToSessionPresenterSpy presenterSpy = new InvToSessionPresenterSpy();

        // Create the interactor
        InviteToSessionInteractor interactor = new InviteToSessionInteractor(presenterSpy, employeeDAO, sessionDAO);

        // Call the interactor
        interactor.invite(inputData);

        // Check the output data
        InviteToSessionOutputData outputData = presenterSpy.getOutputData();
        assertTrue(outputData.isSuccessful());
        assertEquals("Enroll successful", outputData.getMessage());
    }


    private class InvToSessionPresenterSpy implements InviteToSessionOutputBoundary {
        private InviteToSessionOutputData outputData;

        @Override
        public void prepareView(InviteToSessionOutputData outputData) {
            this.outputData = outputData;
        }

        public InviteToSessionOutputData getOutputData() {
            return outputData;
        }
    }
}
