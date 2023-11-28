    package data_access;

    import entity.*;

    import org.json.JSONObject;
    import org.json.JSONArray;

    import java.util.ArrayList;


    public class JsonToCourse {
        private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
        private String jsonString;

        /**
         * Initializer for the JsonToCourse file.
         * @param jsonString The string form of the json file from mongodb
         * @param inMemoryEmployeeDataAccessObject The in memory DAO required to create staff members
         */
        public JsonToCourse(String jsonString, InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject) {
            this.jsonString = jsonString;
            this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
        }

        /**
         * Converts the Json String to an instance of a course.
         * @return The course from the course collection.
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
    }
