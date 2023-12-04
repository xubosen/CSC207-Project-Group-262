package tests.use_case.get_events;

import org.junit.Before;
import org.junit.Test;
import use_case.get_events.GetEventOutputData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GetEventOutputDataTest {

    private GetEventOutputData outputData;

    @Before
    public void setUp() {
        // Correctly initializing the list of event IDs
        ArrayList<String> eventIDs = new ArrayList<>(Arrays.asList("EVENT123"));
        outputData = new GetEventOutputData(eventIDs);
    }

    @Test
    public void testGetEventIDs() {
        // Test to ensure the list contains the correct event ID
        assertTrue("List should contain EVENT123", outputData.getEventIDs().contains("EVENT123"));
        assertEquals("List should have one element", 1, outputData.getEventIDs().size());
    }
}
