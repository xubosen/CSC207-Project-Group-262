package entity;
import java.util.Collection;

public interface Event {
    // Getter for mySessions
    Collection<Session> getMySessions();

    // Getter for name
    String getName();

    // Getter for course
    String getCourse();
}