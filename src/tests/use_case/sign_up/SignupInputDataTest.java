package tests.use_case.sign_up;

import org.junit.Before;
import org.junit.Test;
import use_case.sign_up.SignupInputData;

import static org.junit.Assert.*;

public class SignupInputDataTest {

    private SignupInputData inputData;

    @Before
    public void setUp() {
        inputData = new SignupInputData("user123", "John Doe", "john.doe@example.com", "password123", "Instructor");
    }

    @Test
    public void testGetUserID() {
        assertEquals("user123", inputData.getUserID());
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", inputData.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", inputData.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", inputData.getPassword());
    }

    @Test
    public void testGetType() {
        assertEquals("Instructor", inputData.getType());
    }
}
