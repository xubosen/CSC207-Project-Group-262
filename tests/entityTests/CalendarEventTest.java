package entityTests;

// Testing imports
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Project imports
import entity.CalendarEvent;
import entity.DateTimeSpan;

// Java imports
import java.time.LocalDateTime;

public class CalendarEventTest {
    private CalendarEvent calendarEvent;

    @Before
    public void init() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 11, 10, 7, 57);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 11, 10, 8, 57);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(startDateTime, endDateTime);
        calendarEvent = new CalendarEvent("Write Tests for Entities",
                "Need to write tests for entities", dateTimeSpan);
    }

    @Test
    public void testGetName() {
        assertEquals("Write Tests for Entities", calendarEvent.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Need to write tests for entities", calendarEvent.getDescription());
    }

    @Test
    public void testChangeName() {
        assertEquals("Need to write tests for entities", calendarEvent.getDescription());
        calendarEvent.changeName("Write Tests for Use Cases");
        assertEquals("Write Tests for Use Cases", calendarEvent.getName());
    }

    @Test
    public void testChangeDescription() {
        assertEquals("Need to write tests for entities", calendarEvent.getDescription());
        calendarEvent.changeDescription("Need to write tests for use cases");
        assertEquals("Need to write tests for use cases", calendarEvent.getDescription());
    }

    @Test
    public void testGetDateTimeSpan() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 11, 10, 7, 57);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 11, 10, 8, 57);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(startDateTime, endDateTime);
        assert calendarEvent.getDateTimeSpan().equals(dateTimeSpan);
    }
}
