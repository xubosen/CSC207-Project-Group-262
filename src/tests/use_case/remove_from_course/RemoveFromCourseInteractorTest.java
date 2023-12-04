package tests.use_case.remove_from_course;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Instructor;
import org.junit.Before;
import org.junit.Test;
import use_case.remove_from_course.RemoveFromCourseInputData;
import use_case.remove_from_course.RemoveFromCourseInteractor;

import static org.junit.Assert.*;

public class RemoveFromCourseInteractorTest {
    private RemoveFromCourseInteractor interactor;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemoryCourseDataAccessObject courseDAO;
    private RemoveFromCourseOutputBoundaryStub myPresenterStub;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        courseDAO = new InMemoryCourseDataAccessObject();
        myPresenterStub = new RemoveFromCourseOutputBoundaryStub();
        interactor = new RemoveFromCourseInteractor(myPresenterStub, employeeDAO, courseDAO);

        // Setup initial data in DAOs
        Instructor admin = new Instructor("adminID", "Admin", "admin@example.com", "adminPass");
        employeeDAO.save(admin);

        Course course = new Course("Software Design", "CSC207", admin);
        courseDAO.save(course);
    }

    @Test
    public void testRemoveFromCourseEmployeeDoesNotExist() {
        RemoveFromCourseInputData inputData = new RemoveFromCourseInputData("nonExistentEmployeeID", "CSC207");
        interactor.removeFromCourse(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee does not exist", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromCourseCourseDoesNotExist() {
        RemoveFromCourseInputData inputData = new RemoveFromCourseInputData("adminID", "nonExistentCourseCode");
        interactor.removeFromCourse(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Course does not exist", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromCourseEmployeeNotInCourse() {
        Instructor instructor = new Instructor("instructorID", "New Instructor", "instructor@example.com", "pass");
        employeeDAO.save(instructor);
        RemoveFromCourseInputData inputData = new RemoveFromCourseInputData(instructor.getUID(), "CSC207");
        interactor.removeFromCourse(inputData);

        assertFalse(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee is not in the course", myPresenterStub.getOutputData().getMessage());
    }

    @Test
    public void testRemoveFromCourseSuccess() {
        Instructor instructor = new Instructor("instructorID", "New Instructor", "instructor@example.com", "pass");
        employeeDAO.save(instructor);
        Course course = courseDAO.getByID("CSC207");
        course.addStaff(instructor);

        RemoveFromCourseInputData inputData = new RemoveFromCourseInputData(instructor.getUID(), "CSC207");
        interactor.removeFromCourse(inputData);

        assertTrue(myPresenterStub.getOutputData().isSuccessful());
        assertEquals("Employee removed from the course", myPresenterStub.getOutputData().getMessage());
    }
}

