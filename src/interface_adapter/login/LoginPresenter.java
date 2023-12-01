package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import use_case.log_in.LoginOutputBoundary;
import use_case.log_in.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;

    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void prepareSuccessView(LoginOutputData response) {
        LoginState loginState = loginViewModel.getState();
        loginState.setType(response.getType());
        loginState.setLoginSuccessful(response.isLoginSuccessful());
        loginViewModel.firePropertyChanged();
    }

    public void prepareFailView(String error) {
        // Handle login failure
        // Update loginViewModel to reflect the error
        LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }

}
