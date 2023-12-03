package entityTests;

import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventTest {

    private Event event;
    private Employee employee;
    private ClassSession session1, session2;
    private Course course;

    @Before
    public void setUp() {
        course = new Course("Course Name", "COURSE123", new Instructor("ADMIN123", "Admin", "admin@example.com", "password"));
        employee = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        event = new Tutorial("Event Name", "EVENT123", course); // Initialize event first

        // Set up two different class sessions
        LocalDateTime start1 = LocalDateTime.of(2023, 5, 15, 10, 30);
        LocalDateTime end1 = LocalDateTime.of(2023, 5, 15, 12, 45);
        session1 = new ClassSession("SESSION123", "Session Name 1", new CalendarEvent("Tuesday", "Description", new DateTimeSpan(start1, end1)), "Room 101", event);

        LocalDateTime start2 = LocalDateTime.of(2023, 5, 16, 11, 30);
        LocalDateTime end2 = LocalDateTime.of(2023, 5, 16, 13, 45);
        session2 = new ClassSession("SESSION124", "Session Name 2", new CalendarEvent("Wednesday", "Description", new DateTimeSpan(start2, end2)), "Room 102", event);
    }

    @Test
    public void testAddStaff() {
        assertTrue("Staff should be added successfully", event.addStaff(employee));
        assertFalse("Same staff should not be added again", event.addStaff(employee));
    }

    @Test
    public void testRemoveStaff() {
        event.addStaff(employee);
        assertTrue("Staff should be removed successfully", event.removeStaff(employee));
        assertFalse("Non-existent staff should not be removed", event.removeStaff(employee));
    }

    @Test
    public void testAddSession() {
        assertTrue("Session should be added successfully", event.addSession(session1));
        assertFalse("Same session should not be added again", event.addSession(session1));
    }

    @Test
    public void testRemoveSession() {
        event.addSession(session2);
        assertTrue("Session should be removed successfully", event.removeSession(session2));
        assertFalse("Non-existent session should not be removed", event.removeSession(session2));
    }

    @Test
    public void testContainsSession() {
        event.addSession(session1);
        assertTrue("Event should contain the session", event.containsSession(session1));
        event.removeSession(session1);
        assertFalse("Event should not contain the removed session", event.containsSession(session1));
    }

    @Test
    public void testListStaff() {
        event.addStaff(employee);
        assertEquals("List should contain one staff member", 1, event.listStaff().size());
        assertTrue("List should contain the added staff member", event.listStaff().containsKey(employee.getUserID()));
    }

    @Test
    public void testGetName() {
        assertEquals("Event Name should be returned", "Event Name", event.getName());
    }

    @Test
    public void testGetEventID() {
        assertEquals("Event ID should be returned", "EVENT123", event.getEventID());
    }

    @Test
    public void testGetCourse() {
        assertSame("Course object should be returned", course, event.getCourse());
    }

    @Test
    public void testConflictsWith() {
        event.addSession(session1);
        assertFalse("Sessions should not conflict", event.conflictsWith(session2));

        // Modify session2 so it overlaps with session1
        LocalDateTime overlappingStart = LocalDateTime.of(2023, 5, 15, 11, 30);
        LocalDateTime overlappingEnd = LocalDateTime.of(2023, 5, 15, 13, 45);
        ClassSession overlappingSession = new ClassSession("SESSION125", "Session Name 3", new CalendarEvent("Tuesday", "Description", new DateTimeSpan(overlappingStart, overlappingEnd)), "Room 103", event);

        assertTrue("Sessions should conflict", event.conflictsWith(overlappingSession));
    }

}