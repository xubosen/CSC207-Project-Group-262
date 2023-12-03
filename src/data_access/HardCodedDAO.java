package data_access;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HardCodedDAO implements DataAccessInterface{
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject();

    public HardCodedDAO() {

        // Create some employees
        employeeDAO.addEmployee(new Instructor("simon", "simon", "s@gmail.com", "123"));
        employeeDAO.addEmployee(new Instructor("alex", "alex", "a@gmail.com", "1234"));
        employeeDAO.addEmployee(new Instructor("dan", "dan", "d@gmail.com", "12345"));
        employeeDAO.addEmployee(new Instructor("yoo", "yoo", "y@gmai.com", "123456"));
        employeeDAO.addEmployee(new TeachingAssistant("ta1", "ta1", "t1@g.com", "1234567"));
        employeeDAO.addEmployee(new TeachingAssistant("ta2", "ta2", "t2@g.com", "12345678"));
        employeeDAO.addEmployee(new TeachingAssistant("ta3", "ta3", "t3@g.com", "123456789"));
        employeeDAO.addEmployee(new TeachingAssistant("ta4", "ta4", "t4@g.com", "1234567890"));

        Instructor simon = (Instructor) employeeDAO.getByID("simon");
        Instructor alex = (Instructor) employeeDAO.getByID("alex");
        Instructor dan = (Instructor) employeeDAO.getByID("dan");
        Instructor yoo = (Instructor) employeeDAO.getByID("yoo");
        TeachingAssistant ta1 = (TeachingAssistant) employeeDAO.getByID("ta1");
        TeachingAssistant ta2 = (TeachingAssistant) employeeDAO.getByID("ta2");
        TeachingAssistant ta3 = (TeachingAssistant) employeeDAO.getByID("ta3");
        TeachingAssistant ta4 = (TeachingAssistant) employeeDAO.getByID("ta4");

        // Create some courses & add some employees to them
        courseDAO.addCourse(new Course("Basket Weaving", "BW1000", simon));
        courseDAO.addCourse(new Course("Software Design", "CSC207", alex));
        courseDAO.addCourse(new Course("Analysis I", "MAT157", dan));
        courseDAO.addCourse(new Course("Analysis II", "MAT257", yoo));

        Course basketWeaving = courseDAO.getByID("BW1000");
        Course csc207 = courseDAO.getByID("CSC207");
        Course mat157 = courseDAO.getByID("MAT157");
        Course mat257 = courseDAO.getByID("MAT257");

        csc207.addStaff(simon);
        csc207.addStaff(ta1);
        csc207.addStaff(ta2);
        csc207.addStaff(ta3);

        mat157.addStaff(simon);
        mat157.addStaff(ta1);
        mat157.addStaff(ta2);
        mat157.addStaff(ta3);
        mat157.addStaff(ta4);

        // Create some events & add some employees to them
        eventDAO.addEvent(new Lecture("CSC207 - Lecture Session 1", "CSC207-LEC0101", csc207));
        eventDAO.addEvent(new Lecture("CSC207 - Lecture Session 2", "CSC207-LEC0201", csc207));
        eventDAO.addEvent(new Tutorial("CSC207 - Tutorial Session 1", "CSC207-TUT0101", csc207));
        eventDAO.addEvent(new Tutorial("CSC207 - Tutorial Session 2", "CSC207-TUT0201", csc207));

        Lecture LS1 = (Lecture) eventDAO.getByID("CSC207-LEC0101");
        Lecture LS2 = (Lecture) eventDAO.getByID("CSC207-LEC0201");
        Tutorial TS1 = (Tutorial) eventDAO.getByID("CSC207-TUT0101");
        Tutorial TS2 = (Tutorial) eventDAO.getByID("CSC207-TUT0201");

        LS1.addStaff(simon);

        LS2.addStaff(alex);

        TS1.addStaff(simon);
        TS1.addStaff(ta1);

        TS2.addStaff(ta2);

        // Create some sessions & add some employees to them
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 1, 13, 0);
        DateTimeSpan dateTimeSpan = new DateTimeSpan(start, end);
        CalendarEvent calendarEvent = new CalendarEvent("CSC207 - Lecture Session 1", "CSC207-LEC0101",
                dateTimeSpan);
        ClassSession session1 = new ClassSession("Test", "Test Session",
                calendarEvent, "here",
                LS1);
        sessionDAO.addSession(session1);

        session1.addStaff(simon);
    }

    public InMemoryEmployeeDataAccessObject getEmployeeDAO() {
        return employeeDAO;
    }

    public InMemoryEventDataAccessObject getEventDAO() {
        return eventDAO;
    }

    public InMemoryCourseDataAccessObject getCourseDAO() {
        return courseDAO;
    }

    public InMemorySessionDataAccessObject getSessionDAO() {
        return sessionDAO;
    }

    @Override
    public boolean saveToDatabase(InMemoryCourseDataAccessObject courseDAO,
                                  InMemoryEmployeeDataAccessObject employeeDAO,
                                  InMemoryEventDataAccessObject eventDAO,
                                  InMemorySessionDataAccessObject sessionDAO) {
        System.out.println("Saving unsuccessful!");
        return false;
    }
}
