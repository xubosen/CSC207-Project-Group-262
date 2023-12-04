package tests.use_case.remove_from_event;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.Course;
import entity.Instructor;
import entity.Lecture;
import org.junit.Before;
import org.junit.Test;
import use_case.remove_from_event.RemoveFromEventInputData;
import use_case.remove_from_event.RemoveFromEventInteractor;

import static org.junit.Assert.*;

public class RemoveFromEventInteractorTest {
    private RemoveFromEventInteractor interactor;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemoryEventDataAccessObject eventDAO;
    private RemoveFromEventOutputBoundaryStub myPresenterStub;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        eventDAO = new InMemoryEventDataAccessObject();
        myPresenterStub = new RemoveFromEventOutputBoundaryStub();
        interactor = new RemoveFromEventInteractor(myPresenterStub, employeeDAO, eventDAO);

        // Setup initial data in DAOs
        // Add employees and events to the DAOs
    }

    @Test
    public void testRemoveFromEventEmployeeNotInEvent() {
        Instructor employee = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        employeeDAO.save(employee);
        Course course = new Course("Course Name", "COURSE123", new Instructor("ADMIN123", "Admin", "admin@example.com", "password"));
        Lecture event = new Lecture("Event Name", "EVENT123", course);
        eventDAO.save(event);

        RemoveFromEventInputData inputData = new RemoveFromEventInputData("EMP123", "EVENT123");
        interactor.removeFromEvent(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee is not in event", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromEventSuccess() {
        Instructor employee = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        employeeDAO.save(employee);
        Course course = new Course("Course Name", "COURSE123", new Instructor("ADMIN123", "Admin", "admin@example.com", "password"));
        Lecture event = new Lecture("Event Name", "EVENT123", course);
        event.addStaff(employee);
        eventDAO.save(event);

        RemoveFromEventInputData inputData = new RemoveFromEventInputData("EMP123", "EVENT123");
        interactor.removeFromEvent(inputData);

        assertTrue(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee removed from event", myPresenterStub.getOutputData().getMessage());
    }

}
