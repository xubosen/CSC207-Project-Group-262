package use_case.create_event;

import data_access.InMemoryCourseDataAccessObject;
import data_access.InMemoryEmployeeDataAccessObject;
import data_access.InMemoryEventDataAccessObject;
import entity.*;

public class CreateEventInteractor implements CreateEventInputBoundary {
    // Currently getting employeesDAO.get employee by id is returning a null value.
    // TODO: Might be completed, need to test
    private CreateEventOutputBoundary createEventPresenter;
    private InMemoryEventDataAccessObject eventsDAO;
    private InMemoryEmployeeDataAccessObject employeesDAO;
    private InMemoryCourseDataAccessObject coursesDAO;

    /**
     * Initializes the event creator interactor.
     * @param createEventPresenter Create Event output boundary.
     * @param employeesDAO The in memory DAO for Employees We might not need this because it might create an empty event.
     * @param eventsDAO The In memory DAO for events
     * @param coursesDAO The in memory DAO for courses Maybe need this because we need to check which Course it belongs to
     */
    public CreateEventInteractor(CreateEventOutputBoundary createEventPresenter, InMemoryEmployeeDataAccessObject employeesDAO,
                                 InMemoryEventDataAccessObject eventsDAO, InMemoryCourseDataAccessObject coursesDAO) {
        this.createEventPresenter = createEventPresenter;
        this.employeesDAO = employeesDAO;
        this.eventsDAO = eventsDAO;
        this.coursesDAO = coursesDAO;
    }

    /**
     * Tries to create the event, creates output data to input into our presenter
     * @param inputData
     */
    public void createEvent(CreateEventInputData inputData) {
        CreateEventOutputData output;

        // If the event already exists return false and the corresponding message in output data
        if (doesEventExist(inputData)) {
            output = new CreateEventOutputData(false, "Event already exists.");
        } else if (!isInstructor(inputData)) {
            // Might not need this error message because this view shouldn't be available to those who are not instructors.
            output = new CreateEventOutputData(false, "User is not an Instructor.");
        // If the course does not exist.
        } else if (!doesCourseExist(inputData)) {
            output = new CreateEventOutputData(false, "Course does not exists.");

        } else {
            // Try to create the new course from the input
            // TODO: Figure out how to pull most recent button clicked so we don't need this weird course method thing.
            Course course = coursesDAO.getByID(inputData.getCourseCode());
            if (inputData.getTypeOfEvent().equals("Lecture"))
            {
                eventsDAO.addEvent(new Lecture(inputData.getEventName(), inputData.getEventID(), course));
                output = new CreateEventOutputData(true, "event created successfully");
            } else if (inputData.getTypeOfEvent().equals("Tutorial")){
                eventsDAO.addEvent(new Tutorial(inputData.getEventName(), inputData.getEventID(), course));
                output = new CreateEventOutputData(true, "event created successfully");
            } else {
                output = new CreateEventOutputData(false, "Invalid event type");
            }


        }

        // Call the presenter to present the output data
        createEventPresenter.prepareView(output);
    }


    private boolean isInstructor(CreateEventInputData inputData) {
        // TODO: Is this available to instructors as well.
        return employeesDAO.getByID(inputData.getCreatorID()).getClass().equals(Instructor.class);
    }

//    private Employee getEmployeeFromInputData(CreateCourseInputData inputData) {
//        return employeesDAO.getByID(inputData.getAdminID());
//    }

    private boolean doesEventExist(CreateEventInputData inputData) {
        return eventsDAO.existsByID(inputData.getEventID());
    }

    private boolean doesCourseExist(CreateEventInputData inputData) {
        return coursesDAO.existsByID(inputData.getCourseCode());
    }
}
