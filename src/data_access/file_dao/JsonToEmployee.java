package data_access.file_dao;

import entity.*;
import org.bson.json.JsonObject;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;


public class JsonToEmployee {
    private String userIDKey = "userID";
    private String passwordKey = "password";
    private String nameKey = "name";
    private String emailKey = "email";
    private String roleKey = "role";
    private String jsonString;

    public JsonToEmployee(String jsonString) {
        this.jsonString = jsonString;
    }

    /** String form of the json pulled from mongodb and converts it into an employee
     * @return Employee
     */
    public Employee convert() {
        JSONObject obj = new JSONObject(jsonString);

        String userID = obj.getString(userIDKey);
        String password = obj.getString(passwordKey);
        String name = obj.getString(nameKey);
        String email = obj.getString(emailKey);
        String employeeType = obj.getString(roleKey);

        if (employeeType.equals("class entity.TeachingAssistant")) {
            TeachingAssistant teachingAssistant = new TeachingAssistant(userID, name, email, password);
            return teachingAssistant;
        } else {
            Instructor instructor = new Instructor(userID, name, email, password);
            return instructor;
        }

    }
}