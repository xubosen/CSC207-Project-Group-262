package tests.entity;

import entity.Course;
import entity.Instructor;
import entity.Tutorial;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TutorialTest {

    @Test
    public void testTutorialConstruction() {
        Instructor admin = new Instructor("adminID", "Admin", "admin@example.com", "password");
        Course course = new Course("CourseName", "CSE101", admin);
        Tutorial tutorial = new Tutorial("TutorialName", "TUT01", course);

        assertEquals("TutorialName", tutorial.getName());
        assertEquals("TUT01", tutorial.getEventID());
        assertEquals(course, tutorial.getCourse());
    }
}
