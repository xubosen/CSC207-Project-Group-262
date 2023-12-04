package tests.daoTests;

import data_access.file_dao.FileCourseDataAccessObject;
import data_access.file_dao.FileEmployeeDataAccessObject;
import data_access.file_dao.FileEventDataAccessObject;
import data_access.file_dao.FileSessionDataAccessObject;
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

public class SessionDAOTest {

    /**
     * This test section setsup the reading test by writing to the database.
     * @throws IOException
     */
    @Before
    public void setUp() throws IOException {
        Instructor alexander = new Instructor("phanale1231423", "Alexander Phan12",
                "alexander.phan@mail.utoronto.ca12", "123412");

        Course csc236 = new Course("Introduction to the Theory of Computation", "CSC236", alexander);

        Lecture lec0101 = new Lecture("Computer Science LEC0101 M F", "LEC0101", csc236);

        Tutorial tut5201 = new Tutorial("Computer Science TUT5201 W", "TUT5201", csc236);

        DateTimeSpan dateTimeSpanM = new DateTimeSpan(LocalDateTime.of(2023, 11, 27, 13, 0),
                LocalDateTime.of(2023, 11, 27, 14, 0));

        CalendarEvent calendarEventM = new CalendarEvent("LEC0101", "Monday Lecture Nov. 27 1-2pm", dateTimeSpanM);

        ClassSession lec0101M = new ClassSession("LEC0101M 11/27 1-2", "Monday Lecture Nov. 27 1-2pm", calendarEventM, "WI1016", lec0101);

        DateTimeSpan dateTimeSpanF = new DateTimeSpan(LocalDateTime.of(2023, 12, 1, 13, 0),
                LocalDateTime.of(2023, 12, 1, 14, 0));

        CalendarEvent calendarEventF = new CalendarEvent("LEC0101", "Friday Lecture Dec. 01 1-2pm", dateTimeSpanF);

        ClassSession lec0101F = new ClassSession("LEC0101F 12/01 1-2", "Friday Lecture Dec. 01 1-2pm", calendarEventF, "WI1016", lec0101);


        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/employeeInfo.csv");
        HashMap<String, Employee> employees = new HashMap<>(employeeDataAccessObject.getAccount());
        employees.put(alexander.getUID(), alexander);
        InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject(employees);


        FileCourseDataAccessObject fileCourseDataAccessObject = new FileCourseDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/courseInfo.csv",
                inMemoryEmployeeDataAccessObject);
        HashMap<String, Course> courses = new HashMap<>(fileCourseDataAccessObject.getCourses());
        courses.put(csc236.getCourseCode(), csc236);
        InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject = new InMemoryCourseDataAccessObject(courses);

        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/eventInfo.csv",
                inMemoryEmployeeDataAccessObject, inMemoryCourseDataAccessObject);
        HashMap<String, Event> events = new HashMap<>(fileEventDAO.getEvents());
        events.put(lec0101.getEventID(), lec0101);
        events.put(tut5201.getEventID(), tut5201);
        InMemoryEventDataAccessObject memEventDAO = new InMemoryEventDataAccessObject(events);

        FileSessionDataAccessObject fileSessionDAO = new FileSessionDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/sessionInfo.csv",
                inMemoryEmployeeDataAccessObject, memEventDAO);
        HashMap<String, ClassSession> sessions = new HashMap<>(fileSessionDAO.getSessions());
        sessions.put(lec0101M.getSessionID(), lec0101M);
        sessions.put(lec0101F.getSessionID(), lec0101F);


        // Updates current alexander to have CSC236 as one of their courses.
        employeeDataAccessObject.save(employees);

        // Updates courses to have MAT157 included.
        fileCourseDataAccessObject.save(courses);

        // Updates events to have one lecture and one tutorial.
        fileEventDAO.save(events);

        // Updates Class Sessions to include the lectures class session.
        fileSessionDAO.save(sessions);
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

        FileSessionDataAccessObject fileSessionDAO = new FileSessionDataAccessObject("src/data_access/file_dao/mongoDBFilePaths/sessionInfo.csv",
                inMemoryEmployeeDataAccessObject, eventDAO);

        InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject(fileSessionDAO.getSessions());

        HashMap<String, String> classSessions = new HashMap<>();
        classSessions.put("LECBW101 FRI", "");
        classSessions.put("BW101 Sat", "");
        classSessions.put("LEC0101M 11/27 1-2", "");
        classSessions.put("LEC0101F 12/01 1-2", "");
        assertEquals(classSessions.keySet(), sessionDAO.getAllIDs());
    }
}
