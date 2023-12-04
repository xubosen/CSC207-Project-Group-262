package tests.use_case.invite_to_course_test;

import use_case.enroll.EnrollInputData;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class InvToCourseInputDataTest {

    @Test
    public void testInputDataReadWrite() {
        EnrollInputData inputData = new EnrollInputData("invitorID", "inviteeID",
                "courseID");

        assertEquals("invitorID", inputData.getInvitorID());
        assertEquals("inviteeID", inputData.getInviteeID());
        assertEquals("courseID", inputData.getCourseCode());
    }
}
