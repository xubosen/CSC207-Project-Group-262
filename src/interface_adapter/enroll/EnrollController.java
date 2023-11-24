package interface_adapter.enroll;

import use_case.enroll.EnrollInputBoundary;
import use_case.enroll.EnrollInputData;

public class EnrollController {
    private EnrollInputBoundary enrollInteractor;


    public EnrollController(EnrollInputBoundary enrollInteractor) {
        this.enrollInteractor = enrollInteractor;
    }

    public void enroll(String userID, String courseCode) {
        // TODO: Discuss using email instead of userID at next meeting
        EnrollInputData inputData = new EnrollInputData(userID, "", courseCode);
        enrollInteractor.enroll(inputData);
    }
}
