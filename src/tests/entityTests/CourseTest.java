package entityTests;
import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class CourseTest {
    private Course course;
    private Instructor admin;
    private TeachingAssistant staffMember;
    private Event event;
    private ClassSession classSession;

    @Before
    public void setUp() {
        admin = new Instructor("adminID", "Admin", "admin@example.com", "password");
        course = new Course("CourseName", "CSE101", admin);
        staffMember = new TeachingAssistant("staffID", "Staff", "staff@example.com", "pass");
        event = new Lecture("LectureEvent", "EVENT01", course);

        // Create a class session
        LocalDateTime start = LocalDateTime.of(2023, 11, 17, 8, 0);
        LocalDateTime end = LocalDateTime.of(2023, 11, 17, 9, 0);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(start, end);
        CalendarEvent calendarEvent = new CalendarEvent("EVENT02-Session1", "A Tutorial", dateTimeSpan);
        classSession = new ClassSession("ClassSessionEvent", "EVENT02-Session1",
                calendarEvent,"BA1000", event);
    }

    @Test
    public void testCourseConstruction() {
        assertEquals("Course name should match constructor input", "CourseName", course.getName());
        assertEquals("Course code should match constructor input", "CSE101", course.getCourseCode());
        assertEquals("Admin should match constructor input", admin, course.getAdmin());
    }

    @Test
    public void testContainsStaff() {
        assertFalse("Course should not contain staff member", course.containsStaff(staffMember));
        assertTrue("Course should contain admin", course.containsStaff(admin));
    }

    @Test
    public void testAddStaff() {
        // Test addStaff
        assertTrue("Adding a new staff member should return true", course.addStaff(staffMember));
        assertFalse("Adding a null staff member should return false", course.addStaff(null));
        assertFalse("Adding an already existing staff member should return false", course.addStaff(staffMember));

        assertTrue("The staff member should be contained in the course", course.containsStaff(staffMember));
        assertTrue("The course should be contained in the employee", staffMember.containsCourse(course));
    }

    @Test
    public void testRemoveStaff() {
        course.addStaff(staffMember); // Add the staff member to the course.
        assertTrue("Removing an existing staff member should return true", course.removeStaff(staffMember));
        Employee nonExistentStaffMember = new Instructor("nonExistentStaffMember", "Staff", "A", "B");
        assertFalse("Removing a non-contained staff member should return false",
                course.removeStaff(nonExistentStaffMember));

        assertFalse("The staff member should not be contained in the course", course.containsStaff(staffMember));
        assertFalse("The course should not be contained in the employee", staffMember.containsCourse(course));
        assertFalse("The session should not contain the staff", classSession.containsStaff(staffMember));
        assertFalse("The staff should not contain the session", staffMember.containsSession(classSession));
    }

    @Test
    public void testAddEvent() {
        course.removeEvent(event);
        assertTrue("Adding a new event should return true", course.addEvent(event));
        assertFalse("Adding a null event should return false", course.addEvent(null));
        assertFalse("Adding an already existing event should return false", course.addEvent(event));
    }

    @Test
    public void testRemoveEvent() {
        course.addEvent(event);
        event.addSession(classSession);
        assertTrue("Removing an existing event should return true", course.removeEvent(event));
        assertFalse("Removing a non-contained event should return false", course.removeEvent(event));

        assertFalse("The event should not be contained in the course", course.containsEvent(event));
        assertFalse("The course should not be contained in the event", event.getCourse() == course);
        assertTrue("The event should have null for its course attribute", event.getCourse() == null);
        assertTrue("The session should have null for its event attribute", classSession.getEvent() == null);
        assertFalse("The employee should not contain the session", staffMember.containsSession(classSession));
    }

    @Test
    public void testListStaff() {
        course.addStaff(staffMember);
        assertTrue("Listed staff should include added staff member", course.getStaff().containsKey(staffMember.getUserID()));
    }

    @Test
    public void testListEvents() {
        course.addEvent(event);
        assertTrue("Listed events should include added event", course.getEvents().containsKey(event.getEventID()));
    }
}
