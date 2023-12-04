package tests.use_case.get_sessions;

import org.junit.Before;
import org.junit.Test;
import use_case.get_sessions.GetSessionsOutputData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetSessionsOutputDataTest {

    private GetSessionsOutputData outputData;
    private ArrayList<String> sessionList;

    @Before
    public void setUp() {
        sessionList = new ArrayList<>(Arrays.asList("session1", "session2", "session3"));
        outputData = new GetSessionsOutputData(sessionList);
    }

    @Test
    public void testGetSessions() {
        assertEquals(sessionList, outputData.getSessions());
        assertTrue(outputData.getSessions().contains("session1"));
        assertTrue(outputData.getSessions().contains("session2"));
        assertTrue(outputData.getSessions().contains("session3"));
    }
}
