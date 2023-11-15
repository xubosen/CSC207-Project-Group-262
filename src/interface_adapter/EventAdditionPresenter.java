package interface_adapter;

import use_case.EventAdditionOutputBoundary;
import use_case.EventAdditionOutputData;

public class EventAdditionPresenter implements EventAdditionOutputBoundary {
    private EventAdditionViewModel eventAdditionViewModel;
    public EventAdditionPresenter(EventAdditionViewModel eventAdditionViewModel) {
        this.eventAdditionViewModel = eventAdditionViewModel;
    }

    public void prepareView(EventAdditionOutputData outputData) {
        System.out.println(outputData.getMessage());
        EventAdditionState curState = eventAdditionViewModel.getState();
        curState.setInviteSuccessful(outputData.isSuccessful());
        curState.setInviteResponseMessage(outputData.getMessage());
        eventAdditionViewModel.setState(curState);

        eventAdditionViewModel.firePropertyChanged();
    }

}