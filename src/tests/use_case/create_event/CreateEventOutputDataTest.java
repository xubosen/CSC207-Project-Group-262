package tests.use_case.create_event;

import org.junit.Before;
import org.junit.Test;
import use_case.create_event.CreateEventOutputData;

import static org.junit.Assert.*;

public class CreateEventOutputDataTest {

    private CreateEventOutputData outputData;

    @Before
    public void setUp() {
        outputData = new CreateEventOutputData(true, "Event creation successful");
    }

    @Test
    public void testIsSuccessful() {
        assertTrue(outputData.isSuccessful());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Event creation successful", outputData.getMessage());
    }
}
