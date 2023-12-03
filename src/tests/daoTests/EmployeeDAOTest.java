package tests.daoTests;


import data_access.file_dao.FileEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
public class EmployeeDAOTest {
    private Course csc207;
    private Employee alexander;
    private Employee staffMember;
    private Event event;

    @Before
    public void setUp() throws IOException {
        Instructor alexander = new Instructor("phanale1231423", "Alexander Phan12",
                "alexander.phan@mail.utoronto.ca12", "123412");

        Course csc207 = new Course("Software Design123", "CSC207123", alexander);

        Event tut5201 = new Tutorial("Tutorial5201", "TUT5201", csc207);

        DateTimeSpan dateTimeSpan = new DateTimeSpan(LocalDateTime.of(2023, 11, 20, 19, 0),
                LocalDateTime.of(2023, 11, 20, 21, 0));
        CalendarEvent calEvent = new CalendarEvent("name", "description", dateTimeSpan);

        ClassSession CSC207TUT5201M1123 = new ClassSession("CSC207TUT5201 2023-11-20", "Who knows",
                calEvent, "BA3195", tut5201);

        CSC207TUT5201M1123.addStaff(alexander);

        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");

        InMemoryEmployeeDataAccessObject memoryEmpDAO = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        employeeDataAccessObject.save(memoryEmpDAO.getEmployees());
    }

    @Test
    public void TestWritingAndReading() throws IOException {
        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");

        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());
        System.out.println(inMemoryEmployeeDataAccessObject.getAllIDs());
        HashMap<String, String> employees = new HashMap<>();
        employees.put("phanale4", "bruh");
        employees.put("simon", "bruh");
        employees.put("yoohamj", "bruh");
        employees.put("mdm", "bruh");
        employees.put("xubosen", "asdifj");
        assertEquals(employees.keySet(), inMemoryEmployeeDataAccessObject.getAllIDs());
        // This test worked and didn't create duplicates of the documents either
    }
}
