package tests.entity;

import entity.Calendar;
import entity.CalendarEvent;
import entity.DateTimeSpan;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CalendarTest {
    private Calendar calendar;
    private CalendarEvent calEvent1;
    private CalendarEvent calEvent2;
    private DateTimeSpan timeSpan1;
    private DateTimeSpan timeSpan2;

    @Before
    public void setUp() {
        calendar = new Calendar();
        LocalDateTime start1 = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, 12, 1, 11, 0);
        LocalDateTime start2 = LocalDateTime.of(2023, 12, 1, 11, 0);
        LocalDateTime end2 = LocalDateTime.of(2023, 12, 1, 12, 0);

        timeSpan1 = new DateTimeSpan(start1, end1);
        timeSpan2 = new DateTimeSpan(start2, end2);

        calEvent1 = new CalendarEvent("Event1", "Description1", timeSpan1);
        calEvent2 = new CalendarEvent("Event2", "Description2", timeSpan2);
    }

    @Test
    public void testAddEvent() {
        assertTrue(calendar.addEvent(calEvent1));
        assertTrue(calendar.addEvent(calEvent2)); // Should succeed as there is no overlap
    }

    @Test
    public void testAddEventWithOverlap() {
        // Create overlapping DateTimeSpans
        LocalDateTime start1 = LocalDateTime.of(2023, 12, 1, 9, 30);
        LocalDateTime end1 = LocalDateTime.of(2023, 12, 1, 10, 30);
        LocalDateTime start2 = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end2 = LocalDateTime.of(2023, 12, 1, 11, 0);

        DateTimeSpan overlappingSpan1 = new DateTimeSpan(start1, end1);
        DateTimeSpan overlappingSpan2 = new DateTimeSpan(start2, end2);

        CalendarEvent overlappingEvent1 = new CalendarEvent("OverlappingEvent1", "Description1", overlappingSpan1);
        CalendarEvent overlappingEvent2 = new CalendarEvent("OverlappingEvent2", "Description2", overlappingSpan2);

        assertTrue(calendar.addEvent(overlappingEvent1));
        assertFalse(calendar.addEvent(overlappingEvent2)); // Should fail due to overlap
    }

    @Test
    public void testRemoveEvent() {
        calendar.addEvent(calEvent1);
        assertTrue(calendar.removeEvent(calEvent1));
        assertFalse(calendar.removeEvent(calEvent2)); // Should fail as it was never added
    }

    @Test
    public void testListCalendarEvents() {
        calendar.addEvent(calEvent1);
        calendar.addEvent(calEvent2);
        ArrayList<CalendarEvent> events = calendar.listCalendarEvents();

        assertTrue(events.contains(calEvent1));
        assertTrue(events.contains(calEvent2));
        assertEquals(2, events.size());
    }
    @Test
    public void testGetName() {
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);
        CalendarEvent calEvent = new CalendarEvent("EventName", "Description", timeSpan);
        calendar.addEvent(calEvent);

        assertEquals("EventName", calEvent.getName());
    }

    @Test
    public void testGetDateTimeSpan() {
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);
        CalendarEvent calEvent = new CalendarEvent("EventName", "Description", timeSpan);

        assertEquals(start, calEvent.getDateTimeSpan().getStart());
        assertEquals(end, calEvent.getDateTimeSpan().getEnd());
    }
}