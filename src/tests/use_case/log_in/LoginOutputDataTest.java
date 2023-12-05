package tests.use_case.log_in;

import org.junit.Before;
import org.junit.Test;
import use_case.log_in.LoginOutputData;

import static org.junit.Assert.*;

public class LoginOutputDataTest {

    private LoginOutputData successfulLoginOutputData;
    private LoginOutputData failedLoginOutputData;

    @Before
    public void setUp() {
        successfulLoginOutputData = new LoginOutputData("user123", "Instructor", true);
        failedLoginOutputData = new LoginOutputData("user123", "Instructor", false);
    }

    @Test
    public void testGetUsername() {
        assertEquals("user123", successfulLoginOutputData.getUsername());
        assertEquals("user123", failedLoginOutputData.getUsername());
    }

    @Test
    public void testGetType() {
        assertEquals("Instructor", successfulLoginOutputData.getType());
        assertEquals("Instructor", failedLoginOutputData.getType());
    }

    @Test
    public void testLoginSuccessful() {
        assertTrue(successfulLoginOutputData.isLoginSuccessful());
        assertFalse(failedLoginOutputData.isLoginSuccessful());
    }
}
