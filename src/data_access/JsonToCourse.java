    package data_access;

    import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
    import entity.*;

    import org.json.JSONObject;
    import org.json.JSONArray;

    import java.util.ArrayList;


    public class JsonToCourse {
        private String courseNameSearch = "course_name";
        private String courseCodeSearch = "course_code";
        private String adminSearch = "admin";
        private String staffSearch = "staff";
        private InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject;
        private JSONObject jsonObject;

        /**
         * Initializer for the JsonToCourse file.
         * @param jsonString The string form of the json file from mongodb
         * @param inMemoryEmployeeDataAccessObject The in memory DAO required to create staff members
         */
        public JsonToCourse(String jsonString, InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject) {
            this.inMemoryEmployeeDataAccessObject = inMemoryEmployeeDataAccessObject;
            this.jsonObject = new JSONObject(jsonString);
        }

        /**
         * Converts the Json String to an instance of a course.
         * @return The course from the course collection.
         */
        public Course convert() {
            String courseName = jsonObject.getString(courseNameSearch);
            String courseCode = jsonObject.getString(courseCodeSearch);
            String admin = jsonObject.getString(adminSearch);

            ArrayList<String> listStaff = new ArrayList<String>();
            JSONArray staffArray = (JSONArray) jsonObject.get(staffSearch);
            if (staffArray != null) {
                for (int i = 0; i < staffArray.length(); i++){
                    listStaff.add(staffArray.getString(i));
                }
            }

            Instructor courseAdmin = (Instructor) inMemoryEmployeeDataAccessObject.getByID(admin);

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
