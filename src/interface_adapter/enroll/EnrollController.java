package interface_adapter.enroll;

import use_case.enroll.EnrollInputBoundary;
import use_case.enroll.EnrollInputData;

public class EnrollController {
    private EnrollInputBoundary enrollInteractor;


    public EnrollController(EnrollInputBoundary enrollInteractor) {
        this.enrollInteractor = enrollInteractor;
    }

    public void enroll(String invitorID, String inviteeID, String courseCode) {
        EnrollInputData inputData = new EnrollInputData(invitorID, inviteeID, courseCode);
        enrollInteractor.enroll(inputData);
    }
}
