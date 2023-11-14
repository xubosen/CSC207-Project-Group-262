package data_access;
import entity.TeachingAssistant;
import entity.Instructor;
import entity.Course;

public class HardCodedDAO {
    private InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
    private InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();

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
    }

    public InMemoryEmployeeDataAccessObject getEmployeeDAO() {
        return employeeDAO;
    }

    public InMemoryCourseDataAccessObject getCourseDAO() {
        return courseDAO;
    }
}
