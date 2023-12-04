package tests.use_case.invite_to_course_test;

// import entities
import entity.Instructor;
import entity.TeachingAssistant;

// import use cases
import entity.Course;
import entity.Instructor;
import entity.TeachingAssistant;
import use_case.enroll.EnrollOutputBoundary;
import use_case.enroll.EnrollInputData;
import use_case.enroll.EnrollOutputData;
import use_case.enroll.EnrollInteractor;

// import data access objects
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;

// junit imports
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvToCourseInteractorTest {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private Instructor admin;
    private Instructor instructor1;
    private TeachingAssistant ta1;
    private TeachingAssistant ta2;

    private Course course1;

    @Before
    public void setUp() {
        // Create some employees
        employeeDAO.addEmployee(new Instructor("admin", "test admin", "admin@test.com", "1"));
        employeeDAO.addEmployee(new Instructor("instructor1", "instructor 1", "ins1@test.com", "1"));
        employeeDAO.addEmployee(new TeachingAssistant("ta1", "TA 1", "t1@test.com", "1"));
        employeeDAO.addEmployee(new TeachingAssistant("ta2", "TA 2", "t2@test.com", "1"));

        admin = (Instructor) employeeDAO.getByID("admin");
        instructor1 = (Instructor) employeeDAO.getByID("instructor1");
        ta1 = (TeachingAssistant) employeeDAO.getByID("ta1");
        ta2 = (TeachingAssistant) employeeDAO.getByID("ta2");

        // Create some courses & add some employees to them
        courseDAO.addCourse(new Course("Test Course", "TEST", admin));

        course1 = courseDAO.getByID("TEST");

        course1.addStaff(instructor1);
        course1.addStaff(ta1);
    }

    @Test
    public void testInvToCourseWrongInviteeID() {
        EnrollInputData inputData = new EnrollInputData("Non-existent user", "Wrong ID",
                course1.getCourseCode());
        InvToCourseOutputSpy mockPresenter = new InvToCourseOutputSpy();
        EnrollInteractor enrollInteractor = new EnrollInteractor(mockPresenter, employeeDAO, courseDAO);
        enrollInteractor.enroll(inputData);

        EnrollOutputData outputData = mockPresenter.getOutputData();

        assertFalse(outputData.isSuccessful());
        assertEquals("Invitee does not exist", outputData.getMessage());
    }

    @Test
    public void testInvToCourseWrongCourseID() {
        EnrollInputData inputData = new EnrollInputData(admin.getUID(), ta1.getUID(),
                "Wrong Course Code");
        InvToCourseOutputSpy mockPresenter = new InvToCourseOutputSpy();
        EnrollInteractor enrollInteractor = new EnrollInteractor(mockPresenter, employeeDAO, courseDAO);
        enrollInteractor.enroll(inputData);

        EnrollOutputData outputData = mockPresenter.getOutputData();

        assertFalse(outputData.isSuccessful());
        assertEquals("Course does not exist", outputData.getMessage());
    }

    @Test
    public void testInvToCourseInvitorNotAdmin() {
        EnrollInputData inputData = new EnrollInputData(instructor1.getUID(), ta1.getUID(),
                course1.getCourseCode());
        InvToCourseOutputSpy mockPresenter = new InvToCourseOutputSpy();
        EnrollInteractor enrollInteractor = new EnrollInteractor(mockPresenter, employeeDAO, courseDAO);
        enrollInteractor.enroll(inputData);

        EnrollOutputData outputData = mockPresenter.getOutputData();

        assertFalse(outputData.isSuccessful());
        assertEquals("Access denied. You are not the admin of this course", outputData.getMessage());
    }

    @Test
    public void testInvToCourseInviteeAlreadyEnrolled() {
        EnrollInputData inputData = new EnrollInputData(admin.getUID(), instructor1.getUID(),
                course1.getCourseCode());
        InvToCourseOutputSpy mockPresenter = new InvToCourseOutputSpy();
        EnrollInteractor enrollInteractor = new EnrollInteractor(mockPresenter, employeeDAO, courseDAO);
        enrollInteractor.enroll(inputData);

        EnrollOutputData outputData = mockPresenter.getOutputData();

        assertFalse(outputData.isSuccessful());
        assertEquals("This user is already enrolled in the course", outputData.getMessage());
    }


    @Test
    public void testInvToCourseSuccess() {
        EnrollInputData inputData = new EnrollInputData(admin.getUID(), ta2.getUID(),
                course1.getCourseCode());
        InvToCourseOutputSpy mockPresenter = new InvToCourseOutputSpy();
        EnrollInteractor enrollInteractor = new EnrollInteractor(mockPresenter, employeeDAO, courseDAO);
        enrollInteractor.enroll(inputData);

        EnrollOutputData outputData = mockPresenter.getOutputData();

        assertTrue(outputData.isSuccessful());
        assertEquals("Enroll successful!", outputData.getMessage());
    }


    private class InvToCourseOutputSpy implements EnrollOutputBoundary {
        private EnrollOutputData outputData = null;

        public InvToCourseOutputSpy() {
        }

        @Override
        public void prepareView(EnrollOutputData outputData) {
            this.outputData = outputData;
        }

        public EnrollOutputData getOutputData() {
            return outputData;
        }
    }

}