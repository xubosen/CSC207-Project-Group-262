package interface_adapter.get_events;

import use_case.get_events.GetEventOutputBoundary;
import use_case.get_events.GetEventOutputData;

public class GetEventPresenter implements GetEventOutputBoundary {
    private GetEventViewModel viewModel;

    public GetEventPresenter(GetEventViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GetEventOutputData outputData) {
        GetEventState curState = viewModel.getState();
        curState.setEventIDs(outputData.getEventIDs());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
