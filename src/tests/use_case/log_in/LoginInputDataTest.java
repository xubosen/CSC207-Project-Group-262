package tests.use_case.log_in;

import org.junit.Before;
import org.junit.Test;
import use_case.log_in.LoginInputData;

import static org.junit.Assert.assertEquals;

public class LoginInputDataTest {

    private LoginInputData loginInputData;

    @Before
    public void setUp() {
        loginInputData = new LoginInputData("username123", "password123");
    }

    @Test
    public void testGetUsername() {
        assertEquals("username123", loginInputData.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", loginInputData.getPassword());
    }
}
