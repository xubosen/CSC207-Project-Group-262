package data_access;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.Employee;

import java.io.IOException;

public interface DataAccessInterface {
    public InMemoryCourseDataAccessObject getCourseDAO();
    public InMemoryEmployeeDataAccessObject getEmployeeDAO();
    public InMemoryEventDataAccessObject getEventDAO();
    public InMemorySessionDataAccessObject getSessionDAO();

    public boolean saveToDatabase(InMemoryCourseDataAccessObject courseDAO,
                                  InMemoryEmployeeDataAccessObject employeeDAO,
                                  InMemoryEventDataAccessObject eventDAO,
                                  InMemorySessionDataAccessObject sessionDAO) throws IOException;

}
