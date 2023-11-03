package entity;
import java.util.Collection;

public class Lecture implements Event {
    private Collection<Session> mySessions;
    private String name;
    private String course;

    public Lecture(Collection<Session> mySessions, String name, String course) {
        this.mySessions = mySessions;
        this.name = name;
        this.course = course;
    }

    @Override
    public Collection<Session> getMySessions() {
        return mySessions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCourse() {
        return course;
    }
}
