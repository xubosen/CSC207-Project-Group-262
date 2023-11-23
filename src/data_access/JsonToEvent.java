package data_access;

import entity.*;
import org.bson.json.JsonObject;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class JsonToEvent {
    private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
    private InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject;
    private String jsonString;

//    public static void main(String[] args) {
//        String test = "{\"_id\": {\"$oid\": \"655bd81c3974c0a90e01ec08\"}, \"name\": \"CSC207TUT\", \"eventID\": \"TUT5201\", \"course\": \"CSC207\", " +
//                "\"sessions\": [\"TUT5201_11/20\"], \"staff\": [\"phanale4\", \"xubosen\"], \"event_type\": \"class entity.Tutorial\"}";
//
//        JSONObject obj = new JSONObject(test);
//
//        JSONArray staffArray = (JSONArray) obj.get("staff");
//
//        String name = obj.getString("name");
//        String eventID = obj.getString("eventID");
//        String courseCode = obj.getString("course");
//        String eventType = obj.getString("event_type");
//
//        System.out.println(name);
//        System.out.println(eventID);
//        System.out.println(courseCode);
//        System.out.println(staffArray);
//        System.out.println(eventType);
//
//
//    }
    public JsonToEvent(String jsonString, InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject,
                          InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject) {
        this.jsonString = jsonString;
        this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
        this.inMemoryCourseDataAccessObject = inMemoryCourseDataAccessObject;

    }

    /**
     * @return
     */
    public Event convert() {
        JSONObject obj = new JSONObject(jsonString);
        // "{\"_id\": {\"$oid\": \"6557dd0d9a2a09870f0db0e8\"}, " +
        //                "\"userID\": \"phanale4\", \"password\": \"123\", \"name\": \"Alexander Phan\", " +
        //                "\"email\": \"alexanderphan@mail.utoronto.ca\", " +
        //                "\"courses\": [{\"CSC207\":csc207,\"CSC236\":csc236}], \"sessions\": []}" +
        //                "\"role\": \"class entity TeachingAssistant\""
        // test code

        String name = obj.getString("name");
        String eventID = obj.getString("eventID");
        String courseCode = obj.getString("course");
        String eventType = obj.getString("event_type");



        ArrayList<String> listStaff = new ArrayList<String>();
        JSONArray staffArray = (JSONArray) obj.get("staff");
        if (staffArray != null) {
            for (int i = 0; i < staffArray.length(); i++){
                listStaff.add(staffArray.getString(i));
            }
        }

        Course course = inMemoryCourseDataAccessObject.getByID(courseCode);

        if (eventType.equals("class entity.Tutorial")) {
            Tutorial event = new Tutorial(name, eventID, course);
            for (String employees : listStaff) {
                event.addStaff(inMemoryEmployeeDataAccessObject.getByID(employees));
            }
            return event;
        }
        else {
            Lecture event = new Lecture(name, eventID, course);
            for (String employees : listStaff) {
                event.addStaff(inMemoryEmployeeDataAccessObject.getByID(employees));
            }
            return event;
        }
    }
}