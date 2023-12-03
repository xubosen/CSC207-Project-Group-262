package use_case.add_to_event;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.Course;
import entity.Employee;
import entity.Event;
import entity.Instructor;

public class EventAdditionInteractor implements EventAdditionInputBoundary {
    private EventAdditionOutputBoundary eventAdditionPresenter;
    private InMemoryEmployeeDataAccessObject employeesDAO;
    private InMemoryEventDataAccessObject eventsDAO;



    public EventAdditionInteractor(EventAdditionOutputBoundary eventAdditionPresenter, InMemoryEmployeeDataAccessObject employeesDAO, InMemoryEventDataAccessObject eventsDAO) {
        this.eventAdditionPresenter = eventAdditionPresenter;
        this.employeesDAO = employeesDAO;
        this.eventsDAO = eventsDAO;
    }

    public void addEmployeeToEvent(EventAdditionInputData inputData) {
        EventAdditionOutputData output;

        Employee invitor = employeesDAO.getByID(inputData.getUserID());
        Employee invitee = employeesDAO.getByID(inputData.getInviteeID());
        Event curEvent = getEventFromInputData(inputData);

        // If the employee does not exist return false and a corresponding message in output data

        if (! doesEmployeeExist(inputData.getInviteeID())) {
            output = new EventAdditionOutputData(false, "Invitee does not exist");

        // If the event does not exist return false and a corresponding message in output data
        } else if (! doesEventExist(inputData)) {
            output = new EventAdditionOutputData(false, "Event does not exist");

        } else if (! curEvent.getCourse().containsStaff(invitor)) {
            output = new EventAdditionOutputData(false, "Access Denied: You are not an instructor " +
                    "for this course");
        } else if (! curEvent.getCourse().containsStaff(invitee)) {
            output = new EventAdditionOutputData(false, "Access Denied: The employee you are trying to " +
                    "add is not a staff of this course");
        }

        // If the employee exists, the event exists and the employee is in the course, try to add the employee
        else {
            // Try to add the Employee to the Event

            boolean additionSuccessful = curEvent.addStaff(invitee);

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

    private boolean doesEmployeeExist(String userID) {
        return employeesDAO.existsByID(userID);
    }

    private boolean doesEventExist(EventAdditionInputData inputData) {
        return eventsDAO.existsByID(inputData.getEventID());
    }

    private Event getEventFromInputData(EventAdditionInputData inputData) {
        return eventsDAO.getByID(inputData.getEventID());
    }


}