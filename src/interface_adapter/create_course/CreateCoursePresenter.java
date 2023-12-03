package interface_adapter.create_course;

import use_case.create_course.CreateCourseOutputBoundary;
import use_case.create_course.CreateCourseOutputData;

public class CreateCoursePresenter implements CreateCourseOutputBoundary {
    private CreateCourseViewModel createCourseViewModel;

    /**
     * Initializer for the presenter of the create course use case.
     * @param createCourseViewModel The view model of this use case.
     */
    public CreateCoursePresenter(CreateCourseViewModel createCourseViewModel) {
        this.createCourseViewModel = createCourseViewModel;
    }

    /**
     * Prepare view with the current outputdata.
     * @param outputData The data that will be output when createCourse has been tried.
     */
    public void prepareView(CreateCourseOutputData outputData) {
        CreateCourseState curState = createCourseViewModel.getState();
        curState.setCourseCreationSuccessful(outputData.isSuccessful());
        curState.setCourseCreationResponseMessage(outputData.getMessage());
        createCourseViewModel.setState(curState);

        createCourseViewModel.firePropertyChanged();
    }
}
