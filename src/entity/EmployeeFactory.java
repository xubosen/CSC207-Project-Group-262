package entity;

import java.util.HashMap;
import java.util.Objects;

public class EmployeeFactory {
    public Employee create(String userID, String name, String email, String password,
                             HashMap<String, ClassSession> sessions, HashMap<String, Course> courses,
                             String employeeType) {
        Employee employee;

        if (Objects.equals(employeeType, "class entity.TeachingAssistant")) {
            employee = new TeachingAssistant(userID, name, email, password);
            for (ClassSession classSession : sessions.values()) {
                employee.addSession(classSession);
            }
            for (Course course : courses.values()) {
                employee.addCourse(course);
            }
        }
        else { // Currently only two types of employees
            employee = new Instructor(userID, name, email, password);
            for (ClassSession classSession : sessions.values()) {
                employee.addSession(classSession);
            }
            for (Course course : courses.values()) {
                employee.addCourse(course);
            }
        }

        employee.makeCalendar();

        return employee;
    }
}
