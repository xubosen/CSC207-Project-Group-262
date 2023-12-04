package tests.use_case.add_to_event;

import org.junit.Before;
import org.junit.Test;
import use_case.add_to_event.EventAdditionOutputData;

import static org.junit.Assert.*;

public class EventAdditionOutputDataTest {

    private EventAdditionOutputData outputData;

    @Before
    public void setUp() {
        // Example initialization with boolean and string values
        outputData = new EventAdditionOutputData(true, "Event added successfully");
    }

    @Test
    public void testIsSuccessful() {
        // Testing if the isSuccessful method returns the correct boolean value
        assertTrue(outputData.isSuccessful());
    }

    @Test
    public void testGetMessage() {
        // Testing if the getMessage method returns the correct string
        assertEquals("Event added successfully", outputData.getMessage());
    }
}
