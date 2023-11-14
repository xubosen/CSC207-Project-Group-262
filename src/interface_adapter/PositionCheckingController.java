package interface_adapter;

import use_case.PositionCheckingInputBoundary;
import use_case.PositionCheckingInputData;

public class PositionCheckingController {
    private PositionCheckingInputBoundary isAdminInteractor;

    public PositionCheckingController(PositionCheckingInputBoundary isAdminInteractor) {
        this.isAdminInteractor = isAdminInteractor;
    }

    public void isAdmin(String userID, String courseCode) {
        PositionCheckingInputData inputData = new PositionCheckingInputData(userID, courseCode);
        isAdminInteractor.checkIsAdmin(inputData);
    }
}
