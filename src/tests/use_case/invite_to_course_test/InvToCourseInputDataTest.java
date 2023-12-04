package tests.use_case.invite_to_course_test;

import use_case.enroll.EnrollInputData;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class InvToCourseInputDataTest {
    private EnrollInputData inputData;
    @Before
    public void setUp() {
        inputData = new EnrollInputData("invitorID", "inviteeID",
                "courseID");
    }

    @Test
    public void testInputDataReadWrite() {

        assertEquals("invitorID", inputData.getInvitorID());
        assertEquals("inviteeID", inputData.getInviteeID());
        assertEquals("courseID", inputData.getCourseCode());
    }


}
