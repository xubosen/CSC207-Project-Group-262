package interface_adapter.position_checking;

import use_case.position_checking.PositionCheckingOutputBoundary;

public class PositionCheckingPresenter implements PositionCheckingOutputBoundary {
    private boolean isAdmin;

    public PositionCheckingPresenter() {
        isAdmin = false;
    }

    public void presentIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
