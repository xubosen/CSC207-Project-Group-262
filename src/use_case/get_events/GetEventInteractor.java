package use_case.get_events;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Employee;
import entity.Event;

public class GetEventInteractor {
    private GetEventOutputBoundary presenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;

    public GetEventInteractor(GetEventOutputBoundary presenter, InMemoryEmployeeDataAccessObject employeeDAO) {
        this.presenter = presenter;
        this.employeeDAO = employeeDAO;
    }

    public void getEvents(GetEventInputData inputData) {
//        Employee currentEmployee = employeeDAO.getByID(inputData.getUserID());
//        Event event
//        for (Course course : currentEmployee.getCourses().values()) {
//
//            for (Event event : course.getEvents().values()) {
//                presenter.present(event);
//            }
//        }

    }
}
