package tests.use_case.invite_to_event_test;

// import entities
import entity.*;

// import use cases
import use_case.add_to_event.EventAdditionInputData;
import use_case.add_to_event.EventAdditionInteractor;
import use_case.add_to_event.EventAdditionOutputBoundary;
import use_case.add_to_event.EventAdditionOutputData;

// import data access objects
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;

// junit imports
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InvToEventInteractorTest {
    // Data access objects
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();

    // Employees
    private Instructor admin;
    private Instructor instructor1;
    private TeachingAssistant ta1;
    private TeachingAssistant ta2;

    // Courses
    private Course course1;
    private Course course2;

    // Events
    private Lecture lecture;
    private Tutorial tutorial;


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
        course2 = courseDAO.getByID("TEST2");

        course1.addStaff(instructor1);
        course1.addStaff(ta1);

        course2.addStaff(instructor1);

        // Create some events
        lecture = new Lecture("Test Lecture", "TEST_LEC", course1);
        tutorial = new Tutorial("Test Tutorial", "TEST_TUT", course1);
        eventDAO.addEvent(lecture);
        eventDAO.addEvent(tutorial);
        lecture.addStaff(instructor1);
    }

    @Test
    public void testAddToEventInvalidInviteeID() {
        // Create an input data object
        EventAdditionInputData inputData = new EventAdditionInputData("admin", "invalid", "TEST_LEC");

        // Create an output spy
        InvToEventOutputSpy outputSpy = new InvToEventOutputSpy();

        // Create an interactor
        EventAdditionInteractor interactor = new EventAdditionInteractor(outputSpy, employeeDAO, eventDAO);

        // Call the interactor
        interactor.addEmployeeToEvent(inputData);

        // Check the output
        assertFalse(outputSpy.getOutputData().isSuccessful());
        assertEquals("Invitee does not exist", outputSpy.getOutputData().getMessage());
    }

    @Test
    public void testAddToEventInvalidEventID () {
        // Create an input data object
        EventAdditionInputData inputData = new EventAdditionInputData("admin", "instructor1", "invalid");

        // Create an output spy
        InvToEventOutputSpy outputSpy = new InvToEventOutputSpy();

        // Create an interactor
        EventAdditionInteractor interactor = new EventAdditionInteractor(outputSpy, employeeDAO, eventDAO);

        // Call the interactor
        interactor.addEmployeeToEvent(inputData);

        // Check the output
        assertFalse(outputSpy.getOutputData().isSuccessful());
        assertEquals("Event does not exist", outputSpy.getOutputData().getMessage());
    }

    @Test
    public void testAddToEventInviteeNotInCourse () {
        // Create an input data object
        EventAdditionInputData inputData = new EventAdditionInputData("admin", "ta2", "TEST_LEC");

        // Create an output spy
        InvToEventOutputSpy outputSpy = new InvToEventOutputSpy();

        // Create an interactor
        EventAdditionInteractor interactor = new EventAdditionInteractor(outputSpy, employeeDAO, eventDAO);

        // Call the interactor
        interactor.addEmployeeToEvent(inputData);

        // Check the output
        assertFalse(outputSpy.getOutputData().isSuccessful());
        assertEquals("Access Denied: The employee you are trying to add is not a staff of this course", outputSpy.getOutputData().getMessage());
    }

    @Test
    public void testAddToEventInviteeAlreadyInEvent () {
        // Create an input data object
        EventAdditionInputData inputData = new EventAdditionInputData("admin", "instructor1", "TEST_LEC");

        // Create an output spy
        InvToEventOutputSpy outputSpy = new InvToEventOutputSpy();

        // Create an interactor
        EventAdditionInteractor interactor = new EventAdditionInteractor(outputSpy, employeeDAO, eventDAO);

        // Call the interactor
        interactor.addEmployeeToEvent(inputData);

        // Check the output
        assertFalse(outputSpy.getOutputData().isSuccessful());
        assertEquals("This user is already enrolled in the event", outputSpy.getOutputData().getMessage());
    }

    @Test
    public void testAddToEventSuccess () {
        // Create an input data object
        EventAdditionInputData inputData = new EventAdditionInputData("admin", "ta1", "TEST_LEC");

        // Create an output spy
        InvToEventOutputSpy outputSpy = new InvToEventOutputSpy();

        // Create an interactor
        EventAdditionInteractor interactor = new EventAdditionInteractor(outputSpy, employeeDAO, eventDAO);

        // Call the interactor
        interactor.addEmployeeToEvent(inputData);

        // Check the output
        assertTrue(outputSpy.getOutputData().isSuccessful());
        assertEquals("Enroll Successful!", outputSpy.getOutputData().getMessage());
    }

    private class InvToEventOutputSpy implements EventAdditionOutputBoundary {
        private EventAdditionOutputData outputData = null;

        public InvToEventOutputSpy() {
        }

        @Override
        public void prepareView(EventAdditionOutputData outputData) {
            this.outputData = outputData;
        }

        public EventAdditionOutputData getOutputData() {
            return outputData;
        }

    }
}
