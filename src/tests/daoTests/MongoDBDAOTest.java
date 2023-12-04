package tests.daoTests;

import com.mongodb.Mongo;
import data_access.file_dao.*;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import data_access.in_memory_dao.InMemorySessionDataAccessObject;
import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MongoDBDAOTest {
    private final String empPath = "src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv";
    private final String coursePath = "src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv";
    private final String eventPath = "src/data_access/file_dao/mongoDBFilePaths/eventInfo.csv";
    private final String sessionPath = "src/data_access/file_dao/mongoDBFilePaths/sessionInfo.csv";

    @Test
    public void TestGetEmployeeDAO() throws IOException {
        MongoDBDAO mongoDBDAO = new MongoDBDAO();

        FileEmployeeDataAccessObject fileEmpDAO = new FileEmployeeDataAccessObject(empPath);
        InMemoryEmployeeDataAccessObject memEmpDAO = new InMemoryEmployeeDataAccessObject(fileEmpDAO.getAccount());

        assertEquals(mongoDBDAO.getEmployeeDAO().getAllIDs(), memEmpDAO.getAllIDs());
    }

    @Test
    public void TestGetCourseDAO() throws IOException {
        MongoDBDAO mongoDBDAO = new MongoDBDAO();

        FileEmployeeDataAccessObject fileEmpDAO = new FileEmployeeDataAccessObject(empPath);
        InMemoryEmployeeDataAccessObject memEmpDAO = new InMemoryEmployeeDataAccessObject(fileEmpDAO.getAccount());

        FileCourseDataAccessObject fileCourseDAO = new FileCourseDataAccessObject(coursePath, memEmpDAO);
        InMemoryCourseDataAccessObject memCourseDAO = new InMemoryCourseDataAccessObject(fileCourseDAO.getCourses());

        assertEquals(mongoDBDAO.getCourseDAO().getAllIDs(), memCourseDAO.getAllIDs());
    }

    @Test
    public void TestGetEventDAO() throws IOException {
        MongoDBDAO mongoDBDAO = new MongoDBDAO();

        FileEmployeeDataAccessObject fileEmpDAO = new FileEmployeeDataAccessObject(empPath);
        InMemoryEmployeeDataAccessObject memEmpDAO = new InMemoryEmployeeDataAccessObject(fileEmpDAO.getAccount());

        FileCourseDataAccessObject fileCourseDAO = new FileCourseDataAccessObject(coursePath, memEmpDAO);
        InMemoryCourseDataAccessObject memCourseDAO = new InMemoryCourseDataAccessObject(fileCourseDAO.getCourses());

        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject(eventPath, memEmpDAO, memCourseDAO);
        InMemoryEventDataAccessObject memEventDAO = new InMemoryEventDataAccessObject(fileEventDAO.getEvents());

        assertEquals(mongoDBDAO.getEventDAO().getAllIDs(), memEventDAO.getAllIDs());
    }

    @Test
    public void TestGetSessionsDAO() throws IOException {
        MongoDBDAO mongoDBDAO = new MongoDBDAO();

        FileEmployeeDataAccessObject fileEmpDAO = new FileEmployeeDataAccessObject(empPath);
        InMemoryEmployeeDataAccessObject memEmpDAO = new InMemoryEmployeeDataAccessObject(fileEmpDAO.getAccount());

        FileCourseDataAccessObject fileCourseDAO = new FileCourseDataAccessObject(coursePath, memEmpDAO);
        InMemoryCourseDataAccessObject memCourseDAO = new InMemoryCourseDataAccessObject(fileCourseDAO.getCourses());

        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject(eventPath, memEmpDAO, memCourseDAO);
        InMemoryEventDataAccessObject memEventDAO = new InMemoryEventDataAccessObject(fileEventDAO.getEvents());

        FileSessionDataAccessObject fileSessionDAO = new FileSessionDataAccessObject(sessionPath,
                memEmpDAO, memEventDAO);
        InMemorySessionDataAccessObject memSessionDAO = new InMemorySessionDataAccessObject(fileSessionDAO.getSessions());

        assertEquals(mongoDBDAO.getSessionDAO().getAllIDs(), memSessionDAO.getAllIDs());
    }

    @Test
    public void TestSaveDatabase() throws IOException {
        MongoDBDAO mongoDBDAO = new MongoDBDAO();

        FileEmployeeDataAccessObject fileEmpDAO = new FileEmployeeDataAccessObject(empPath);
        InMemoryEmployeeDataAccessObject memEmpDAO = new InMemoryEmployeeDataAccessObject(fileEmpDAO.getAccount());

        FileCourseDataAccessObject fileCourseDAO = new FileCourseDataAccessObject(coursePath, memEmpDAO);
        InMemoryCourseDataAccessObject memCourseDAO = new InMemoryCourseDataAccessObject(fileCourseDAO.getCourses());

        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject(eventPath, memEmpDAO, memCourseDAO);
        InMemoryEventDataAccessObject memEventDAO = new InMemoryEventDataAccessObject(fileEventDAO.getEvents());

        FileSessionDataAccessObject fileSessionDAO = new FileSessionDataAccessObject(sessionPath,
                memEmpDAO, memEventDAO);
        InMemorySessionDataAccessObject memSessionDAO = new InMemorySessionDataAccessObject(fileSessionDAO.getSessions());

        assertEquals(mongoDBDAO.saveToDatabase(mongoDBDAO.getCourseDAO(), mongoDBDAO.getEmployeeDAO(),
                        mongoDBDAO.getEventDAO(), mongoDBDAO.getSessionDAO()),
                mongoDBDAO.saveToDatabase(memCourseDAO, memEmpDAO, memEventDAO, memSessionDAO));
    }
}
