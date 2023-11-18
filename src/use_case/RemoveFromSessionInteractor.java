package use_case;

import data_access.InMemoryEmployeeDataAccessObject;
import data_access.InMemorySessionDataAccessObject;
import entity.ClassSession;
import entity.Employee;

public class RemoveFromSessionInteractor implements RemoveFromSessionInputBoundary{
    private RemoveFromSessionOutputBoundary myPresenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemorySessionDataAccessObject sessionDAO;

    public RemoveFromSessionInteractor(RemoveFromSessionOutputBoundary myPresenter,
                                       InMemoryEmployeeDataAccessObject employeeDAO,
                                       InMemorySessionDataAccessObject sessionDAO) {
        this.myPresenter = myPresenter;
        this.employeeDAO = employeeDAO;
        this.sessionDAO = sessionDAO;
    }

    public void removeFromSession(RemoveFromSessionInputData inputData) {
        // Check if the employee and session exist
        String employeeId = inputData.getEmployeeId();
        if (!employeeDAO.existsByID(employeeId)) {
            RemoveFromSessionOutputData outputData = new RemoveFromSessionOutputData(false,
                    "Employee does not exist");
            myPresenter.prepareView(outputData);
        } else if (!sessionDAO.existsByID(inputData.getSessionId())) {
            RemoveFromSessionOutputData outputData = new RemoveFromSessionOutputData(false,
                    "Session does not exist");
            myPresenter.prepareView(outputData);
        }

        // Check if the employee is in the session
        Employee curEmployee = employeeDAO.getByID(employeeId);
        ClassSession curSession = sessionDAO.getEvent(inputData.getSessionId());
        if (!curSession.containsStaff(curEmployee)) {
            RemoveFromSessionOutputData outputData = new RemoveFromSessionOutputData(false,
                    "Employee is not in the session");
            myPresenter.prepareView(outputData);
        } else {
            // Remove the employee from the session and the session from the employee
            curSession.removeStaff(curEmployee);
            curEmployee.removeSession(curSession);

            RemoveFromSessionOutputData outputData = new RemoveFromSessionOutputData(true,
                    "Successfully removed employee from session");
            myPresenter.prepareView(outputData);
        }
    }
}