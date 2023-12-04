package tests.entity;

import entity.Course;
import entity.Instructor;
import entity.Lecture;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LectureTest {

    @Test
    public void testLectureConstruction() {
        Instructor admin = new Instructor("adminID", "Admin", "admin@example.com", "password");
        Course course = new Course("CourseName", "CSE101", admin);
        Lecture lecture = new Lecture("LectureName", "LECT01", course);

        assertEquals("LectureName", lecture.getName());
        assertEquals("LECT01", lecture.getEventID());
        assertEquals(course, lecture.getCourse());
    }
}