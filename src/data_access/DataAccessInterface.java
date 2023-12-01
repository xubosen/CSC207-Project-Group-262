package data_access;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;

public interface DataAccessInterface {
    public InMemoryCourseDataAccessObject getCourseDAO();
    public InMemoryEmployeeDataAccessObject getEmployeeDAO();
    public InMemoryEventDataAccessObject getEventDAO();
    public InMemorySessionDataAccessObject getSessionDAO();

}
