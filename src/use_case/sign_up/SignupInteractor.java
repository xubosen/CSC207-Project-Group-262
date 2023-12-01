package use_case.sign_up;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Instructor;
import entity.TeachingAssistant;

public class SignupInteractor implements SignupInputBoundary{
    private SignupOutputBoundary presenter;
    private InMemoryEmployeeDataAccessObject employeeDAO;

    public SignupInteractor(SignupOutputBoundary presenter, InMemoryEmployeeDataAccessObject employeeDAO) {
        this.presenter = presenter;
        this.employeeDAO = employeeDAO;
    }

    public void signUp(SignupInputData inputData) {
        SignupOutputData outputData;
        String userID = inputData.getUserID();
        String name = inputData.getName();
        String email = inputData.getEmail();
        String password = inputData.getPassword();

        if (employeeDAO.existsByID(inputData.getUserID())) {
            outputData = new SignupOutputData(false, "User ID already exists");
        } else {
            System.out.println(inputData.getType());
            if (inputData.getType().equals("instructor")) {
                Instructor newInstructor = new Instructor(userID, name, email, password);
                employeeDAO.addEmployee(newInstructor);
                outputData = new SignupOutputData(true, "Signup successful");
            } else if (inputData.getType().equals("ta")) {
                TeachingAssistant newTA = new TeachingAssistant(userID, name, email, password);
                employeeDAO.addEmployee(newTA);
                outputData = new SignupOutputData(true, "Signup successful");
            } else {
                outputData = new SignupOutputData(false, "Role not found");
            }
        }
        presenter.prepareView(outputData);
    }
}
