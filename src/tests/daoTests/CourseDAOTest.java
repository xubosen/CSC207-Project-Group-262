package daoTests;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CourseDAOTest {
    @Before
    public void setUp() throws IOException {
        Instructor alexander = new Instructor("phanale1231423", "Alexander Phan12",
                "alexander.phan@mail.utoronto.ca12", "123412");

        Course mat157 = new Course("Calculus", "MAT157", alexander);

        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("./employeeInformation.csv");

        // For some reason buffered reader can't read the file.
        FileCourseDataAccessObject fileCourseDataAccessObject = new FileCourseDataAccessObject("./courseInformation.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()));
//        // Updates current alexander to have MAT157 as one of their courses.
//        employeeDataAccessObject.save(alexander);
//
//        // Updates courses to have MAT157 included.
//        fileCourseDataAccessObject.save(mat157);
    }

    @Test
    public void TestWritingAndReading() throws IOException {
        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("./employeeInformation.csv");
        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        FileCourseDataAccessObject courseDataAccessObject = new FileCourseDataAccessObject("./courseInformation.csv",
                inMemoryEmployeeDataAccessObject);
        InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject = new InMemoryCourseDataAccessObject(courseDataAccessObject.getCourses());

        HashMap<String, String> courses = new HashMap<>();
        courses.put("MAT157", "");
        assertEquals(courses.keySet(), inMemoryCourseDataAccessObject.getAllIDs());
        // This test worked and didn't create duplicates of the documents either
    }
}
