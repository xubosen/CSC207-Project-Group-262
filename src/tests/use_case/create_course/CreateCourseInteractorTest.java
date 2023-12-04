package tests.use_case.create_course;

import org.junit.Before;
import org.junit.Test;
import use_case.create_course.CreateCourseInputData;
import use_case.create_course.CreateCourseInteractor;
import use_case.create_course.CreateCourseOutputBoundary;
import use_case.create_course.CreateCourseOutputData;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Instructor;

import static org.junit.Assert.*;

public class CreateCourseInteractorTest {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private Instructor admin;

    @Before
    public void setUp() {
        admin = new Instructor("admin123", "Admin Name", "admin@example.com", "password");
        employeeDAO.addEmployee(admin);
    }

    @Test
    public void testCreateCourseSuccess() {
        CreateCourseInputData inputData = new CreateCourseInputData("CS101", "Introduction to Computer Science", admin.getUID());
        CreateCourseOutputSpy mockPresenter = new CreateCourseOutputSpy();
        CreateCourseInteractor createCourseInteractor = new CreateCourseInteractor(mockPresenter, employeeDAO, courseDAO);
        createCourseInteractor.createCourse(inputData);

        CreateCourseOutputData outputData = mockPresenter.getOutputData();

        assertTrue(outputData.isSuccessful());
        assertEquals("Course created successfully", outputData.getMessage());
    }

    // Additional tests for failure scenarios, such as invalid admin ID, duplicate course code, etc.

    private class CreateCourseOutputSpy implements CreateCourseOutputBoundary {
        private CreateCourseOutputData outputData;

        @Override
        public void prepareView(CreateCourseOutputData outputData) {
            this.outputData = outputData;
        }

        public CreateCourseOutputData getOutputData() {
            return outputData;
        }
    }
}
