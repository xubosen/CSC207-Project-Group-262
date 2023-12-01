package interface_adapter.remove_from_course;

import use_case.remove_from_event.RemoveFromEventOutputBoundary;
import use_case.remove_from_event.RemoveFromEventOutputData;

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
