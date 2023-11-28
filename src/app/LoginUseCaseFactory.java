package app;

import data_access.FileEmployeeDataAccessObject;
import data_access.InMemoryEmployeeDataAccessObject;
import data_access.EmployeeDataAccessInterface;
import entity.EmployeeFactory;
import interface_adapter.*;
import use_case.LoginInputBoundary;
import use_case.LoginInteractor;
import use_case.LoginOutputBoundary;
import view.LoginView;

import javax.swing.*;
import java.io.IOException;

public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    public static LoginView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, DashboardViewModel dashboardViewModel) {

        try {
            LoginController loginController = createUserLoginUseCase(viewManagerModel, loginViewModel, dashboardViewModel);
            return new LoginView(loginViewModel, viewManagerModel, loginController, "dashboard");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open employee data file.");
        }

        return null;
    }

    private static LoginController createUserLoginUseCase(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, DashboardViewModel dashboardViewModel) throws IOException {
        EmployeeDataAccessInterface employeeDataAccessObject = new FileEmployeeDataAccessObject("src/database/employeedata.csv", new EmployeeFactory());

        // Notice how we pass this method's parameters to the Presenter.
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel, dashboardViewModel);

        EmployeeFactory employeeFactory = new EmployeeFactory();

        LoginInputBoundary userLoginInteractor = new LoginInteractor(
                employeeDataAccessObject, loginOutputBoundary, employeeFactory);

        return new LoginController(userLoginInteractor);
    }
}
