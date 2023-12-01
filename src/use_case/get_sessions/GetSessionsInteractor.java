package use_case.get_sessions;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.ClassSession;
import entity.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public class GetSessionsInteractor implements GetSessionsInputBoundary{
    private GetSessionsOutputBoundary presenter;
    private InMemoryEmployeeDataAccessObject employeeDataAccessObject;

    public GetSessionsInteractor(GetSessionsOutputBoundary presenter, InMemoryEmployeeDataAccessObject employeeDataAccessObject) {
        this.presenter = presenter;
        this.employeeDataAccessObject = employeeDataAccessObject;
    }

    @Override
    public void getSessions(GetSessionsInputData input) {
        Employee employee = employeeDataAccessObject.getByID(input.getEmployeeId());
        HashMap<String, ClassSession> sessions = employee.getSessions();
        ArrayList<String> sessionList = new ArrayList<String>();
        for (String key : sessions.keySet()) {
            sessionList.add(key);
        }
        GetSessionsOutputData output = new GetSessionsOutputData(sessionList);
        System.out.println(sessionList);
        presenter.present(output);
    }
}
