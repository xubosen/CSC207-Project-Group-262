package entity;

import java.util.HashMap;

public class CourseFactory {
    public Course create(String name, String courseCode, HashMap<String, Employee> staff,
                             HashMap<String, Event> events, Employee admin) {
        Course course = new Course(name, courseCode, admin);

        for (HashMap.Entry<String, Event> entry : events.entrySet()) {

            Event value = entry.getValue();
            course.addEvent(value);
        }

        for (HashMap.Entry<String, Employee> employees : staff.entrySet()) {

            Employee value = employees.getValue();
            course.addStaff(value);
        }

        return course;
    }
}
