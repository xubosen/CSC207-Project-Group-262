package interface_adapter.sign_up;

import use_case.sign_up.SignupOutputBoundary;
import use_case.sign_up.SignupOutputData;

public class SignUpPresenter implements SignupOutputBoundary {
    private SignUpViewModel viewModel;

    public SignUpPresenter(SignUpViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void prepareView(SignupOutputData outputData) {
        SignUpState curState = viewModel.getState();
        curState.setSignupSuccessful(outputData.isSuccessful());
        curState.setSignupResponseMessage(outputData.getMessage());
        viewModel.setState(curState);
        viewModel.firePropertyChanged();
    }
}
