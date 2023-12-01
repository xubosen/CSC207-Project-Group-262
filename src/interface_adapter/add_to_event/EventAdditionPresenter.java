package interface_adapter.add_to_event;

import use_case.add_to_event.EventAdditionOutputBoundary;
import use_case.add_to_event.EventAdditionOutputData;

public class EventAdditionPresenter implements EventAdditionOutputBoundary {
    private EventAdditionViewModel eventAdditionViewModel;
    public EventAdditionPresenter(EventAdditionViewModel eventAdditionViewModel) {
        this.eventAdditionViewModel = eventAdditionViewModel;
    }

    public void prepareView(EventAdditionOutputData outputData) {
        EventAdditionState curState = eventAdditionViewModel.getState();
        curState.setInviteSuccessful(outputData.isSuccessful());
        curState.setInviteResponseMessage(outputData.getMessage());
        eventAdditionViewModel.setState(curState);

        eventAdditionViewModel.firePropertyChanged();
    }

}