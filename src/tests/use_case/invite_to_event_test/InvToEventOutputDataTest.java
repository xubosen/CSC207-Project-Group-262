package tests.use_case.invite_to_event_test;

// Use case imports
import use_case.add_to_event.EventAdditionOutputData;

// Junit imports
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class InvToEventOutputDataTest {

        @Test
        public void testOutputDataReadWrite() {
            EventAdditionOutputData outputData = new EventAdditionOutputData(true, "true message");

            assertTrue(outputData.isSuccessful());
            assertEquals("true message", outputData.getMessage());

            outputData = new EventAdditionOutputData(false, "false message");
            assertFalse(outputData.isSuccessful());
            assertEquals("false message", outputData.getMessage());
        }
}
