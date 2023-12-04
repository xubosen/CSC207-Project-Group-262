package tests.use_case.add_to_event;

import org.junit.Before;
import org.junit.Test;
import use_case.add_to_event.EventAdditionInputData;

import static org.junit.Assert.assertEquals;

public class EventAdditionInputDataTest {

    private EventAdditionInputData inputData;

    @Before
    public void setUp() {
        inputData = new EventAdditionInputData("user123", "invitee456", "event789");
    }

    @Test
    public void testGetUserID() {
        assertEquals("user123", inputData.getUserID());
    }

    @Test
    public void testGetInviteeID() {
        assertEquals("invitee456", inputData.getInviteeID());
    }

    @Test
    public void testGetEventID() {
        assertEquals("event789", inputData.getEventID());
    }
}
