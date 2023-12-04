package use_case.invite_to_session;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.ClassSession;
import entity.Employee;
import entity.Event;

public class InviteToSessionInteractor implements InviteToSessionInputBoundary{
    private InviteToSessionOutputBoundary inviteToSessionPresenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemorySessionDataAccessObject sessionDAO;

    public InviteToSessionInteractor(InviteToSessionOutputBoundary inviteToSessionPresenter,
                                     InMemoryEmployeeDataAccessObject employeeDAO,
                                     InMemorySessionDataAccessObject sessionDAO) {
        this.inviteToSessionPresenter = inviteToSessionPresenter;
        this.sessionDAO = sessionDAO;
        this.employeeDAO = employeeDAO;
    }

    public void invite(InviteToSessionInputData inputData) {
        InviteToSessionOutputData output;
        Employee invitee;
        Employee invitor;
        ClassSession curSession;
        Event parentEvent;

        // If the session does not exist return false and a corresponding message in output data. Otherwise, get the
        // information required to add the invitee to the session
        if (! doesSessionExist(inputData)) {
            output = new InviteToSessionOutputData(false, "Session does not exist");
            inviteToSessionPresenter.prepareView(output);
            return;
        } else {
            invitee = getEmployeeFromInputData(inputData.getInviteeID());
            invitor = getEmployeeFromInputData(inputData.getInvitorID());
            curSession = getSessionFromInputData(inputData);
            parentEvent = curSession.getEvent();
        }

        // If the invitee exists
        if (! doesEmployeeExist(inputData.getInviteeID())) {
            output = new InviteToSessionOutputData(false, "Invitee does not exist");

        // Check if the invitee is staff of the event
        } else if (! parentEvent.containStaff(invitee)) {
            output = new InviteToSessionOutputData(false, "Invitee is not staff of the event");

        // Check if the invitor is staff of the event
        } else if (! parentEvent.containStaff(invitor)) {
            output = new InviteToSessionOutputData(false, "Invitor is not staff of the event");

        } else {
            // Try to add the employee to the session
            boolean enrollSuccessful = curSession.addStaff(invitee);

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


    private boolean doesEmployeeExist(String employeeID) {
        return employeeDAO.existsByID(employeeID);
    }

    private Employee getEmployeeFromInputData(String employeeID) {
        return employeeDAO.getByID(employeeID);
    }

    private boolean doesSessionExist(InviteToSessionInputData inputData) {
        return sessionDAO.existsByID(inputData.getSessionID());
    }

    private ClassSession getSessionFromInputData(InviteToSessionInputData inputData) {
        return sessionDAO.getByID(inputData.getSessionID());
    }

}
