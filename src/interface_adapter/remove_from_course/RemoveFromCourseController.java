package interface_adapter.remove_from_course;

import use_case.remove_from_course.RemoveFromCourseInputBoundary;
import use_case.remove_from_course.RemoveFromCourseInputData;

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
