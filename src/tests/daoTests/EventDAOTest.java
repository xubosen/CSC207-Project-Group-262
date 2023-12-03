package daoTests;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.Course;
import entity.Instructor;
import entity.Lecture;
import entity.Tutorial;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class EventDAOTest {
    @Before
    public void setUp() throws IOException {
        Instructor alexander = new Instructor("phanale1231423", "Alexander Phan12",
                "alexander.phan@mail.utoronto.ca12", "123412");

        Course mat157 = new Course("Calculus", "MAT157", alexander);

        Lecture lec0101 = new Lecture("Math LEC0101", "LEC0101", mat157);

        Tutorial tut5201 = new Tutorial("Math TUT5201", "TUT5201", mat157);

        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("./employeeInformation.csv");

        FileCourseDataAccessObject fileCourseDataAccessObject = new FileCourseDataAccessObject("./courseInformation.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()));

        // Create the file event DAO with the new lecture and tutorial.
        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject("./eventInformation.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()),
                new InMemoryCourseDataAccessObject(fileCourseDataAccessObject.getCourses()));

//        // Updates current alexander to have MAT157 as one of their courses.
//        employeeDataAccessObject.save(alexander);
//
//        // Updates courses to have MAT157 included.
//        fileCourseDataAccessObject.save(mat157);
//
//        // Updates events to have one lecture and one tutorial.
//        fileEventDAO.save(lec0101);
//        fileEventDAO.save(tut5201);
    }

    @Test
    public void TestWritingAndReading() throws IOException {
        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("./employeeInformation.csv");
        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        FileCourseDataAccessObject courseDataAccessObject = new FileCourseDataAccessObject("./courseInformation.csv",
                inMemoryEmployeeDataAccessObject);
        InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject = new InMemoryCourseDataAccessObject(courseDataAccessObject.getCourses());

        FileEventDataAccessObject fileEventDataAccessObject = new FileEventDataAccessObject("./eventInformation.csv",
                inMemoryEmployeeDataAccessObject, inMemoryCourseDataAccessObject);
        InMemoryEventDataAccessObject eventDAO = new InMemoryEventDataAccessObject(fileEventDataAccessObject.getEvents());

        HashMap<String, String> events = new HashMap<>();
        events.put("LEC0101", "blah");
        events.put("TUT5201", "blah");
        assertEquals(events.keySet(), eventDAO.getAllIDs());
        // This test worked and didn't create duplicates of the documents either
    }
}
