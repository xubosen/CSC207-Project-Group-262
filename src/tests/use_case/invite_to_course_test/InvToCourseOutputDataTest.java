package tests.use_case.invite_to_course_test;

// Use case imports
import use_case.enroll.EnrollOutputData;

// Junit imports
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;
public class InvToCourseOutputDataTest {

    @Test
    public void testOutputDataReadWrite() {
        EnrollOutputData outputData = new EnrollOutputData(true, "true message");

        assertTrue(outputData.isSuccessful());
        assertEquals("true message", outputData.getMessage());

        outputData = new EnrollOutputData(false, "false message");
        assertFalse(outputData.isSuccessful());
        assertEquals("false message", outputData.getMessage());
    }
}
