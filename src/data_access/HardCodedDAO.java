package data_access;
import entity.*;

import java.time.LocalDateTime;

public class HardCodedDAO {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();
    private InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject();

    public HardCodedDAO() {
        // Create employees
        Instructor employee1 = new Instructor("001", "John", "John@gmail.com", "123");
        TeachingAssistant employee2 = new TeachingAssistant("002", "Jane", "Jane@gmail.com", "234");
        Instructor employee3 = new Instructor("003", "Jack", "Jack@gmail.com", "345");
        TeachingAssistant employee4 = new TeachingAssistant("004", "Jill", "Jill@gmail.com", "456");

        employeeDAO.addEmployee(employee1);
        employeeDAO.addEmployee(employee2);
        employeeDAO.addEmployee(employee3);
        employeeDAO.addEmployee(employee4);


        // Create courses
        Course course1 = new Course("Software Design", "CSC207", employee1);
        Course course2 = new Course("Underwater Basket Weaving", "UBW2000", employee2);

        employee1.addCourse(course2);
        employee2.addCourse(course1);

        courseDAO.addCourse(course1);
        courseDAO.addCourse(course2);


        // Create events
        Lecture event1 = new Lecture("Lecture0101", "001", course1);
        Tutorial event2 = new Tutorial("Tutorial0101", "002", course1);
        course1.addEvent(event1);
        course1.addEvent(event2);

        Lecture event3 = new Lecture("Lecture0102", "003", course2);
        Tutorial event4 = new Tutorial("Tutorial0102", "004", course2);
        course2.addEvent(event3);
        course2.addEvent(event4);

        eventDAO.addEvent(event1);
        eventDAO.addEvent(event2);
        eventDAO.addEvent(event3);
        eventDAO.addEvent(event4);

        //Add employees to events
        event1.addStaff(employee1);


        // Create sessions
        LocalDateTime start = LocalDateTime.of(2023, 11, 13, 19, 0);
        LocalDateTime end = LocalDateTime.of(2023, 11, 13, 21, 0);
        DateTimeSpan session1DateTimeSpan = new DateTimeSpan(start, end);
        CalendarEvent session1CalEvent = new CalendarEvent("TUT0101-Nov13", "A Tutorial", session1DateTimeSpan);
        ClassSession session1 = new ClassSession("001", "TUT0101-Nov13", session1CalEvent, "BA10000", event1);
        event1.addSession(session1);

        start = LocalDateTime.of(2023, 11, 20, 19, 0);
        end = LocalDateTime.of(2023, 11, 20, 21, 0);
        DateTimeSpan session2DateTimeSpan = new DateTimeSpan(start, end);
        CalendarEvent session2CalEvent = new CalendarEvent("TUT0101-Nov20", "Another Tutorial", session2DateTimeSpan);
        ClassSession session2 = new ClassSession("002", "TUT0101-Nov20", session2CalEvent, "BA10000", event1);
        event2.addSession(session2);

        sessionDAO.addSession(session1);
        sessionDAO.addSession(session2);


        // Add employees to sessions
        System.out.println(session1.addStaff(employee1));
        System.out.println(employee1.addSession(session1));
        System.out.println(session1.containsStaff(employee1));
    }

    public InMemoryEmployeeDataAccessObject getEmployeeDAO() {
        return employeeDAO;
    }

    public InMemoryCourseDataAccessObject getCourseDAO() {
        return courseDAO;
    }

    public InMemoryEventDataAccessObject getEventDAO() {
        return eventDAO;
    }

    public InMemorySessionDataAccessObject getSessionDAO() {
        return sessionDAO;
    }
}
