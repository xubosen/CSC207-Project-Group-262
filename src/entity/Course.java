package entity;

import java.util.Collection;

public interface Course {
    String getName();
    Collection getMyEvents();
    CourseAdmin getAdmin();
}