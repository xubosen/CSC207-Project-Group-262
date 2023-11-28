package data_access;

import entity.*;
import org.bson.json.JsonObject;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;


public class JsonToEmployee {
    private String jsonString;

    public JsonToEmployee(String jsonString) {
        this.jsonString = jsonString;
    }

    /**
     * @return
     */
    public Employee convert() {
//        Employee employee;
//        HashMap<String, Course> courseHashMap = new HashMap<String, Course>();
//        HashMap<String, ClassSession> classSessionHashMap = new HashMap<String, ClassSession>();
//

        JSONObject obj = new JSONObject(jsonString);
        // "{\"_id\": {\"$oid\": \"6557dd0d9a2a09870f0db0e8\"}, " +
        //                "\"userID\": \"phanale4\", \"password\": \"123\", \"name\": \"Alexander Phan\", " +
        //                "\"email\": \"alexanderphan@mail.utoronto.ca\", " +
        //                "\"courses\": [{\"CSC207\":csc207,\"CSC236\":csc236}], \"sessions\": []}" +
        //                "\"role\": \"class entity TeachingAssistant\""
        // test code

        String userID = obj.getString("userID");
        String password = obj.getString("password");
        String name = obj.getString("name");
        String email = obj.getString("email");
        String employeeType = obj.getString("role");

        if (employeeType.equals("class entity.TeachingAssistant")) {
            TeachingAssistant teachingAssistant = new TeachingAssistant(userID, password, name, email);
            return teachingAssistant;
        } else {
            Instructor instructor = new Instructor(userID, password, name, email);
            return instructor;
        }

    }
}
//    public static void main(String[] args) {
//        HashMap<String, Course> courseHashMap = new HashMap<String, Course>();
//        HashMap<String, ClassSession> classSessionHashMap = new HashMap<String, ClassSession>();
//
//        JSONObject obj = new JSONObject("{\"_id\": {\"$oid\": \"6557dd0d9a2a09870f0db0e8\"}, " +
//                "\"userID\": \"phanale4\", \"password\": \"123\", \"name\": \"Alexander Phan\", " +
//                "\"email\": \"alexanderphan@mail.utoronto.ca\", " +
//                "\"courses\": [{\"CSC207\":csc207,\"CSC236\":csc236}], \"sessions\": []}" +
//                "\"role\": \"entity TeachingAssistant\"");
//
//        String userID = obj.getString("userID");
//        String password = obj.getString("password");
//        String name = obj.getString("name");
//        String email = obj.getString("email");
//        String employeeType = obj.getString("role");
//
//        EmployeeFactory employeeFactory = new EmployeeFactory();
//        Employee employee = employeeFactory.create(userID, name, email, password, classSessionHashMap,
//                courseHashMap, employeeType);

//        String courses = obj.getJSONArray("courses").toString().replace("\"", "");
//        String sessions = obj.getJSONArray("sessions").toString().replace("\"", "");
//
//        String coursesCut = courses.substring(2, courses.length() - 2);
//        String sessionCut = courses.substring(2, courses.length() - 2);
//
//        // When you add employee to course, it will add course to employee
//
//        String[] pairs = coursesCut.split(",");
//        for (int i = 0; i < pairs.length; i++) {
//            String pair = pairs[i];
//            String[] keyValue = pair.split(":");
//            courseHashMap.put(keyValue[0], null);
//        }

//        System.out.println(coursesCut);