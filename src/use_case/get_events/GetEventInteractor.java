package use_case.get_events;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Employee;
import entity.Event;

import java.util.ArrayList;

public class GetEventInteractor implements GetEventInputBoundary{
    private GetEventOutputBoundary presenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;

    public GetEventInteractor(GetEventOutputBoundary presenter, InMemoryEmployeeDataAccessObject employeeDAO) {
        this.presenter = presenter;
        this.employeeDAO = employeeDAO;
    }

    public void getEvent(GetEventInputData inputData) {
        Employee currentEmployee = employeeDAO.getByID(inputData.getUserID());
        ArrayList<String> eventIDs = new ArrayList<>();
        for (Course course : currentEmployee.getCourses().values()) {
            for (Event event : course.getEvents().values()) {
                if (event.containStaff(currentEmployee)) {
                    eventIDs.add(event.getEventID());
                }
            }
        }
        presenter.present(new GetEventOutputData(eventIDs));
    }

}
