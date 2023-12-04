package tests.use_case.get_sessions;

import entity.*;
import org.junit.Before;
import org.junit.Test;
import use_case.get_sessions.*;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class GetSessionsInteractorTest {

    private GetSessionsInteractor interactor;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private GetSessionsOutputSpy outputSpy;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        outputSpy = new GetSessionsOutputSpy();
        interactor = new GetSessionsInteractor(outputSpy, employeeDAO);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(startTime, endTime);
        Course course = new Course("Course Name", "COURSE123", new Instructor("ADMIN123", "Admin", "admin@example.com", "password"));
        Employee employee = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        Event event = new Tutorial("Event Name", "EVENT123", course);

        employee = new Employee("employee123", "John Doe", "john@example.com", "password");
        employee.addSession(new ClassSession("Session 1", "John Jung", new CalendarEvent("new_event", "description", dateTimeSpan), "Room1", event));
        employeeDAO.addEmployee(employee);
    }

    @Test
    public void testGetSessionsForEmployee() {
        GetSessionsInputData inputData = new GetSessionsInputData("employee123");
        interactor.getSessions(inputData);

        GetSessionsOutputData outputData = outputSpy.getOutputData();
        assertNotNull(outputData);
        assertTrue(outputData.getSessions().contains("Session 1"));
        assertEquals(1, outputData.getSessions().size());
    }

    private class GetSessionsOutputSpy implements GetSessionsOutputBoundary {
        private GetSessionsOutputData outputData;

        @Override
        public void present(GetSessionsOutputData outputData) {
            this.outputData = outputData;
        }

        public GetSessionsOutputData getOutputData() {
            return outputData;
        }
    }
}
