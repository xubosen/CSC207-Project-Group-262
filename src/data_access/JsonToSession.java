package data_access;

import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.time.LocalDateTime;

public class JsonToSession {
    private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
    private InMemoryEventDataAccessObject inMemoryEventDataAccessObject;
    private String sessionNameKey = "session_name";
    private String sessionIDKey = "sessionID";
    private String locationKey = "location";
    private String eventKey = "event";
    private String staffKey = "staff";
    private String calEventKey = "cal_event";
    private String jsonString;
    private JSONObject jsonObject;
    public JsonToSession(String jsonStringSession, InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
                         InMemoryEventDataAccessObject inMemoryEventDataAccessObject) {
        this.jsonString = jsonStringSession;
        this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
        this.inMemoryEventDataAccessObject = inMemoryEventDataAccessObject;
        this.jsonObject = new JSONObject(jsonString);
    }

    private ArrayList<String> arrayListFromKey(String key) {
        // We need to change the location of this due to CleanArchitecture.
        ArrayList<String> outputList = new ArrayList<>();

        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                outputList.add(jsonArray.getString(i));
            }
        }
        return outputList;
    }

    /**
     * Converts the Json string and returns the ClassSession created from that info.
     * @return ClassSession with the following attributes
     */
    public ClassSession convert() {
        // Consider Json to middle data type
        // Pass that into a session factory

        String sessionName = jsonObject.getString(sessionNameKey);
        String sessionID = jsonObject.getString(sessionIDKey);
        String location = jsonObject.getString(locationKey);
        String eventID = jsonObject.getString(eventKey);


        // These two could be changed to a general arraylist generator and the input parameter is just the
        // key for that category.
        ArrayList<String> listStaff = arrayListFromKey(staffKey);


        ArrayList<String> listTimeSpan = arrayListFromKey(calEventKey);

        Event event = inMemoryEventDataAccessObject.getByID(eventID);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        CalendarEvent calendarEvent = getCalendarEvent(listTimeSpan, formatter);

        ClassSession classSession = new ClassSession(sessionID, sessionName, calendarEvent, location, event);

        // Make empty session as in no staff members
        // Factory takes in all the pieces of info above and listStaff to create the employee
        for (String staffMember : listStaff) {
            Employee tempEmployee = inMemoryEmployeeDataAccessObject.getByID(staffMember);
            classSession.addStaff(tempEmployee);
        }
        return classSession;
    }

    private static CalendarEvent getCalendarEvent(ArrayList<String> listTimeSpan, DateTimeFormatter formatter) {
        LocalDateTime start = LocalDateTime.parse(listTimeSpan.get(2), formatter);
        LocalDateTime end = LocalDateTime.parse(listTimeSpan.get(3), formatter);


        DateTimeSpan dateTimeSpan = new DateTimeSpan(start, end);

        return new CalendarEvent(listTimeSpan.get(0), listTimeSpan.get(1),
                dateTimeSpan);
    }
}
