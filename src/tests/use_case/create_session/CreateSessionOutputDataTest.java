package tests.use_case.create_session;

import org.junit.Before;
import org.junit.Test;
import use_case.create_session.CreateSessionOutputData;

import static org.junit.Assert.*;

public class CreateSessionOutputDataTest {

    private CreateSessionOutputData outputData;

    @Before
    public void setUp() {
        outputData = new CreateSessionOutputData(true, "Session creation successful");
    }

    @Test
    public void testIsSuccessful() {
        assertTrue(outputData.isSuccessful());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Session creation successful", outputData.getMessage());
    }
}
