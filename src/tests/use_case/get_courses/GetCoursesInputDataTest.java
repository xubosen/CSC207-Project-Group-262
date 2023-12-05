package tests.use_case.get_courses;

import org.junit.Before;
import org.junit.Test;
import use_case.get_courses.GetCoursesInputData;

import static org.junit.Assert.assertEquals;

public class GetCoursesInputDataTest {

    private GetCoursesInputData inputData;

    @Before
    public void setUp() {
        inputData = new GetCoursesInputData("user123");
    }

    @Test
    public void testGetUserID() {
        assertEquals("user123", inputData.getUserID());
    }
}
