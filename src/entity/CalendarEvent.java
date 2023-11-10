package entity;

/**
 * A class that represents a calendar evet, with a name, description, and time span.
 */
public class CalendarEvent {
    private String name;
    private String description;
    private DateTimeSpan dateTimeSpan;

    public CalendarEvent(String name, String description, DateTimeSpan dateTimeSpan) {
        this.name = name;
        this.description = description;
        this.dateTimeSpan = dateTimeSpan;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public DateTimeSpan getDateTimeSpan() {
        // Notice that DateTimeSpan is an immutable class, so we can return it directly.
        return this.dateTimeSpan;
    }

    /** Checks if this calendar event overlaps with another calendar event.
     * @param other The other calendar event to check against
     * @return true if the two calendar events overlap, false otherwise
     */
    public boolean clashesWith(CalendarEvent other) {
        return this.dateTimeSpan.overlaps(other.dateTimeSpan);
    }
}