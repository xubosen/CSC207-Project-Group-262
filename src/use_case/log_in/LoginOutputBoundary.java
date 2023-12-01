package use_case.log_in;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData employee);

    void prepareFailView(String error);
}