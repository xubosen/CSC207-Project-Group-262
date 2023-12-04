package tests.use_case.sign_up;

import entity.Employee;
import org.junit.Before;
import org.junit.Test;
import use_case.sign_up.SignupInputData;
import use_case.sign_up.SignupInteractor;
import use_case.sign_up.SignupOutputBoundary;
import use_case.sign_up.SignupOutputData;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;

import static org.junit.Assert.*;

public class SignupInteractorTest {
    private InMemoryEmployeeDataAccessObject employeeDAO;

    @Before
    public void setUp() {
        employeeDAO = new InMemoryEmployeeDataAccessObject();
        Employee employee = new Employee("user123", "User Name", "user@example.com", "password");
        employeeDAO.addEmployee(employee);
    }

    @Test
    public void testSignUpFalse() {
        SignupInputData inputData = new SignupInputData("newUser", "New User", "newuser@example.com", "password", "Instructor");
        SignupOutputSpy mockPresenter = new SignupOutputSpy();
        SignupInteractor signupInteractor = new SignupInteractor(mockPresenter, employeeDAO);
        signupInteractor.signUp(inputData);

        assertFalse(mockPresenter.isSignupSuccessful());
    }

    private static class SignupOutputSpy implements SignupOutputBoundary {
        private boolean signupSuccessful = false;

        @Override
        public void prepareView(SignupOutputData outputData) {
            if (outputData.isSuccessful()) {
                signupSuccessful = true;
            }
        }
        
        public void prepareSuccessView() {
            signupSuccessful = true;
        }
        

        public boolean isSignupSuccessful() {
            return signupSuccessful;
        }
    }
}
