package use_case.log_in;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Employee;

public class LoginInteractor implements LoginInputBoundary {
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginOutputBoundary loginPresenter, InMemoryEmployeeDataAccessObject employeeDAO) {
        this.employeeDAO = employeeDAO;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        if (!employeeDAO.existsByID(username)) {
            System.out.println(employeeDAO.existsByID(username));
            loginPresenter.prepareFailView("User does not exist.");
        } else if (!passwordCorrect(username, password)) {
            loginPresenter.prepareFailView("Incorrect password.");
        } else {
            LoginOutputData loginOutputData = new LoginOutputData(username, getType(username), true);
            loginPresenter.prepareSuccessView(loginOutputData);
        }
    }

    private boolean passwordCorrect(String username, String password) {
        Employee employee = employeeDAO.getByID(username);
        return employee.getPassword().equals(password);
    }

    private String getType(String username) {
        return employeeDAO.getByID(username).getType();
    }
}
