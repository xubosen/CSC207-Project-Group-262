package use_case.create_event;

import data_access.InMemoryCourseDataAccessObject;
import data_access.InMemoryEmployeeDataAccessObject;
import data_access.InMemoryEventDataAccessObject;
import entity.Employee;
import entity.Event;
import entity.Course
import entity.Instructor;

public class CreateEventInteractor implements CreateEventInputBoundary {
    // TODO: Might be completed, need to test
    private CreateEventOutputBoundary createEventPresenter;
    private InMemoryEventDataAccessObject eventsDAO;
    private InMemoryEmployeeDataAccessObject employeesDAO;

    /**
     * Initializes the event creator interactor.
     * @param createEventPresenter Create course output boundary.
     * @param employeesDAO The in memory DAO for Employees We might not need this because it might create an empty event.
     * @param eventsDAO The In memory DAO for events
//     * @param coursesDAO The in memory DAO for courses Maybe need this because we need to check which Course it belongs to
     */
    public CreateEventInteractor(CreateEventOutputBoundary createEventPresenter, InMemoryEmployeeDataAccessObject employeesDAO,
                                 InMemoryEventDataAccessObject eventsDAO) {
        this.createEventPresenter = createEventPresenter;
        this.employeesDAO = employeesDAO;
        this.eventsDAO = eventsDAO;
    }

    /**
     * Tries to create the course, creates output data to input into our presenter
     * @param inputData
     */
    public void createEvent(CreateEventInputData inputData) {
        CreateEventOutputData output;

        // If the event already exists return false and the corresponding message in output data
        if (!doesEventExist(inputData)) {
            output = new CreateEventOutputData(false, "Event already exists.");
        } else if (!isInstructor(inputData)) {
            // Might not need this error message because this view shouldn't be available to those who are not instructors.
            output = new CreateEventOutputData(false, "User is not an Instructor");
        } else {
            // Try to create the new course from the input
            // TODO: Figure out how to pull most recent button clicked.
//            Course course = courseDAO.getCourse("CourseID of most recent button press.");
//            eventsDAO.addEvent(new Event(inputData.getEventName(), inputData.getEventID(), course));

            output = new CreateEventOutputData(true, "event created successfully");
        }

        // Call the presenter to present the output data
        createEventPresenter.prepareView(output);
    }


    private boolean isInstructor(CreateEventInputData inputData) {
        // TODO: Is this available to instructors as well.
//        return employeesDAO.getByID(inputData.getCurrentUserUsingProgram.getClass().equals(Instructor.class));
        return false;
//        return employeesDAO.getByID(inputData.getAdminID()).getClass().equals(Instructor.class);
    }

//    private Employee getEmployeeFromInputData(CreateCourseInputData inputData) {
//        return employeesDAO.getByID(inputData.getAdminID());
//    }

    private boolean doesEventExist(CreateEventInputData inputData) {
        return eventDAO.existsByID(inputData.getEventID());
    }
}
