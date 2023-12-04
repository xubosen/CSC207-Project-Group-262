package tests.use_case.log_in;

import org.junit.Before;
import org.junit.Test;
import use_case.log_in.LoginInputData;
import use_case.log_in.LoginInteractor;
import use_case.log_in.LoginOutputBoundary;
import use_case.log_in.LoginOutputData;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Employee;

import static org.junit.Assert.*;

public class LoginInteractorTest {
    private InMemoryEmployeeDataAccessObject employeeDAO;
    private Employee employee;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        employee = new Employee("user123", "User Name", "user@example.com", "password");
        employeeDAO.addEmployee(employee);
    }

    @Test
    public void testLoginSuccess() {
        LoginInputData inputData = new LoginInputData(employee.getUID(), "password");
        LoginOutputSpy mockPresenter = new LoginOutputSpy();
        LoginInteractor loginInteractor = new LoginInteractor(mockPresenter, employeeDAO);
        loginInteractor.execute(inputData);

        assertTrue(mockPresenter.isSuccessCalled());
        assertFalse(mockPresenter.isFailCalled());
    }

    private class LoginOutputSpy implements LoginOutputBoundary {
        private boolean isSuccessCalled = false;
        private boolean isFailCalled = false;

        public void prepareView(LoginOutputData outputData) {
            // Handle success scenario here if needed
            isSuccessCalled = true;
        }

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            isSuccessCalled = true;
        }

        @Override
        public void prepareFailView(String message) {
            isFailCalled = true;
        }

        public boolean isSuccessCalled() {
            return isSuccessCalled;
        }

        public boolean isFailCalled() {
            return isFailCalled;
        }
    }


}
