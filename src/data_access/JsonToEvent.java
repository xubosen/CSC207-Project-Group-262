package data_access;

import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.*;

import java.util.ArrayList;

public class JsonToEvent {
    private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
    private InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject;
    private String nameKey = "name";
    private String eventIDKey = "eventID";
    private String courseKey = "course";
    private String eventTypeKey = "event_type";
    private String staffKey = "staff";
    private String jsonString;
    private JSONObject jsonObject;

    public JsonToEvent(String jsonString, InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject,
                          InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject) {
        this.jsonString = jsonString;
        this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
        this.inMemoryCourseDataAccessObject = inMemoryCourseDataAccessObject;
        this.jsonObject = new JSONObject(jsonString);
    }

    /**
     * @return
     */
    public Event convert() {
        String name = jsonObject.getString(nameKey);
        String eventID = jsonObject.getString(eventIDKey);
        String courseCode = jsonObject.getString(courseKey);
        String eventType = jsonObject.getString(eventTypeKey);



        ArrayList<String> listStaff = new ArrayList<String>();
        JSONArray staffArray = (JSONArray) jsonObject.get(staffKey);
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