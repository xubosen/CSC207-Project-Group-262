package interface_adapter.remove_from_course;

import use_case.remove_from_course.RemoveFromCourseOutputBoundary;
import use_case.remove_from_course.RemoveFromCourseOutputData;
import use_case.remove_from_event.RemoveFromEventOutputBoundary;
import use_case.remove_from_event.RemoveFromEventOutputData;

public class RemoveFromCoursePresenter implements RemoveFromCourseOutputBoundary {
    private RemoveFromCourseViewModel viewModel;
    public RemoveFromCoursePresenter(RemoveFromCourseViewModel viewModel) {
        this.viewModel = viewModel;
    }


    public void prepareView(RemoveFromCourseOutputData outputData) {
        RemoveFromCourseState curState = viewModel.getState();
        curState.setRemoveSuccessful(outputData.isSuccessful());
        curState.setRemoveResponseMessage(outputData.getMessage());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
