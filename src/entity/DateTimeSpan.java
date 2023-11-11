package entity;

import java.time.LocalDateTime;

/**
 * A class that represents a span of time between two dates with times.
 */
public final class DateTimeSpan {
    private LocalDateTime start;
    private LocalDateTime end;

    public DateTimeSpan(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start of this span.
     * @return the start of this span
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Returns the end of this span.
     * @return the end of this span
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns true if this span is equal to the given span.
     * @param other the span to compare to
     * @return true if this span is equal to the given span
     */
    public boolean equals(DateTimeSpan other) {
        return this.start.equals(other.getStart()) && this.end.equals(other.getEnd());
    }

    /**
     * Returns true if this span overlaps with the given span.
     * @param other the span to compare to
     * @return true if this span overlaps with the given span
     */
    public boolean overlaps(DateTimeSpan other) {
        return this.start.isBefore(other.getEnd()) && this.end.isAfter(other.getStart());
    }
}