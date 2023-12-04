package tests.entity;

// Testing imports

import entity.CalendarEvent;
import entity.DateTimeSpan;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CalendarEventTest {
    private CalendarEvent myEvent;

    @Before
    public void init() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 11, 10, 7, 57);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 11, 10, 8, 57);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(startDateTime, endDateTime);
        myEvent = new CalendarEvent("Write Tests for Entities",
                "Need to write tests for entities", dateTimeSpan);
    }

    @Test
    public void testGetName() {
        assertEquals("Write Tests for Entities", myEvent.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Need to write tests for entities", myEvent.getDescription());
    }

    @Test
    public void testChangeName() {
        assertEquals("Need to write tests for entities", myEvent.getDescription());
        myEvent.changeName("Write Tests for Use Cases");
        assertEquals("Write Tests for Use Cases", myEvent.getName());
    }

    @Test
    public void testChangeDescription() {
        assertEquals("Need to write tests for entities", myEvent.getDescription());
        myEvent.changeDescription("Need to write tests for use cases");
        assertEquals("Need to write tests for use cases", myEvent.getDescription());
    }

    @Test
    public void testGetDateTimeSpan() {
        LocalDateTime startDateTime = LocalDateTime.of(2023, 11, 10, 7, 57);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 11, 10, 8, 57);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(startDateTime, endDateTime);
        assertTrue(myEvent.getDateTimeSpan().equals(dateTimeSpan));
    }

    @Test
    public void testClashesWithOverlappingEvents() {
        LocalDateTime time1 = LocalDateTime.of(2023, 11, 10, 7, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 11, 10, 8, 0);
        LocalDateTime time3 = LocalDateTime.of(2023, 11, 10, 9, 0);
        LocalDateTime time4 = LocalDateTime.of(2023, 11, 10, 10, 0);

        CalendarEvent event1 = new CalendarEvent("Write Tests for Entities",
                "Need to write tests for entities", new DateTimeSpan(time1, time3));
        CalendarEvent event2 = new CalendarEvent("Write Tests for Usecases",
                "Need to write tests for usecases", new DateTimeSpan(time2, time4));

        assertTrue(event1.clashesWith(event2));
        assertTrue(event2.clashesWith(event1));
    }

    @Test
    public void testClashesWithContainedEvents() {
        LocalDateTime time1 = LocalDateTime.of(2023, 11, 10, 7, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 11, 10, 8, 0);
        LocalDateTime time3 = LocalDateTime.of(2023, 11, 10, 9, 0);
        LocalDateTime time4 = LocalDateTime.of(2023, 11, 10, 10, 0);

        CalendarEvent event1 = new CalendarEvent("Write Tests for Entities",
                "Need to write tests for entities", new DateTimeSpan(time1, time4));
        CalendarEvent event2 = new CalendarEvent("Write Tests for Usecases",
                "Need to write tests for usecases", new DateTimeSpan(time2, time3));

        assertTrue(event1.clashesWith(event2));
        assertTrue(event2.clashesWith(event1));
    }

    @Test
    public void testClashesWithNoClash() {
        LocalDateTime time1 = LocalDateTime.of(2023, 11, 10, 6, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 11, 10, 6, 30);

        CalendarEvent event1 = new CalendarEvent("Dinner",
                "Eat.", new DateTimeSpan(time1, time2));

        assertFalse(myEvent.clashesWith(event1));
    }
}
