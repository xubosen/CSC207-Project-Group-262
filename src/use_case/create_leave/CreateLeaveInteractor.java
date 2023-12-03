package use_case.create_leave;

import data_access.in_memory_dao.InMemoryLeaveDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.*;
import use_case.create_leave.CreateLeaveOutputBoundary;
import use_case.create_leave.CreateLeaveOutputData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateLeaveInteractor implements CreateLeaveInputBoundary {
    private CreateLeaveOutputBoundary createLeavePresenter;
    private InMemoryLeaveDataAccessObject leavesDAO;
    private InMemoryEmployeeDataAccessObject employeesDAO;

    /**
     * Initializes the leave creator interactor.
     * @param createLeavePresenter Create Leave output boundary.
     * @param employeesDAO The in memory DAO for Employees.
     * @param leavesDAO The In memory DAO for leaves.
     */
    public CreateLeaveInteractor(CreateLeaveOutputBoundary createLeavePresenter,
                                 InMemoryEmployeeDataAccessObject employeesDAO,
                                 InMemoryLeaveDataAccessObject leavesDAO) {
        this.createLeavePresenter = createLeavePresenter;
        this.employeesDAO = employeesDAO;
        this.leavesDAO = leavesDAO;
    }

    /**
     * Tries to create the leave request, creates output data to input into our presenter.
     *
     * @param inputData The input data for leave creation.
     */
    public void createLeave(CreateLeaveInputData inputData) {

            // Initialize the output data package
        CreateLeaveOutputData output;

        // If the leave request already exists return false and the corresponding message in output data
        if (doesLeaveExist(inputData)) {
            output = new CreateLeaveOutputData(false, "Leave request already exists.");

            // If the employee does not exist
        } else if (!doesEmployeeExist(inputData)) {
            output = new CreateLeaveOutputData(false, "Employee does not exist.");

        } else {

            LocalDate startDate = LocalDate.parse(inputData.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(inputData.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

            Employee requester = employeesDAO.getByID(inputData.getRequesterID());
            Leave newLeave = new Leave(
                    inputData.getLeaveName(),
                    inputData.getLeaveID(),
                    startDate,
                    endDate
            );
            leavesDAO.addLeave(newLeave);
            output = new CreateLeaveOutputData(true, "Leave request created successfully");
        }

        // Call the presenter to present the output data
        createLeavePresenter.prepareView(output);
    }

    private boolean doesLeaveExist(CreateLeaveInputData inputData) {
        return leavesDAO.existsByID(inputData.getLeaveID());
    }

    private boolean doesEmployeeExist(CreateLeaveInputData inputData) {
        return employeesDAO.existsByID(inputData.getRequesterID());
    }
}
