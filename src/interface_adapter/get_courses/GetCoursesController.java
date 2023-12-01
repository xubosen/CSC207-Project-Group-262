package interface_adapter.get_courses;

import use_case.get_courses.GetCoursesInputBoundary;
import use_case.get_courses.GetCoursesInputData;
import use_case.get_courses.GetCoursesInteractor;

public class GetCoursesController {
    private GetCoursesInputBoundary getCoursesInteractor;

    public GetCoursesController(GetCoursesInputBoundary getCoursesInteractor) {
        this.getCoursesInteractor = getCoursesInteractor;
    }
    public void getCourses(String userID) {
        GetCoursesInputData inputData = new GetCoursesInputData(userID);
        getCoursesInteractor.getCourses(inputData);
    }
}
