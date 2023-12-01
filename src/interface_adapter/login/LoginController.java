package interface_adapter.login;

import use_case.log_in.LoginInputBoundary;
import use_case.log_in.LoginInputData;

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
