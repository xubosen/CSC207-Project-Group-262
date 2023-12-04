package tests.use_case.get_courses;

import org.junit.Before;
import org.junit.Test;
import use_case.get_courses.*;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Instructor;

import static org.junit.Assert.*;

public class GetCoursesInteractorTest {

    private GetCoursesInteractor interactor;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private GetCoursesOutputSpy outputSpy;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        outputSpy = new GetCoursesOutputSpy();
        interactor = new GetCoursesInteractor(outputSpy, employeeDAO);

        // Set up a simple instructor with one course
        Instructor instructor = new Instructor("EMP123", "John Doe", "john@example.com", "password");
        Course course = new Course("Course Name", "COURSE123", instructor);
        instructor.addCourse(course);
        employeeDAO.addEmployee(instructor);
    }

    @Test
    public void testGetCoursesForEmployee() {
        GetCoursesInputData inputData = new GetCoursesInputData("EMP123");
        interactor.getCourses(inputData);

        // Verifying that the presenter's present method was called
        assertNotNull(outputSpy.getOutputData());
        assertTrue(outputSpy.getOutputData().getCourses().contains("COURSE123"));
    }

    private class GetCoursesOutputSpy implements GetCoursesOutputBoundary {
        private GetCoursesOutputData outputData;

        @Override
        public void present(GetCoursesOutputData outputData) {
            this.outputData = outputData;
        }

        public GetCoursesOutputData getOutputData() {
            return outputData;
        }
    }
}
