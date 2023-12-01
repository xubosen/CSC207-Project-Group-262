package interface_adapter.get_sessions;

import use_case.get_sessions.GetSessionsOutputBoundary;
import use_case.get_sessions.GetSessionsOutputData;

public class GetSessionsPresenter implements GetSessionsOutputBoundary {
    private GetSessionsViewModel viewModel;

    public GetSessionsPresenter(GetSessionsViewModel getSessionsViewModel) {
        this.viewModel = getSessionsViewModel;
    }

    @Override
    public void present(GetSessionsOutputData outputData) {
        GetSessionsState curState = viewModel.getState();
        curState.setSessions(outputData.getSessions());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
