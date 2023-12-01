package interface_adapter.get_courses;

import use_case.get_courses.GetCoursesOutputBoundary;
import use_case.get_courses.GetCoursesOutputData;

public class GetCoursesPresenter implements GetCoursesOutputBoundary {
    private GetCoursesViewModel viewModel;

    public GetCoursesPresenter(GetCoursesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GetCoursesOutputData outputData) {
        GetCoursesState curState = viewModel.getState();
        curState.setCourses(outputData.getCourses());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
