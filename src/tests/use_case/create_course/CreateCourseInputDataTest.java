package tests.use_case.create_course;

import org.junit.Before;
import org.junit.Test;
import use_case.create_course.CreateCourseInputData;

import static org.junit.Assert.assertEquals;

public class CreateCourseInputDataTest {

    private CreateCourseInputData inputData;

    @Before
    public void setUp() {
        inputData = new CreateCourseInputData("CS101", "Introduction to Computer Science", "admin123");
    }

    @Test
    public void testGetCourseCode() {
        assertEquals("CS101", inputData.getCourseCode());
    }

    @Test
    public void testGetCourseName() {
        assertEquals("Introduction to Computer Science", inputData.getCourseName());
    }

    @Test
    public void testGetAdminID() {
        assertEquals("admin123", inputData.getAdminID());
    }
}
