package tests.daoTests;

import data_access.file_dao.FileCourseDataAccessObject;
import data_access.file_dao.FileEmployeeDataAccessObject;
import data_access.file_dao.FileEventDataAccessObject;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class EventDAOTest {
    @Before
    public void setUp() throws IOException {
        // This part also tests whether the new Instructor is getting saved to the database.
        Instructor alexander = new Instructor("phanale1231423", "Alexander Phan12",
                "alexander.phan@mail.utoronto.ca12", "123412");

        Course mat157 = new Course("Calculus", "MAT157", alexander);

        Lecture lec0101 = new Lecture("Math LEC0101", "LEC0101", mat157);

        Tutorial tut5201 = new Tutorial("Math TUT5201", "TUT5201", mat157);

        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");
        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());
        HashMap<String, Employee> employees = new HashMap<>(employeeDataAccessObject.getAccount());
        employees.put(alexander.getUID(), alexander);


        FileCourseDataAccessObject fileCourseDataAccessObject = new FileCourseDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()));
        HashMap<String, Course> courses = new HashMap<>(fileCourseDataAccessObject.getCourses());
        courses.put(mat157.getCourseCode(), mat157);

        InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject = new InMemoryCourseDataAccessObject(fileCourseDataAccessObject.getCourses());

        // Create the file event DAO with the new lecture and tutorial.
        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/eventInfo.csv",
                inMemoryEmployeeDataAccessObject, inMemoryCourseDataAccessObject);
        HashMap<String, Event> events = new HashMap<>(fileEventDAO.getEvents());
        events.put(lec0101.getEventID(), lec0101);
        events.put(tut5201.getEventID(), tut5201);

        InMemoryEventDataAccessObject memEventDAO = new InMemoryEventDataAccessObject(fileEventDAO.getEvents());

        // Updates current alexander to have MAT157 as one of their courses.
        employeeDataAccessObject.save(inMemoryEmployeeDataAccessObject.getEmployees());

        // Updates courses to have MAT157 included.
        fileCourseDataAccessObject.save(inMemoryCourseDataAccessObject.getCourses());

        // Updates events to have one lecture and one tutorial.
        fileEventDAO.save(memEventDAO.getEvents());
    }

    @Test
    public void TestWritingAndReading() throws IOException {
        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");
        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        FileCourseDataAccessObject courseDataAccessObject = new FileCourseDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv",
                inMemoryEmployeeDataAccessObject);
        InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject = new InMemoryCourseDataAccessObject(courseDataAccessObject.getCourses());

        FileEventDataAccessObject fileEventDataAccessObject = new FileEventDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/eventInfo.csv",
                inMemoryEmployeeDataAccessObject, inMemoryCourseDataAccessObject);
        InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject(fileEventDataAccessObject.getEvents());

        HashMap<String, String> events = new HashMap<>();
        events.put("CSC207-LEC0101", "");
        events.put("TUT5201", "");
        events.put("BW101", "");
        events.put("LEC0101", "");
        assertEquals(events.keySet(), eventDAO.getAllIDs());
    }
}
