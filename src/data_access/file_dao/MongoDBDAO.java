package data_access.file_dao;

import data_access.DataAccessInterface;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;

import java.io.IOException;

public class MongoDBDAO implements DataAccessInterface {
    private final String COURSE_FILE_PATH = "src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv";
    private final String EMPLOYEE_FILE_PATH = "src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv";
    private final String EVENT_FILE_PATH = "src/data_access/file_dao/mongoDBFilePaths/eventInfo.csv";
    private final String SESSION_FILE_PATH = "src/data_access/file_dao/mongoDBFilePaths/sessionInfo.csv";
    FileEmployeeDataAccessObject fileEmpDAO;
    FileCourseDataAccessObject fileCourseDAO;

    FileEventDataAccessObject fileEventDAO;
    FileSessionDataAccessObject fileSessionDAO;

    private InMemoryEmployeeDataAccessObject employeeDAO;
    private InMemoryCourseDataAccessObject courseDAO;
    private InMemoryEventDataAccessObject eventDAO;
    private InMemorySessionDataAccessObject sessionDAO;

    public MongoDBDAO() throws IOException {
        // Make EmployeeDAO
        fileEmpDAO = new FileEmployeeDataAccessObject(EMPLOYEE_FILE_PATH);
        employeeDAO = new InMemoryEmployeeDataAccessObject(fileEmpDAO.getAccount());

        // Make CourseDAO
        fileCourseDAO = new FileCourseDataAccessObject(COURSE_FILE_PATH, employeeDAO);
        courseDAO = new InMemoryCourseDataAccessObject(fileCourseDAO.getCourses());

        // Make EventDAO
        fileEventDAO = new FileEventDataAccessObject(EVENT_FILE_PATH, employeeDAO, courseDAO);
        eventDAO = new InMemoryEventDataAccessObject(fileEventDAO.getEvents());

        // Make SessionDAO
        fileSessionDAO = new FileSessionDataAccessObject(SESSION_FILE_PATH, employeeDAO, eventDAO);
        sessionDAO = new InMemorySessionDataAccessObject(fileSessionDAO.getSessions());
    }

    @Override
    public InMemoryCourseDataAccessObject getCourseDAO() {
        return courseDAO;
    }

    @Override
    public InMemoryEmployeeDataAccessObject getEmployeeDAO() {
        return employeeDAO;
    }

    @Override
    public InMemoryEventDataAccessObject getEventDAO() {
        return eventDAO;
    }

    @Override
    public InMemorySessionDataAccessObject getSessionDAO() {
        return sessionDAO;
    }

    @Override
    public boolean saveToDatabase(InMemoryCourseDataAccessObject courseDAO, InMemoryEmployeeDataAccessObject employeeDAO,
                                  InMemoryEventDataAccessObject eventDAO, InMemorySessionDataAccessObject sessionDAO) throws IOException {
        this.courseDAO = courseDAO;
        this.employeeDAO = employeeDAO;
        this.eventDAO = eventDAO;
        this.sessionDAO = sessionDAO;

        boolean empSaveSuccess = fileEmpDAO.save(employeeDAO.getEmployees());
        boolean courseSaveSuccess = fileCourseDAO.save(courseDAO.getCourses());
        boolean eventSaveSuccess = fileEventDAO.save(eventDAO.getEvents());
        boolean sessionSaveSuccess = fileSessionDAO.save(sessionDAO.getSessions());

        return empSaveSuccess && courseSaveSuccess && eventSaveSuccess && sessionSaveSuccess;
    }
}
