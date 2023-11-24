package interface_adapter.remove_from_session;

import use_case.remove_from_session.RemoveFromSessionOutputBoundary;
import use_case.remove_from_session.RemoveFromSessionOutputData;

public class RemoveFromSessionPresenter implements RemoveFromSessionOutputBoundary {
    private RemoveFromSessionViewModel viewModel;
    public RemoveFromSessionPresenter(RemoveFromSessionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void prepareView(RemoveFromSessionOutputData outputData) {
        RemoveFromSessionState curState = viewModel.getState();
        curState.setRemoveSuccessful(outputData.isSuccessful());
        curState.setRemoveResponseMessage(outputData.getMessage());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
