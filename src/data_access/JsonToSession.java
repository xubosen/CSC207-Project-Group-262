package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.util.Date;

public class JsonToSession {
    private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
    private InMemoryEventDataAccessObject inMemoryEventDataAccessObject;
    private String jsonString;
    public JsonToSession(String jsonStringSession, InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
                         InMemoryEventDataAccessObject inMemoryEventDataAccessObject) {
        this.jsonString = jsonStringSession;
        this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
        this.inMemoryEventDataAccessObject = inMemoryEventDataAccessObject;
    }

    /**
     *
     * @return
     */
    public ClassSession convert() {
        // TODO: Convert all of the info to the session collection info.
        JSONObject obj = new JSONObject(jsonString);

        // The plan is to store the name description and the date time span into a arrayList

        String sessionName = obj.getString("session_name");
        String sessionID = obj.getString("sessionID");
        String location = obj.getString("location");
        String eventID = obj.getString("event");

        ArrayList<String> listStaff = new ArrayList<String>();
        JSONArray staffArray = (JSONArray) obj.get("staff");
        if (staffArray != null) {
            for (int i = 0; i < staffArray.length(); i++){
                listStaff.add(staffArray.getString(i));
            }
        }

        ArrayList<Object> listTimeSpan = new ArrayList<>();
        JSONArray timeSpanArray = (JSONArray) obj.get("cal_event");
        if (timeSpanArray != null) {
            for (int i = 0; i < timeSpanArray.length(); i++) {
                listTimeSpan.add(timeSpanArray.getString(i));
                // TODO Make sure that this can pull the data about start time and end time as a string form
            }
        }
        Event event = inMemoryEventDataAccessObject.getEvent(eventID);

        // How to convert from string to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse((CharSequence) listTimeSpan.get(2), formatter);
        LocalDateTime end = LocalDateTime.parse((CharSequence) listTimeSpan.get(3), formatter);


        DateTimeSpan dateTimeSpan = new DateTimeSpan(start, end);
        // Maybe I need to convert the values at 2 and 3 to type LocalDateTime.
        // Figure this step out and then create tests todahy and then data bases should be good.

        CalendarEvent calendarEvent = new CalendarEvent(listTimeSpan.get(0).toString(), listTimeSpan.get(1).toString(),
                dateTimeSpan);


        ClassSession classSession = new ClassSession(sessionID, sessionName, calendarEvent, location, event);

        for (String staffMember : listStaff) {
            Employee tempEmployee = inMemoryEmployeeDataAccessObject.getByID(staffMember);
            classSession.addStaff(tempEmployee);
        }

        // This would require Event to be created first but Event also needs to have Course created.
        // Could also potentially have the moment event is initialized that it would add it self to course.
        // This would allow me to read the course collection and then read event collection to complete the course info.

        // TODO: Consider variable changes.

        // TODO: Create tests for all of the DAO and then also discuss about changing the init for some classes like course with the admin

        return classSession;

        // Could potentially use course factory if we can recreate the arraylist into Hashmap but this is probably
        // better because it will have each instance of employee have their corresponding Courses listed underneath.
    }
}
