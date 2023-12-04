package tests.use_case.get_events;

import org.junit.Before;
import org.junit.Test;
import use_case.get_events.GetEventInputData;

import static org.junit.Assert.*;

public class GetEventInputDataTest {

    private GetEventInputData inputData;

    @Before
    public void setUp() {
        // Assuming GetEventInputData requires an event ID as input
        inputData = new GetEventInputData("EVENT123");
    }

    @Test
    public void testGetUserId() {
        // Test to ensure the correct event ID is retrieved
        assertEquals("EVENT123", inputData.getUserID());
    }
}
