//package app;
//
//import data_access.FileEmployeeDataAccessObject;
//import data_access.EmployeeDataAccessInterface;
//import entity.EmployeeFactory;
//import interface_adapter.*;
//import interface_adapter.dashboard.DashboardViewModel;
//import interface_adapter.login.LoginController;
//import interface_adapter.login.LoginPresenter;
//import interface_adapter.login.LoginViewModel;
//import use_case.log_in.LoginInputBoundary;
//import use_case.log_in.LoginInteractor;
//import use_case.log_in.LoginOutputBoundary;
//import view.IntroView.LoginView;
//
//import javax.swing.*;
//import java.io.IOException;
//
//public class LoginUseCaseFactory {
//
//    /** Prevent instantiation. */
//    private LoginUseCaseFactory() {}
//
//    public static LoginView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, DashboardViewModel dashboardViewModel) {
//
//        try {
//            LoginController loginController = createUserLoginUseCase(viewManagerModel, loginViewModel, dashboardViewModel);
//            return new LoginView(loginViewModel, viewManagerModel, loginController, "dashboard");
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "Could not open employee data file.");
//        }
//
//        return null;
//    }
//
//    private static LoginController createUserLoginUseCase(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, DashboardViewModel dashboardViewModel) throws IOException {
//        EmployeeDataAccessInterface employeeDataAccessObject = new FileEmployeeDataAccessObject("src/database/employeedata.csv", new EmployeeFactory());
//
//        // Notice how we pass this method's parameters to the Presenter.
//        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel, dashboardViewModel);
//
//        EmployeeFactory employeeFactory = new EmployeeFactory();
//
//        LoginInputBoundary userLoginInteractor = new LoginInteractor(
//                employeeDataAccessObject, loginOutputBoundary, employeeFactory);
//
//        return new LoginController(userLoginInteractor);
//    }
//}
