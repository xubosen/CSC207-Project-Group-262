package entity;

/**
 * Represents a lecture event.
 * A lecture event is a type of event with a name, event ID, associated course,
 * sessions, and a list of staff members.
 */
public class Lecture extends Event {

    /**
     * Constructs a new Lecture object with the given name, event ID, and course.
     *
     * @param name     The name of the lecture event.
     * @param eventId  The unique identifier for the lecture event.
     * @param course   The course associated with the lecture event.
     */
    public Lecture(String name, String eventId, Course course) {
        super(name, eventId, course);
    }
}