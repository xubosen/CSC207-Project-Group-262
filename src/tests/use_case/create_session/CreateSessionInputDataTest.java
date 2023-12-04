package tests.use_case.create_session;

import org.junit.Before;
import org.junit.Test;
import use_case.create_session.CreateSessionInputData;
import entity.DateTimeSpan;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class CreateSessionInputDataTest {

    private CreateSessionInputData inputData;
    private LocalDateTime testStartTime;
    private LocalDateTime testEndTime;

    @Before
    public void setUp() {
        testStartTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        testEndTime = LocalDateTime.of(2023, 1, 1, 12, 0);
        inputData = new CreateSessionInputData("user123", "session456", "Workshop on Java", "A session about Java programming", "Conference Room 1", testStartTime, testEndTime, "event789");
    }

    @Test
    public void testGetUserID() {
        assertEquals("user123", inputData.getUserID());
    }

    @Test
    public void testGetSessionID() {
        assertEquals("session456", inputData.getSessionID());
    }

    @Test
    public void testGetSessionName() {
        assertEquals("Workshop on Java", inputData.getSessionName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A session about Java programming", inputData.getDescription());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Conference Room 1", inputData.getLocation());
    }

    @Test
    public void testGetDateTimeSpan() {
        DateTimeSpan expectedSpan = new DateTimeSpan(testStartTime, testEndTime);
        DateTimeSpan actualSpan = inputData.getDateTimeSpan();
        assertEquals(expectedSpan.getStart(), actualSpan.getStart());
        assertEquals(expectedSpan.getEnd(), actualSpan.getEnd());
    }

    @Test
    public void testGetParentEventID() {
        assertEquals("event789", inputData.getParentEventID());
    }
}
