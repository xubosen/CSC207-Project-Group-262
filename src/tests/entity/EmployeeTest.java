package tests.entity;

import entity.*;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EmployeeTest {
    @Test
    public void testInstructorConstructionAndGetters() {
        Instructor instructor = new Instructor("emp01", "John Doe", "john@example.com", "password123");

        assertEquals("emp01", instructor.getUID());
        assertEquals("John Doe", instructor.getName());
        assertEquals("john@example.com", instructor.getEmail());
        assertEquals("password123", instructor.getPassword());
    }

    @Test
    public void testAddAndRemoveSession() {
        Instructor employee = new Instructor("emp01", "John Doe", "john@example.com", "password123");
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);
        CalendarEvent calEvent = new CalendarEvent("EventName", "Description", timeSpan);
        Instructor admin = new Instructor("adminID", "Admin", "admin@example.com", "password");
        Course course = new Course("CourseName", "CSE101", admin);
        ClassSession session = new ClassSession("sess01", "Session Name", calEvent, "Room 100", new Lecture("EventName", "EVENT01", course));

        assertTrue(employee.addSession(session));
        assertTrue(employee.containsSession(session));
        assertTrue(employee.removeSession(session));
        assertFalse(employee.containsSession(session));
    }

    @Test
    public void testAddAndRemoveCourse() {
        TeachingAssistant employee = new TeachingAssistant("emp01", "John Doe", "john@example.com", "password123");
        Instructor admin = new Instructor("adminID", "Admin", "admin@example.com", "password");
        Course course = new Course("CourseName", "CSE101", admin);

        assertTrue(employee.addCourse(course));
        assertTrue(employee.containsCourse(course));
        assertTrue(employee.removeCourse(course));
        assertFalse(employee.containsCourse(course));
    }
}
