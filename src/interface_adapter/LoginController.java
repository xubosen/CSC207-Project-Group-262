package interface_adapter;

import use_case.LoginInputBoundary;
import use_case.LoginInputData;

public class LoginController {

    final LoginInputBoundary userLoginUseCaseInteractor;

    public LoginController(LoginInputBoundary userLoginUseCaseInteractor) {
        this.userLoginUseCaseInteractor = userLoginUseCaseInteractor;
    }

    public void execute(String username, String password) {
        LoginInputData loginInputData = new LoginInputData(username, password);

        userLoginUseCaseInteractor.execute(loginInputData);
    }
}
