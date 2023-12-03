package data_access.file_dao;

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


        FileEmployeeDataAccessObject employeeDataAccessObject = new FileEmployeeDataAccessObject("./employeeInformation.csv");

        FileCourseDataAccessObject fileCourseDataAccessObject = new FileCourseDataAccessObject("./courseInformation.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()));

        FileEventDataAccessObject fileEventDAO = new FileEventDataAccessObject("./eventInformation.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()),
                new InMemoryCourseDataAccessObject(fileCourseDataAccessObject.getCourses()));

        FileSessionDataAccessObject fileSessionDAO = new FileSessionDataAccessObject("./sessionInformation.csv",
                new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount()),
                new InMemoryEventDataAccessObject(fileEventDAO.getEvents()));

//        // Updates current alexander to have CSC236 as one of their courses.
//        employeeDataAccessObject.save(alexander);
//
//        // Updates courses to have MAT157 included.
//        fileCourseDataAccessObject.save(csc236);
//
//        // Updates events to have one lecture and one tutorial.
//        fileEventDAO.save(lec0101);
//        fileEventDAO.save(tut5201);
//
//        // Updates Class Sessions to include the lectures class session.
//        fileSessionDAO.save(lec0101M);
//        fileSessionDAO.save(lec0101F);
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

        FileSessionDataAccessObject fileSessionDAO = new FileSessionDataAccessObject("./sessionInformation.csv",
                inMemoryEmployeeDataAccessObject, eventDAO);

        InMemorySessionDataAccessObject sessionDAO = new InMemorySessionDataAccessObject(fileSessionDAO.getSessions());

        HashMap<String, String> classSessions = new HashMap<>();
        classSessions.put("LEC0101M 11/27 1-2", "");
        classSessions.put("LEC0101F 12/01 1-2", "");
        assertEquals(classSessions.keySet(), sessionDAO.getAllIDs());
        // This test worked and didn't create duplicates of the documents either
    }
}
