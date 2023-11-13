package interface_adapter;

import use_case.LoginInputData;
import use_case.LoginOutputBoundary;
import use_case.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          DashboardViewModel dashboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.dashboardViewModel = dashboardViewModel;
    }

    public void prepareSuccessView(LoginOutputData response) {
        // Assuming LoginOutputData contains necessary information upon successful login
        // Update the login state or perform other necessary actions

        // Switch to the dashboard view on successful login
        viewManagerModel.setActiveView(dashboardViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String error) {
        // Handle login failure
        // Update loginViewModel to reflect the error
        LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(error); // Assuming you want to show the error in username field
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void execute(LoginInputData loginInputData) {

    }

    // Additional methods related to login process can be added here
}
