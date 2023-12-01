package use_case.remove_from_event;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
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
        Event curEvent = eventDAO.getByID(inputData.getEventID());
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
