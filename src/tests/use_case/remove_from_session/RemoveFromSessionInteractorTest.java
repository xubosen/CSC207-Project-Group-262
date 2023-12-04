package tests.use_case.remove_from_session;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.*;
import org.junit.Before;
import org.junit.Test;
import use_case.remove_from_session.RemoveFromSessionInputData;
import use_case.remove_from_session.RemoveFromSessionInteractor;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RemoveFromSessionInteractorTest {
    private RemoveFromSessionInteractor interactor;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemorySessionDataAccessObject sessionDAO;
    private RemoveFromSessionOutputBoundaryStub myPresenterStub;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        sessionDAO = new InMemorySessionDataAccessObject();
        myPresenterStub = new RemoveFromSessionOutputBoundaryStub();
        interactor = new RemoveFromSessionInteractor(myPresenterStub, employeeDAO, sessionDAO);

        // Add initial data to DAOs if necessary

    }

    @Test
    public void testRemoveFromSessionEmployeeDoesNotExist() {
        RemoveFromSessionInputData inputData = new RemoveFromSessionInputData("nonExistentEmployeeID", "SESSION123");
        interactor.removeFromSession(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee does not exist", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromSessionSessionDoesNotExist() {
        employeeDAO.save(new Instructor("EMP123", "John Doe", "john@example.com", "password"));
        RemoveFromSessionInputData inputData = new RemoveFromSessionInputData("EMP123", "nonExistentSessionID");
        interactor.removeFromSession(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Session does not exist", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromSessionEmployeeNotInSession() {
        Instructor employee = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        employeeDAO.save(employee);
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);
        CalendarEvent calEvent = new CalendarEvent("EventName", "Description", timeSpan);
        Instructor admin = new Instructor("adminID", "AdminName", "admin@example.com", "password");
        Course course = new Course("CourseName", "COURSE01", admin);
        Event event = new Lecture("EventName", "EVENT01", course);
        ClassSession session = new ClassSession("SESSION123", "Session Name", calEvent, "location", event);
        sessionDAO.save(session);

        RemoveFromSessionInputData inputData = new RemoveFromSessionInputData("EMP123", "SESSION123");
        interactor.removeFromSession(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee is not in the session", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromSessionSuccess() {
        Instructor employee = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        employeeDAO.save(employee);
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);
        CalendarEvent calEvent = new CalendarEvent("EventName", "Description", timeSpan);
        Instructor admin = new Instructor("adminID", "AdminName", "admin@example.com", "password");
        Course course = new Course("CourseName", "COURSE01", admin);
        Event event = new Lecture("EventName", "EVENT01", course);
        ClassSession session = new ClassSession("SESSION123", "Session Name", calEvent, "location", event);
        session.addStaff(employee);
        sessionDAO.save(session);

        RemoveFromSessionInputData inputData = new RemoveFromSessionInputData("EMP123", "SESSION123");
        interactor.removeFromSession(inputData);

        assertTrue(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Successfully removed employee from session", myPresenterStub.getOutputData().getMessage());
    }
}
