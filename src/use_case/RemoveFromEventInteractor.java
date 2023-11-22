package use_case;

import data_access.InMemoryEmployeeDataAccessObject;
import data_access.InMemoryEventDataAccessObject;
import entity.Employee;
import entity.Event;

public class RemoveFromEventInteractor implements RemoveFromEventInputBoundary{
    private RemoveFromEventOutputBoundary myPresenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemoryEventDataAccessObject eventDAO;

    public RemoveFromEventInteractor(RemoveFromEventOutputBoundary myPresenter, InMemoryEmployeeDataAccessObject employeeDAO, InMemoryEventDataAccessObject eventDAO) {
        this.myPresenter = myPresenter;
        this.employeeDAO = employeeDAO;
        this.eventDAO = eventDAO;
    }

    @Override
    public void removeFromEvent(RemoveFromEventInputData inputData) {
        // Check if the employee and event exist
        String employeeID = inputData.getEmployeeID();
        if (!employeeDAO.existsByID(employeeID)) {
            RemoveFromEventOutputData outputData = new RemoveFromEventOutputData(false, "Employee does not exist");
            myPresenter.prepareView(outputData);
        } else if (!eventDAO.existsByID(inputData.getEventID())) {
            RemoveFromEventOutputData outputData = new RemoveFromEventOutputData(false, "Event does not exist");
            myPresenter.prepareView(outputData);
        }

        // Check if the employee is in the event
        Employee curEmployee = employeeDAO.getByID(employeeID);
        Event curEvent = eventDAO.getEvent(inputData.getEventID());
        if (!curEvent.listStaff().containsValue(curEmployee)) {
            RemoveFromEventOutputData outputData = new RemoveFromEventOutputData(false, "Employee is not in event");
            myPresenter.prepareView(outputData);
        } else {
            // Remove the employee from the event
            curEvent.removeStaff(curEmployee);
            RemoveFromEventOutputData outputData = new RemoveFromEventOutputData(true, "Employee removed from event");
            myPresenter.prepareView(outputData);
        }
    }
}
