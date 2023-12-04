package tests.daoTests;

import data_access.file_dao.FileCourseDataAccessObject;
import data_access.file_dao.FileEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.Course;
import entity.Instructor;
import entity.Employee;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CourseDAOTest {
    @Before
    public void setUp() throws IOException {
        Instructor alexander = new Instructor("phanale1231423", "Alexander Phan12",
                "alexander.phan@mail.utoronto.ca12", "123412");

        Course mat157 = new Course("Calculus", "MAT157", alexander);

        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");
        HashMap<String, Employee> employees = new HashMap<>(employeeDataAccessObject.getAccount());
        employees.put(alexander.getUID(), alexander);

        InMemoryEmployeeDataAccessObject memoryEmpDAO = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        FileCourseDataAccessObject fileCourseDataAccessObject = new FileCourseDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()));
        HashMap<String, Course> courses = new HashMap<>(fileCourseDataAccessObject.getCourses());
        courses.put(mat157.getCourseCode(), mat157);

        // Updates current alexander to have MAT157 as one of their courses.
        InMemoryCourseDataAccessObject memoryCourseDAO = new InMemoryCourseDataAccessObject(fileCourseDataAccessObject.getCourses());
        employeeDataAccessObject.save(employees);
//
//        // Updates courses to have MAT157 included.
        fileCourseDataAccessObject.save(courses);
    }

    @Test
    public void TestWritingAndReading() throws IOException {
        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");
        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        FileCourseDataAccessObject courseDataAccessObject = new FileCourseDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv",
                inMemoryEmployeeDataAccessObject);
        InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject = new InMemoryCourseDataAccessObject(courseDataAccessObject.getCourses());

        HashMap<String, String> courses = new HashMap<>();
        courses.put("testCourse", "");
        courses.put("CSC207", "");
        courses.put("CSC236", "");
        courses.put("MAT157", "");
        courses.put("Basket Weaving", "");
        courses.put("Rocket Flying", "");
        assertEquals(courses.keySet(), inMemoryCourseDataAccessObject.getAllIDs());
    }
}
