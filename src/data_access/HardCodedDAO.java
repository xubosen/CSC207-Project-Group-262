package data_access;
import entity.*;

public class HardCodedDAO {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
    private InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject();

    public HardCodedDAO() {
        Instructor employee1 = new Instructor("001", "John", "John@gmail.com", "123");
        TeachingAssistant employee2 = new TeachingAssistant("002", "Jane", "Jane@gmail.com", "234");
        Instructor employee3 = new Instructor("003", "Jack", "Jack@gmail.com", "345");
        TeachingAssistant employee4 = new TeachingAssistant("004", "Jill", "Jill@gmail.com", "456");

        employeeDAO.addEmployee(employee1);
        employeeDAO.addEmployee(employee2);
        employeeDAO.addEmployee(employee3);
        employeeDAO.addEmployee(employee4);

        Course course1 = new Course("Software Design", "CSC207", employee1);
        Course course2 = new Course("Underwater Basket Weaving", "UBW2000", employee2);

        employee1.addCourse(course2);
        employee2.addCourse(course1);

        courseDAO.addCourse(course1);
        courseDAO.addCourse(course2);

        Lecture lecture1 = new Lecture("CSC207 Lecture", "LEC0101", course1);
        Tutorial tutorial1 = new Tutorial("UBW2000 Tutorial", "TUT0202", course2);

        course1.addEvent(lecture1);
        course2.addEvent(tutorial1);

        lecture1.addStaff(employee1);
        tutorial1.addStaff(employee2);

        eventDAO.save(lecture1);
        eventDAO.save(tutorial1);

    }

    public InMemoryEmployeeDataAccessObject getEmployeeDAO() {
        return employeeDAO;
    }

    public InMemoryCourseDataAccessObject getCourseDAO() {
        return courseDAO;
    }

    public InMemoryEventDataAccessObject getEventDAO() {return eventDAO;};
}
