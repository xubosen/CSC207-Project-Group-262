package interface_adapter;

import use_case.EnrollInputBoundary;
import use_case.EnrollInputData;

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
