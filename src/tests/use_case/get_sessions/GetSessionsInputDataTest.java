package tests.use_case.get_sessions;

import org.junit.Before;
import org.junit.Test;
import use_case.get_sessions.GetSessionsInputData;

import static org.junit.Assert.assertEquals;

public class GetSessionsInputDataTest {

    private GetSessionsInputData inputData;

    @Before
    public void setUp() {
        inputData = new GetSessionsInputData("employee123");
    }

    @Test
    public void testGetEmployeeId() {
        assertEquals("employee123", inputData.getEmployeeId());
    }
}
