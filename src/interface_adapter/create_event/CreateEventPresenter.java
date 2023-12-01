package interface_adapter.create_event;

import use_case.create_event.CreateEventOutputBoundary;
import use_case.create_event.CreateEventOutputData;

public class CreateEventPresenter implements CreateEventOutputBoundary {
    private CreateEventViewModel createEventViewModel;

    /**
     * Initializer for the presenter of the create event use case.
     * @param createEventViewModel The view model of this use case.
     */
    public CreateEventPresenter(CreateEventViewModel createEventViewModel) {
        this.createEventViewModel = createEventViewModel;
    }

    /**
     * Prepare view with the current outputdata.
     * @param outputData The data that will be output when createCourse has been tried.
     */
    public void prepareView(CreateEventOutputData outputData) {
        // TODO: Delete this line after testing
        System.out.println(outputData.getMessage());

        CreateEventState curState = createEventViewModel.getState();
        curState.setEventCreationSuccessful(outputData.isSuccessful());
        curState.setEventCreationResponseMessage(outputData.getMessage());
        createEventViewModel.setState(curState);

        createEventViewModel.firePropertyChanged();
    }
}
