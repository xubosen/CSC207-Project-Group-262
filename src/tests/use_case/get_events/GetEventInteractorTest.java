package tests.use_case.get_events;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import org.junit.Before;
import org.junit.Test;
import tests.use_case.create_event.CreateEventInteractorTest;
import use_case.create_event.CreateEventInputData;
import use_case.create_event.CreateEventInteractor;
import use_case.create_event.CreateEventOutputData;
import use_case.get_events.*;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.*;

import static org.junit.Assert.*;

public class GetEventInteractorTest {
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
    public void testGetEventDetails() {
        GetEventInputData inputData = new GetEventInputData("instructor1");
        GetEventInteractorTest.GetEventOutputSpy mockPresenter = new GetEventOutputSpy();
        GetEventInteractor getEventInteractor = new GetEventInteractor(mockPresenter, employeeDAO);
        getEventInteractor.getEvent(inputData);
        GetEventOutputData outputData = mockPresenter.getOutputData();

        assertNotNull(outputData);
        assertFalse(outputData.getEventIDs().contains("EVENT123"));
    }

    private class GetEventOutputSpy implements GetEventOutputBoundary {
        private GetEventOutputData outputData;

        @Override
        public void present(GetEventOutputData outputData) {
            this.outputData = outputData;
        }

        public GetEventOutputData getOutputData() {
            return outputData;
        }
    }
}
