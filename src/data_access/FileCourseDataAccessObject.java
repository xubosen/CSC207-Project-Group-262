//package data_access;
//
//import com.mongodb.client.*;
//import entity.*;
//import org.bson.Document;
//
//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * This Class allows us to pull from the courses collection inside our mongodb Database.
// * It then creates all instances of courses stored inside so it can be saved into memory for the program to retrieve.
// */
//public class FileCourseDataAccessObject {
//    private final File databaseFiles;
//    private final Map<String, Integer> headers = new LinkedHashMap<>();
//    private static final HashMap<String, Course> courses = new HashMap<>();
//
//    /**
//     * Constructs a new file course DAO using information inside the inMemoryEmployeeDataAccessObject and the course
//     * collection on mongodb.
//     * @param inMemoryEmployeeDataAccessObject the DAO that we will pull from to generate the courses with their staff
//     */
//    public FileCourseDataAccessObject(String databasePath,
//                                      InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject) throws IOException {
//        this.databaseFiles = new File(databasePath);
//
//        headers.put("databaseLink", 0);
//        headers.put("databaseName", 1);
//        headers.put("collectionName", 2);
//
//        ArrayList<String> holder = getURIAndDBNames();
//        String uri = holder.get(0);
//        String databaseName = holder.get(1);
//        String collectionName = holder.get(2);
//
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            // database and collection code goes here
//            MongoDatabase db = mongoClient.getDatabase(databaseName);
//            MongoCollection<Document> collection = db.getCollection(collectionName);
//
//            // find code goes here
//            MongoCursor<Document> cursor = collection.find().iterator();
//
//            // iterate code goes here
//            try {
//                while (cursor.hasNext()) {
//                    String courseInJsonForm = cursor.next().toJson();
//                    JsonToCourse jsonToCourse = new JsonToCourse(courseInJsonForm, inMemoryEmployeeDataAccessObject);
//                    Course course = jsonToCourse.convert();
//                    courses.put(course.getCourseCode(), course);
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//    }
//
//    /**
//     * Saves this course into the courses HashMap and then proceeds to save it into the database.
//     * @param course The course that you want to save.
//     */
//    public void save(Course course) throws IOException {
//        courses.put(course.getCourseCode(), course);
//        this.save();
//    }
//
//    /**
//     * Retrieves the course with the respective course code.
//     * @param courseCode The course code use to search through the course HashMap.
//     * @return The respective course
//     */
//    public Course get(String courseCode) {
//        return courses.get(courseCode);
//    }
//
//    /**
//     * Saves all the courses inside the HashMap into the courses collection on mongodb.
//     */
//    private void save() throws IOException {
//        ArrayList<String> holder = getURIAndDBNames();
//        String uri = holder.get(0);
//        String databaseName = holder.get(1);
//        String collectionName = holder.get(2);
//
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//
//            // database and collection code goes here
//            MongoDatabase db = mongoClient.getDatabase(databaseName);
//            MongoCollection<Document> collection = db.getCollection(collectionName);
//
//            // insert code goes here
//            List<Document> documents = new ArrayList<>();
//
//            for (Course course : courses.values()) {
//
//                ArrayList<String> staffList = new ArrayList<>();
//
//                for (Employee employee : course.getStaff().values()) {
//                    staffList.add(employee.getUID());
//                }
//
//                ArrayList<String> eventList = new ArrayList<>();
//
//                for (Event event : course.getEvents().values()) {
//                    eventList.add(event.getEventID());
//                }
//
//                Document courseDoc = new Document("course_name", course.getName()).
//                        append("course_code", course.getCourseCode()).append("Staff", staffList).
//                        append("events", course.getEvents()).append("course_admin", course.getAdmin().getUID());
//
//                // This adds the employee information to the mongodb
//                documents.add(courseDoc);
//
//            }
//
//            // Need to not use insertMany because it can create duplicate entries within mongodb.
//            // Could search using userID and delete past data before inserting the new data.
//            collection.insertMany(documents);
//        }
//    }
//
//
//    /**
//     * Return whether a course exists with their identifier.
//     * @param courseCode the co
//     * @return whether the course code is inside
//     */
//    public boolean existsByCode(String courseCode) {
//        return courses.containsKey(courseCode);
//    }
//
//    /**
//     * Converts the Hashmap of courses into a String where each of their course code is displayed on a new line.
//     * @return Every single course code line by line.
//     */
//    public String getCoursesString() {
//        StringBuilder courses = new StringBuilder();
//        for (Map.Entry<String, Course> entry : this.courses.entrySet()) {
//            courses.append(entry.getKey()).append("\n");
//        }
//        return courses.toString();
//    }
//
//    /**
//     * Returns the HashMap of course code and courses
//     * @return this.courses
//     */
//    public HashMap<String, Course> getCourses() {
//        return courses;
//    }
//
//    private ArrayList<String> getURIAndDBNames() throws IOException {
//
//        ArrayList<String> uriAndNames = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFiles))) {
//            String header = reader.readLine();
//
//            assert header.equals("databaseLink,databaseName,collectionName");
//            String row;
//            while ((row = reader.readLine()) != null) {
//                String[] col = row.split(",");
//                uriAndNames.add(String.valueOf(col[headers.get("databaseLink")]));
//                uriAndNames.add(String.valueOf(col[headers.get("databaseName")]));
//                uriAndNames.add(String.valueOf(col[headers.get("collectionName")]));
//            }
//        }
//        return uriAndNames;
//    }
//}
