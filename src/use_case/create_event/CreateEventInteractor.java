package use_case.create_event;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.*;

public class CreateEventInteractor implements CreateEventInputBoundary {
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
    public CreateEventInteractor(CreateEventOutputBoundary createEventPresenter,
                                 InMemoryEmployeeDataAccessObject employeesDAO,
                                 InMemoryEventDataAccessObject eventsDAO,
                                 InMemoryCourseDataAccessObject coursesDAO) {
        this.createEventPresenter = createEventPresenter;
        this.employeesDAO = employeesDAO;
        this.eventsDAO = eventsDAO;
        this.coursesDAO = coursesDAO;
    }

    /**
     * Tries to create the event, creates output data to input into our presenter
     *
     * Representational Invariant:
     * - The user creating the event is an instructor
     *
     * @param inputData
     */
    public void createEvent(CreateEventInputData inputData) {

        // Initialize the output data package
        CreateEventOutputData output;
        Course course = coursesDAO.getByID(inputData.getCourseCode());
        Employee curUser = employeesDAO.getByID(inputData.getCreatorID());

        // If the event already exists return false and the corresponding message in output data
        if (doesEventExist(inputData)) {
            output = new CreateEventOutputData(false, "Event already exists.");

        // If the user is not an instructor
        } else if (!isInstructor(inputData)) {
            // This error message is only here for testing purposes because this view shouldn't be available to those
            // who are not instructors.
            output = new CreateEventOutputData(false, "Access denied. User is not an Instructor.");
        } else if (!doesCourseExist(inputData)) {
            output = new CreateEventOutputData(false, "Course does not exist.");

        } else if (!course.containsStaff(curUser)) {
            output = new CreateEventOutputData(false, "Access denied. User is not an Instructor.");
        } else {

            if (inputData.getTypeOfEvent().equals("lecture")) {
                Lecture newLecture = new Lecture(inputData.getEventName(), inputData.getEventID(), course);
                eventsDAO.addEvent(newLecture);
                output = new CreateEventOutputData(true, "Event created successfully");

            } else if (inputData.getTypeOfEvent().equals("tutorial")){
                Tutorial newTutorial = new Tutorial(inputData.getEventName(), inputData.getEventID(), course);
                eventsDAO.addEvent(newTutorial);
                output = new CreateEventOutputData(true, "Event created successfully");

            } else {
                output = new CreateEventOutputData(false, "Invalid event type");
            }
        }

        // Call the presenter to present the output data
        createEventPresenter.prepareView(output);
    }


    private boolean isInstructor(CreateEventInputData inputData) {
        Employee curUser = employeesDAO.getByID(inputData.getCreatorID());
        return curUser.getType().equals("instructor");
    }


    private boolean doesEventExist(CreateEventInputData inputData) {
        return eventsDAO.existsByID(inputData.getEventID());
    }

    private boolean doesCourseExist(CreateEventInputData inputData) {
        return coursesDAO.existsByID(inputData.getCourseCode());
    }
}
