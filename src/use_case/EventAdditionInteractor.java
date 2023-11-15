package use_case;

import data_access.InMemoryEmployeeDataAccessObject;
import data_access.InMemoryEventDataAccessObject;
import entity.Employee;
import entity.Event;

public class EventAdditionInteractor implements EventAdditionInputBoundary {
    private EventAdditionOutputBoundary eventAdditionPresenter;
    private InMemoryEmployeeDataAccessObject employeesDAO;
    private InMemoryEventDataAccessObject eventsDAO;


    public EventAdditionInteractor(EventAdditionOutputBoundary eventAdditionPresenter, InMemoryEmployeeDataAccessObject employeesDAO,
                            InMemoryEventDataAccessObject eventsDAO) {
        this.eventAdditionPresenter = eventAdditionPresenter;
        this.employeesDAO = employeesDAO;
        this.eventsDAO = eventsDAO;
    }

    public void addEmployeeToEvent(EventAdditionInputData inputData) {
        EventAdditionOutputData output;

        // If the employee does not exist return false and a corresponding message in output data

        if (! doesEmployeeExist(inputData)) {
            output = new EventAdditionOutputData(false, "Employee does not exist");
        }
        // If the event does not exist return false and a corresponding message in output data

        else if (! doesEventExist(inputData)) {
            output = new EventAdditionOutputData(false, "Event does not exist");
        }

        // If the employee exists and the course exists, try to add the employee to the course

        else {
            Employee curEmployee = getEmployeeFromInputData(inputData);
            Event curEvent = getEventFromInputData(inputData);

            // Try to add the Employee to the Event

            boolean additionSuccessful = curEvent.addStaff(curEmployee);

            // If the employee was added successfully, return true in output data

            if (additionSuccessful) {
                output = new EventAdditionOutputData(true, "Enroll Successful!");
            }

            // If the employee was unsuccessful, it means that the employee is already enrolled in the event

            else {
                output = new EventAdditionOutputData(false, "This user is already enrolled in the event");
            }
        }

        // Call the presenter to present the output data
        eventAdditionPresenter.prepareView(output);
    }

    private boolean doesEmployeeExist(EventAdditionInputData inputData) {
        return employeesDAO.existsByID(inputData.getUserID());
    }

    private Employee getEmployeeFromInputData(EventAdditionInputData inputData) {
        return employeesDAO.getByID(inputData.getUserID());
    }

    private boolean doesEventExist(EventAdditionInputData inputData) {
        return eventsDAO.existsByID(inputData.getEventID());
    }

    private Event getEventFromInputData(EventAdditionInputData inputData) {
        return eventsDAO.getEvent(inputData.getEventID());
    }


}