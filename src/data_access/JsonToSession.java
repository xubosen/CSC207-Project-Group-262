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
     *
     * @return
     */
    public ClassSession convert() {
        // TODO: Convert all of the info to the session collection info.
        JSONObject obj = new JSONObject(jsonString);

        // Json to middle data type

        // Pass that into a session factory

        // The plan is to store the name description and the date time span into a arrayList

        String sessionName = obj.getString("session_name");
        String sessionID = obj.getString("sessionID");
        String location = obj.getString("location");
        String eventID = obj.getString("event");


        // These two could be changed to a general arraylist generator and the input parameter is just the
        // key for that category.
        ArrayList<String> listStaff = arrayListFromKey("staff");
//                new ArrayList<String>();
//        JSONArray staffArray = (JSONArray) obj.get("staff");
//        if (staffArray != null) {
//            for (int i = 0; i < staffArray.length(); i++){
//                listStaff.add(staffArray.getString(i));
//            }
//        }
        // Make this not hardcoded
        ArrayList<String> listTimeSpan = arrayListFromKey("cal_event");
//                new ArrayList<>();
//        JSONArray timeSpanArray = (JSONArray) obj.get("cal_event");
//        if (timeSpanArray != null) {
//            for (int i = 0; i < timeSpanArray.length(); i++) {
//                listTimeSpan.add(timeSpanArray.getString(i));
//                // TODO Make sure that this can pull the data about start time and end time as a string form
//            }
//        }


        Event event = inMemoryEventDataAccessObject.getEvent(eventID);

        // How to convert from string to LocalDateTime
        // This chunk could be converted to a helper method
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse((CharSequence) listTimeSpan.get(2), formatter);
        LocalDateTime end = LocalDateTime.parse((CharSequence) listTimeSpan.get(3), formatter);


        DateTimeSpan dateTimeSpan = new DateTimeSpan(start, end);
        // Maybe I need to convert the values at 2 and 3 to type LocalDateTime.
        // Figure this step out and then create tests todahy and then data bases should be good.

        CalendarEvent calendarEvent = new CalendarEvent(listTimeSpan.get(0).toString(), listTimeSpan.get(1).toString(),
                dateTimeSpan);


        ClassSession classSession = new ClassSession(sessionID, sessionName, calendarEvent, location, event);

        // Make empty session as in no staff members
        // Factory takes in all the pieces of info above and listStaff to create the employee
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
