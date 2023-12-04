package tests.use_case.create_course;

import org.junit.Before;
import org.junit.Test;
import use_case.create_course.CreateCourseOutputData;

import static org.junit.Assert.*;

public class CreateCourseOutputDataTest {

    private CreateCourseOutputData outputData;

    @Before
    public void setUp() {
        outputData = new CreateCourseOutputData(true, "Course creation successful");
    }

    @Test
    public void testIsSuccessful() {
        assertTrue(outputData.isSuccessful());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Course creation successful", outputData.getMessage());
    }
}
