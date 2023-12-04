package tests.use_case.get_courses;

import org.junit.Before;
import org.junit.Test;
import use_case.get_courses.GetCoursesOutputData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCoursesOutputDataTest {

    private GetCoursesOutputData outputData;
    private ArrayList<String> courseList;

    @Before
    public void setUp() {
        courseList = new ArrayList<>(Arrays.asList("Course1", "Course2", "Course3"));
        outputData = new GetCoursesOutputData(courseList);
    }

    @Test
    public void testGetCourses() {
        assertEquals(courseList, outputData.getCourses());
        assertTrue(outputData.getCourses().contains("Course1"));
        assertTrue(outputData.getCourses().contains("Course2"));
        assertTrue(outputData.getCourses().contains("Course3"));
    }
}
