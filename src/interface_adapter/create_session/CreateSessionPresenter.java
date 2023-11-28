package interface_adapter.create_session;

import use_case.create_event.CreateEventOutputBoundary;
import use_case.create_event.CreateEventOutputData;

public class CreateSessionPresenter implements CreateEventOutputBoundary {
    // TODO: Double check if this works
    private CreateSessionViewModel createSessionViewModel;

    /**
     * Initializer for the presenter of the create event use case.
     * @param createSessionViewModel The view model of this use case.
     */
    public CreateSessionPresenter(CreateSessionViewModel createSessionViewModel) {
        this.createSessionViewModel = createSessionViewModel;
    }

    /**
     * Prepare view with the current outputdata.
     * @param outputData The data that will be output when createCourse has been tried.
     */
    public void prepareView(CreateEventOutputData outputData) {
        System.out.println(outputData.getMessage());
        CreateSessionState curState = createSessionViewModel.getState();
        curState.setEventCreationSuccessful(outputData.isSuccessful());
        curState.setEventCreationResponseMessage(outputData.getMessage());
        createSessionViewModel.setState(curState);

        createSessionViewModel.firePropertyChanged();
    }
}
