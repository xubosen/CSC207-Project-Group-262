package interface_adapter.create_session;

import use_case.create_session.CreateSessionOutputBoundary;
import use_case.create_session.CreateSessionOutputData;

public class CreateSessionPresenter implements CreateSessionOutputBoundary {
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
    public void prepareView(CreateSessionOutputData outputData) {
        CreateSessionState curState = createSessionViewModel.getState();
        curState.setSessionCreationSuccessful(outputData.isSuccessful());
        curState.setSessionCreationResponseMessage(outputData.getMessage());
        createSessionViewModel.setState(curState);

        createSessionViewModel.firePropertyChanged();
    }
}
