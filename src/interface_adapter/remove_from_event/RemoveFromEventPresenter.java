package interface_adapter.remove_from_event;

import use_case.remove_from_event.RemoveFromEventOutputBoundary;
import use_case.remove_from_event.RemoveFromEventOutputData;

public class RemoveFromEventPresenter implements RemoveFromEventOutputBoundary {
    private RemoveFromEventViewModel viewModel;
    public RemoveFromEventPresenter(RemoveFromEventViewModel viewModel) {
        this.viewModel = viewModel;
    }


    public void prepareView(RemoveFromEventOutputData outputData) {
        RemoveFromEventState curState = viewModel.getState();
        curState.setRemoveSuccessful(outputData.isSuccessful());
        curState.setRemoveResponseMessage(outputData.getMessage());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
