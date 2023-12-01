package use_case.get_courses;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public class GetCoursesInteractor implements GetCoursesInputBoundary{
    private GetCoursesOutputBoundary presenter;
    private InMemoryEmployeeDataAccessObject employeeDataAccessObject;

    public GetCoursesInteractor(GetCoursesOutputBoundary presenter, InMemoryEmployeeDataAccessObject employeeDataAccessObject) {
        this.presenter = presenter;
        this.employeeDataAccessObject = employeeDataAccessObject;
    }

    @Override
    public void getCourses(GetCoursesInputData inputData) {
        Employee curEmployee = employeeDataAccessObject.getByID(inputData.getUserID());

        HashMap<String, Course> courses = curEmployee.getCourses();
        ArrayList<String> courseCodes = new ArrayList<String>();
        for (String courseCode : courses.keySet()) {
            courseCodes.add(courseCode);
        }

        GetCoursesOutputData outputData = new GetCoursesOutputData(courseCodes);
        presenter.present(outputData);
    }
}
