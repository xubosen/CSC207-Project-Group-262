package use_case;

import entity.Employee;
import entity.Course;
import data_access.InMemoryCourseDataAccessObject;
import data_access.InMemoryEmployeeDataAccessObject;

public class EnrollInteractor implements EnrollInputBoundary{

        private EnrollOutputBoundary enrollPresenter;
        private InMemoryEmployeeDataAccessObject employeesDAO;
        private InMemoryCourseDataAccessObject coursesDAO;


        public EnrollInteractor(EnrollOutputBoundary enrollPresenter, InMemoryEmployeeDataAccessObject employeesDAO,
                                InMemoryCourseDataAccessObject coursesDAO) {
            this.enrollPresenter = enrollPresenter;
            this.employeesDAO = employeesDAO;
            this.coursesDAO = coursesDAO;
        }

        public void enroll(EnrollInputData inputData) {
            EnrollOutputData output;

            // If the employee does not exist return false and a corresponding message in output data
            if (! doesEmployeeExist(inputData)) {
                output = new EnrollOutputData(false, "Employee does not exist");

            // If the course does not exist return false and a corresponding message in output data
            } else if (! doesCourseExist(inputData)) {
                output = new EnrollOutputData(false, "Course does not exist");

            // If the employee exists and the course exists, try to add the employee to the course
            } else {
                Employee curEmployee = getEmployeeFromInputData(inputData);
                Course curCourse = getCourseFromInputData(inputData);

                // Try to add the employee to the course
                boolean enrollSuccessful = curCourse.addStaff(curEmployee);

                // If the employee was added successfully, return true in output data
                if (enrollSuccessful) {
                    output = new EnrollOutputData(true, "Enroll successful!");

                // If the employee was unsuccessful, it means that the employee is already enrolled in the course
                } else {
                    output = new EnrollOutputData(false,
                            "This user is already enrolled in the course");
                }
            }

            // Call the presenter to present the output data
            enrollPresenter.prepareView(output);
        }

        private boolean doesEmployeeExist(EnrollInputData inputData) {
            return employeesDAO.existsByID(inputData.getUserID());
        }

        private Employee getEmployeeFromInputData(EnrollInputData inputData) {
            return employeesDAO.getByID(inputData.getUserID());
        }

        private boolean doesCourseExist(EnrollInputData inputData) {
            return coursesDAO.existsByID(inputData.getCourseCode());
        }

        private Course getCourseFromInputData(EnrollInputData inputData) {
            return coursesDAO.getByID(inputData.getCourseCode());
        }

}
