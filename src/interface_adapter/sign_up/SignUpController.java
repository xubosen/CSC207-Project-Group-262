package interface_adapter.sign_up;

import use_case.sign_up.SignupInputBoundary;
import use_case.sign_up.SignupInputData;

public class SignUpController {
    private SignupInputBoundary signUpInteractor;

    public SignUpController(SignupInputBoundary signUpInteractor) {
        this.signUpInteractor = signUpInteractor;
    }

    public void signUp(String userID, String name, String email, String password, String role) {
        SignupInputData inputData = new SignupInputData(userID, name, email, password, role);
        signUpInteractor.signUp(inputData);
    }
}
