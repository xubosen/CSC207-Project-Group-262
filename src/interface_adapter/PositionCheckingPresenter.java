package interface_adapter;

import use_case.PositionCheckingOutputBoundary;

public class PositionCheckingPresenter implements PositionCheckingOutputBoundary {
    private boolean isAdmin;

    public PositionCheckingPresenter() {
        isAdmin = false;
    }

    public void presentIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
