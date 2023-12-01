//package use_case.log_in;
//
//import data_access.EmployeeDataAccessInterface;
//import entity.Employee;
//import entity.EmployeeFactory;
//
//public class LoginInteractor implements LoginInputBoundary {
//    final EmployeeDataAccessInterface employeeDataAccessObject;
//    final LoginOutputBoundary loginPresenter;
//    final EmployeeFactory employeeFactory;
//
//    public LoginInteractor(EmployeeDataAccessInterface employeeDataAccessInterface,
//                           LoginOutputBoundary loginOutputBoundary,
//                           EmployeeFactory employeeFactory) {
//        this.employeeDataAccessObject = employeeDataAccessInterface;
//        this.loginPresenter = loginOutputBoundary;
//        this.employeeFactory = employeeFactory;
//    }
//
//    @Override
//    public void execute(LoginInputData loginInputData) {
//        Employee employee = employeeDataAccessObject.findByName(loginInputData.getUsername());
//
//        if (employee == null) {
//            loginPresenter.prepareFailView("Employee does not exist.");
//        } else if (!employee.getPassword().equals(loginInputData.getPassword())) {
//            loginPresenter.prepareFailView("Incorrect password.");
//        } else {
//            LoginOutputData loginOutputData = new LoginOutputData(employee.getName(), true);
//            loginPresenter.prepareSuccessView(loginOutputData);
//            // Optionally, update last login time or any other necessary info
//        }
//    }
//}
