package use_case.invite_to_session;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.ClassSession;
import entity.Employee;

public class InviteToSessionInteractor implements InviteToSessionInputBoundary{
    private InviteToSessionOutputBoundary inviteToSessionPresenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemorySessionDataAccessObject sessionDAO;

    public InviteToSessionInteractor(InviteToSessionOutputBoundary inviteToSessionPresenter, InMemoryEmployeeDataAccessObject employeeDAO, InMemorySessionDataAccessObject sessionDAO) {
        this.inviteToSessionPresenter = inviteToSessionPresenter;
        this.sessionDAO = sessionDAO;
        this.employeeDAO = employeeDAO;
    }

    public void invite(InviteToSessionInputData inputData) {
        InviteToSessionOutputData output;

        // If the employee does not exist return false and a corresponding message in output data
        if (! doesEmployeeExist(inputData)) {
            output = new InviteToSessionOutputData(false, "Employee does not exist");

            // If the session does not exist return false and a corresponding message in output data
        } else if (! doesSessionExist(inputData)) {
            output = new InviteToSessionOutputData(false, "Session does not exist");

            // If the employee exists and the session exists, try to add the employee to the session
        } else {
            Employee curEmployee = getEmployeeFromInputData(inputData);
            ClassSession curSession = getSessionFromInputData(inputData);

            // Try to add the employee to the session
            boolean enrollSuccessful = curSession.addStaff(curEmployee);

            // If the employee was added successfully, return true in output data
            if (enrollSuccessful) {
                output = new InviteToSessionOutputData(true, "Enroll successful");

                // If the employee was unsuccessful, it means that the employee is already enrolled in the session
            } else {
                output = new InviteToSessionOutputData(false, "Employee is already enrolled in the session");
            }
        }

        // Call the presenter to present the output data
        inviteToSessionPresenter.prepareView(output);
    }


    private boolean doesEmployeeExist(InviteToSessionInputData inputData) {
        return employeeDAO.existsByID(inputData.getUserID());
    }

    private Employee getEmployeeFromInputData(InviteToSessionInputData inputData) {
        return employeeDAO.getByID(inputData.getUserID());
    }

    private boolean doesSessionExist(InviteToSessionInputData inputData) {
        return sessionDAO.existsByID(inputData.getSessionID());
    }

    private ClassSession getSessionFromInputData(InviteToSessionInputData inputData) {
        return sessionDAO.getByID(inputData.getSessionID());
    }

}
