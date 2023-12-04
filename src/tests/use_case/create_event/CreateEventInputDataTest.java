package tests.use_case.create_event;

import org.junit.Before;
import org.junit.Test;
import use_case.create_event.CreateEventInputData;

import static org.junit.Assert.assertEquals;

public class CreateEventInputDataTest {

    private CreateEventInputData inputData;

    @Before
    public void setUp() {
        inputData = new CreateEventInputData("Conference 2023", "event123", "Conference", "creator456", "CS101");
    }

    @Test
    public void testGetEventName() {
        assertEquals("Conference 2023", inputData.getEventName());
    }

    @Test
    public void testGetEventID() {
        assertEquals("event123", inputData.getEventID());
    }

    @Test
    public void testGetTypeOfEvent() {
        assertEquals("Conference", inputData.getTypeOfEvent());
    }

    @Test
    public void testGetCreatorID() {
        assertEquals("creator456", inputData.getCreatorID());
    }

    @Test
    public void testGetCourseCode() {
        assertEquals("CS101", inputData.getCourseCode());
    }
}
