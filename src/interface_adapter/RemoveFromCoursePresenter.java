package interface_adapter;

import use_case.RemoveFromEventOutputBoundary;
import use_case.RemoveFromEventOutputData;

public class RemoveFromCoursePresenter implements RemoveFromEventOutputBoundary {
    private RemoveFromCourseViewModel viewModel;
    public RemoveFromCoursePresenter(RemoveFromCourseViewModel viewModel) {
        this.viewModel = viewModel;
    }


    public void prepareView(RemoveFromEventOutputData outputData) {
        RemoveFromCourseState curState = viewModel.getState();
        curState.setRemoveSuccessful(outputData.isSuccessful());
        curState.setRemoveResponseMessage(outputData.getMessage());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
