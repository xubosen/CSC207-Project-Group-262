package interface_adapter.position_checking;

import use_case.position_checking.PositionCheckingInputBoundary;
import use_case.position_checking.PositionCheckingInputData;

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
