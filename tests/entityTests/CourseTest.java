package entityTests;
import entity.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class CourseTest {
    private Course course;
    private Employee admin;
    private Employee staffMember;
    private Event event;

    @Before
    public void setUp() {
        admin = new Instructor("adminID", "Admin", "admin@example.com", "password");
        course = new Course("CourseName", "CSE101", admin);

        staffMember = new Instructor("staffID", "Staff", "staff@example.com", "pass");
        event = new Lecture("LectureEvent", "EVENT01", course);
    }

    @Test
    public void testCourseConstruction() {
        assertEquals("Course name should match constructor input", "CourseName", course.getName());
        assertEquals("Course code should match constructor input", "CSE101", course.getCourseCode());
        assertEquals("Admin should match constructor input", admin, course.getAdmin());
    }

    @Test
    public void testAddAndRemoveStaff() {
        // Test addStaff
        assertTrue("Adding a new staff member should return true", course.addStaff(staffMember));
        assertFalse("Adding a null staff member should return false", course.addStaff(null));
        assertFalse("Adding an already existing staff member should return false", course.addStaff(staffMember));

        // Test removeStaff
        assertTrue("Removing an existing staff member should return true", course.removeStaff(staffMember));
        Employee nonExistentStaff = new Instructor("nonExistentID", "NonExistent", "123", "123");
        assertFalse("Removing a non-existing staff member should return false", course.removeStaff(nonExistentStaff));
    }

    @Test
    public void testAddAndRemoveEvent() {
        // Test addEvent
        assertTrue("Adding a new event should return true", course.addEvent(event));
        assertFalse("Adding a null event should return false", course.addEvent(null));
        assertFalse("Adding an already existing event should return false", course.addEvent(event));

        // Test removeEvent
        assertTrue("Removing an existing event should return true", course.removeEvent(event));
        Event nonExistentEvent = new Lecture("nonExistentEvent", "EVENT02", course);
        assertFalse("Removing a non-existing event should return false", course.removeEvent(nonExistentEvent));
    }

    @Test
    public void testListStaff() {
        course.addStaff(staffMember);
        assertTrue("Listed staff should include added staff member", course.listStaff().containsKey(staffMember.getUID()));
    }

    @Test
    public void testListEvents() {
        course.addEvent(event);
        assertTrue("Listed events should include added event", course.listEvents().containsKey(event.getEventID()));
    }
}
