    package data_access;

    import entity.*;
    import entity.Event;
    import org.bson.json.JsonObject;

    import org.json.JSONObject;
    import org.json.JSONArray;

    import java.awt.*;
    import java.util.ArrayList;
    import java.util.HashMap;


    public class JsonToCourse {
        private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
        private String jsonString;
        public JsonToCourse(String jsonString, InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject) {
            this.jsonString = jsonString;
            this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
        }

        /**
         *
         * @return
         */
        public Course convert() {
            JSONObject obj = new JSONObject(jsonString);

            String courseName = obj.getString("course_name");
            String courseCode = obj.getString("course_code");
            String admin = obj.getString("admin"); // This is just the UID of the staff member

            ArrayList<String> listStaff = new ArrayList<String>();
            JSONArray staffArray = (JSONArray) obj.get("staff");
            if (staffArray != null) {
                for (int i = 0; i < staffArray.length(); i++){
                    listStaff.add(staffArray.getString(i));
                }
            }

            Employee courseAdmin = inMemoryEmployeeDataAccessObject.getByID(admin);

            Course course = new Course(courseName, courseCode, courseAdmin);

            for (String staffMember : listStaff) {
                Employee tempEmployee = inMemoryEmployeeDataAccessObject.getByID(staffMember);
                course.addStaff(tempEmployee);
            }

            // This would require Event to be created first but Event also needs to have Course created.
            // Could also potentially have the moment event is initialized that it would add it self to course.
            // This would allow me to read the course collection and then read event collection to complete the course info.

            // TODO: Consider variable changes.

            return course;

            // Could potentially use course factory if we can recreate the arraylist into Hashmap but this is probably
            // better because it will have each instance of employee have their corresponding Courses listed underneath.
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
    }
