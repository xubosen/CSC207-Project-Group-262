package use_case;

public interface LoginOutputBoundary {
    void execute(LoginInputData loginInputData);

    void prepareSuccessView(LoginOutputData employee);

    void prepareFailView(String error);
}