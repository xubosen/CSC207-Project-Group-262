package use_case.log_in;

public interface LoginOutputBoundary {
    void execute(LoginInputData loginInputData);

    void prepareSuccessView(LoginOutputData employee);

    void prepareFailView(String error);
}