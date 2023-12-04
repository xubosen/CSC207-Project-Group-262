package tests.entity;

import entity.DateTimeSpan;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DateTimeSpanTest {

    @Test
    public void testDateTimeSpanConstructionAndGetters() {
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan = new DateTimeSpan(start, end);

        assertEquals(start, timeSpan.getStart());
        assertEquals(end, timeSpan.getEnd());
    }

    @Test
    public void testEquals() {
        LocalDateTime start = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 1, 11, 0);
        DateTimeSpan timeSpan1 = new DateTimeSpan(start, end);
        DateTimeSpan timeSpan2 = new DateTimeSpan(start, end);

        assertTrue(timeSpan1.equals(timeSpan2));
    }

    @Test
    public void testOverlaps() {
        LocalDateTime start1 = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, 12, 1, 11, 0);
        LocalDateTime start2 = LocalDateTime.of(2023, 12, 1, 10, 30);
        LocalDateTime end2 = LocalDateTime.of(2023, 12, 1, 11, 30);
        DateTimeSpan timeSpan1 = new DateTimeSpan(start1, end1);
        DateTimeSpan timeSpan2 = new DateTimeSpan(start2, end2);

        assertTrue(timeSpan1.overlaps(timeSpan2));
    }

    @Test
    public void testNonOverlappingSpans() {
        LocalDateTime start1 = LocalDateTime.of(2023, 12, 1, 9, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, 12, 1, 10, 0);
        LocalDateTime start2 = LocalDateTime.of(2023, 12, 1, 11, 0);
        LocalDateTime end2 = LocalDateTime.of(2023, 12, 1, 12, 0);
        DateTimeSpan timeSpan1 = new DateTimeSpan(start1, end1);
        DateTimeSpan timeSpan2 = new DateTimeSpan(start2, end2);

        assertFalse(timeSpan1.overlaps(timeSpan2));
    }
}
