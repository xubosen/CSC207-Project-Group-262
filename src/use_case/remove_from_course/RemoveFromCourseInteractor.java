package use_case.remove_from_course;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Employee;

public class RemoveFromCourseInteractor implements RemoveFromCourseInputBoundary{
    private RemoveFromCourseOutputBoundary myPresenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemoryCourseDataAccessObject courseDAO;

    public RemoveFromCourseInteractor(RemoveFromCourseOutputBoundary myPresenter, InMemoryEmployeeDataAccessObject employeeDAO, InMemoryCourseDataAccessObject courseDAO) {
        this.myPresenter = myPresenter;
        this.employeeDAO = employeeDAO;
        this.courseDAO = courseDAO;
    }

    @Override
    public void removeFromCourse(RemoveFromCourseInputData inputData) {
        // Check if the employee and course exist
        String employeeID = inputData.getEmployeeID();
        if (!employeeDAO.existsByID(employeeID)) {
            RemoveFromCourseOutputData outputData = new RemoveFromCourseOutputData(false, "Employee does not exist");
            myPresenter.prepareView(outputData);
        } else if (!courseDAO.existsByID(inputData.getCourseCode())) {
            RemoveFromCourseOutputData outputData = new RemoveFromCourseOutputData(false, "Course does not exist");
            myPresenter.prepareView(outputData);
        } else {
            // Check if the employee is in the course
            Employee curEmployee = employeeDAO.getByID(employeeID);
            Course curCourse = courseDAO.getByID(inputData.getCourseCode());

            if (!curCourse.containsStaff(curEmployee)) {
                RemoveFromCourseOutputData outputData = new RemoveFromCourseOutputData(false, "Employee is not in the course");
                myPresenter.prepareView(outputData);
            } else {
                // Remove the employee from the course
                curCourse.removeStaff(curEmployee);

                RemoveFromCourseOutputData outputData = new RemoveFromCourseOutputData(true, "Employee removed from the course");
                myPresenter.prepareView(outputData);
            }
        }
    }
}
