package interface_adapter;

import use_case.RemoveFromCourseInputBoundary;
import use_case.RemoveFromCourseInputData;

public class RemoveFromCourseController {
    private RemoveFromCourseInputBoundary removeFromCourseInteractor;

    public RemoveFromCourseController(RemoveFromCourseInputBoundary removeFromCourseInteractor) {
        this.removeFromCourseInteractor = removeFromCourseInteractor;
    }
    public void removeFromCourse(String userID, String courseCode) {
        RemoveFromCourseInputData inputData = new RemoveFromCourseInputData(userID, courseCode);
        removeFromCourseInteractor.removeFromCourse(inputData);
    }
}
