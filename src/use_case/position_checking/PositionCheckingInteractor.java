package use_case.position_checking;

import entity.Course;
import entity.Employee;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;

public class PositionCheckingInteractor implements PositionCheckingInputBoundary{
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemoryCourseDataAccessObject courseDAO;
    private PositionCheckingOutputBoundary presenter;

    public PositionCheckingInteractor(PositionCheckingOutputBoundary presenter, InMemoryEmployeeDataAccessObject employeeDAO, InMemoryCourseDataAccessObject courseDAO) {
        this.employeeDAO = employeeDAO;
        this.courseDAO = courseDAO;
        this.presenter = presenter;
    }

    public void checkIsAdmin(PositionCheckingInputData inputData) {
        Course curCourse = courseDAO.getByID(inputData.getCourseCode());
        Employee curEmployee = employeeDAO.getByID(inputData.getUserID());
        boolean isAdmin = curCourse.getAdmin().getUID().equals(curEmployee.getUID());
        presenter.presentIsAdmin(isAdmin);
    }
}
