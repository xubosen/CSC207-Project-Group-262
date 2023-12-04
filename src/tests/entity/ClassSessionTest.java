package tests.entity;

import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ClassSessionTest {
    private ClassSession session;
    private CalendarEvent calEvent;
    private Event event;
    private Course course;
    private Instructor admin;

    @Before
    public void setUp() {
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);
        calEvent = new CalendarEvent("EventName", "Description", timeSpan);

        admin = new Instructor("adminID", "AdminName", "admin@example.com", "password");
        course = new Course("CourseName", "COURSE01", admin);
        event = new Lecture("EventName", "EVENT01", course);

        session = new ClassSession("sessionID", "SessionName", calEvent, "Location", event);

    }

    @Test
    public void testClassSessionConstruction() {
        assertEquals("sessionID", session.getSessionID());
        assertEquals("SessionName", session.getName());
        assertEquals(calEvent, session.toCalendarEvent());
        assertEquals("Location", session.getLocation());
        assertEquals(event, session.getEvent());
    }

    @Test
    public void testAddStaff() {
        TeachingAssistant employee = new TeachingAssistant("101", "John", "employee@example.com", "123");
        assertTrue(session.addStaff(employee));
        assertFalse(session.addStaff(employee)); // Adding the same employee again should return false
    }

    @Test
    public void testRemoveStaff() {
        TeachingAssistant employee = new TeachingAssistant("101", "John", "employee@example.com", "123");
        session.addStaff(employee);
        assertTrue(session.removeStaff(employee));
        assertFalse(session.removeStaff(employee)); // Removing the same employee again should return false
    }

    @Test
    public void testChangeLocation() {
        session.changeLocation("NewLocation");
        assertEquals("NewLocation", session.getLocation());
    }

    @Test
    public void testReschedule() {
        // Create a new calendar event for rescheduling
        LocalDateTime newStart = LocalDateTime.of(2023, 12, 2, 10, 0);
        LocalDateTime newEnd = LocalDateTime.of(2023, 12, 2, 11, 0);
        DateTimeSpan newTimeSpan = new DateTimeSpan(newStart, newEnd);
        CalendarEvent newCalEvent = new CalendarEvent("NewEventName", "NewDescription", newTimeSpan);

        // Assuming reschedule logic does not depend on external event conflicts
        assertTrue(session.reschedule(newCalEvent));

    }

    @Test
    public void testConflictsWith() {
        // Create a conflicting class session
        LocalDateTime startConflict = LocalDateTime.of(2023, 12, 1, 10, 30);
        LocalDateTime endConflict = LocalDateTime.of(2023, 12, 1, 11, 30);
        DateTimeSpan timeSpanConflict = new DateTimeSpan(startConflict, endConflict);
        CalendarEvent calEventConflict = new CalendarEvent("ConflictEvent", "Description", timeSpanConflict);
        ClassSession sessionConflict = new ClassSession("sessionIDConflict", "SessionConflict", calEventConflict, "Location", event);

        assertTrue(session.conflictsWith(sessionConflict));
    }

    @Test
    public void testListStaff() {
        TeachingAssistant employee = new TeachingAssistant("101", "John", "employee@example.com", "123");
        session.addStaff(employee);
        assertTrue(session.listStaff().contains(employee));
    }

    @Test
    public void testDelete() {
        TeachingAssistant employee = new TeachingAssistant("101", "John", "employee@example.com", "123");
        session.addStaff(employee);
        session.delete();

        assertNull(session.getEvent());
        assertFalse(session.containsStaff(employee));
        // Assuming Event class properly handles session deletion
    }

    @Test
    public void testGetSessionID() {
        assertEquals("sessionID", session.getSessionID());
    }

    @Test
    public void testGetName() {
        assertEquals("SessionName", session.getName());
    }

    @Test
    public void testToCalendarEvent() {
        assertEquals(calEvent, session.toCalendarEvent());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Location", session.getLocation());
    }

    @Test
    public void testGetEvent() {
        assertEquals(event, session.getEvent());
    }
}
