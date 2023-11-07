package entity;

/**
 * Represents a tutorial event.
 * A tutorial event is a type of event with a name, event ID, associated course,
 * sessions, and a list of staff members.
 */
public class Tutorial extends Event {
    /**
     * Constructs a new Tutorial object with the given name, event ID, and course.
     *
     * @param name     The name of the tutorial event.
     * @param eventId  The unique identifier for the tutorial event.
     * @param course   The course associated with the tutorial event.
     */
    public Tutorial(String name, String eventId, Course course) {
        super(name, eventId, course);
    }
}