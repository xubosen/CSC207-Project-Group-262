package use_case.create_course;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Employee;
import entity.Instructor;

public class CreateCourseInteractor implements CreateCourseInputBoundary {
    // TODO: Might be completed, need to test
    private CreateCourseOutputBoundary createCoursePresenter;
    private InMemoryCourseDataAccessObject coursesDAO;
    private InMemoryEmployeeDataAccessObject employeesDAO;

    /**
     * Initializes the course creator interactor.
     * @param createCoursePresenter Create course output boundary.
     * @param employeesDAO The in memory DAO for Employees
     * @param coursesDAO The in memory DAO for courses
     */
    public CreateCourseInteractor(CreateCourseOutputBoundary createCoursePresenter, InMemoryEmployeeDataAccessObject employeesDAO,
                            InMemoryCourseDataAccessObject coursesDAO) {
        this.createCoursePresenter = createCoursePresenter;
        this.employeesDAO = employeesDAO;
        this.coursesDAO = coursesDAO;
    }

    /**
     * Tries to create the course, creates output data to input into our presenter
     * @param inputData
     */
    public void createCourse(CreateCourseInputData inputData) {
        CreateCourseOutputData output;

        // If the Course exists then return false and the corresponding message in output data
        if (doesCourseExist(inputData)) {
            output = new CreateCourseOutputData(false, "Course already exists.");

        // Make sure the course creator is type instructor. This might not be necessary because the view won't
        // show create course for a TA
        } else if (!isInstructor(inputData)) {
            output = new CreateCourseOutputData(false, "User is not an Instructor");
        } else {
            // Try to create the new course from the input
            // TODO: Make sure the Employee is Instructor.
            Instructor admin = (Instructor) getEmployeeFromInputData(inputData);
            coursesDAO.addCourse(new Course(inputData.getCourseName(), inputData.getCourseCode(), admin));

            output = new CreateCourseOutputData(true, "Course created successfully");
        }

        // Call the presenter to present the output data
        createCoursePresenter.prepareView(output);
    }


    private boolean isInstructor(CreateCourseInputData inputData) {

        return employeesDAO.getByID(inputData.getAdminID()).getClass().equals(Instructor.class);
    }

    private Employee getEmployeeFromInputData(CreateCourseInputData inputData) {
        return employeesDAO.getByID(inputData.getAdminID());
    }

    private boolean doesCourseExist(CreateCourseInputData inputData) {
        return coursesDAO.existsByID(inputData.getCourseCode());
    }
}
