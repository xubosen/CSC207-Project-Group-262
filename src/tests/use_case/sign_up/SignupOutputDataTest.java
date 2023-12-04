package tests.use_case.sign_up;

import org.junit.Before;
import org.junit.Test;
import use_case.sign_up.SignupOutputData;

import static org.junit.Assert.*;

public class SignupOutputDataTest {

    private SignupOutputData successOutputData;
    private SignupOutputData failureOutputData;

    @Before
    public void setUp() {
        successOutputData = new SignupOutputData(true, "Signup successful");
        failureOutputData = new SignupOutputData(false, "Signup failed");
    }

    @Test
    public void testIsSuccessfulForSuccess() {
        assertTrue(successOutputData.isSuccessful());
    }

    @Test
    public void testIsSuccessfulForFailure() {
        assertFalse(failureOutputData.isSuccessful());
    }

    @Test
    public void testGetMessageForSuccess() {
        assertEquals("Signup successful", successOutputData.getMessage());
    }

    @Test
    public void testGetMessageForFailure() {
        assertEquals("Signup failed", failureOutputData.getMessage());
    }
}
