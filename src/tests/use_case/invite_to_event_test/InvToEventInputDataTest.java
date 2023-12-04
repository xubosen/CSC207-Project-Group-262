package tests.use_case.invite_to_event_test;


import org.junit.Before;
import org.junit.Test;
import use_case.add_to_event.EventAdditionInputData;

import static org.junit.Assert.*;

public class InvToEventInputDataTest {

    @Test
    public void testInputDataReadWrite() {
        EventAdditionInputData inputData = new EventAdditionInputData("invitorID", "inviteeID",
                "eventID");

        assertEquals("invitorID", inputData.getUserID());
        assertEquals("inviteeID", inputData.getInviteeID());
        assertEquals("eventID", inputData.getEventID());
    }
}
