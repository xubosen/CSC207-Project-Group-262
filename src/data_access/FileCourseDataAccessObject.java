package data_access;

import entity.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileCourseDataAccessObject {
    private final File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    public static final Map<String, Course> courses = new HashMap<>();

    private CourseFactory courseFactory;

    public FileCourseDataAccessObject(String csvPath, CourseFactory courseFactory) throws IOException {
        this.courseFactory = courseFactory;

        csvFile = new File(csvPath);
        headers.put("courseName", 0);
        headers.put("courseCode", 1);
        headers.put("courseAdmin", 2);

//        headers.put("staff", 3);
//
//        headers.put("events", 4);

        // Must recast the serializable information back into original object type

        // Uses ObjectInputStream and ObjectOutputStream

        if (csvFile.length() == 0) {
            save();
        } else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();

                // For later: clean this up by creating a new Exception subclass and handling it in the UI.
                assert header.equals("courseName,courseCode,courseAdmin");

                String row;

                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",");
                    String courseName = String.valueOf(col[headers.get("courseName")]);
                    String courseCode = String.valueOf(col[headers.get("courseCode")]);
                    String courseAdmin = String.valueOf(col[headers.get("courseAdmin")]);

//                    String staff = String.valueOf(col[headers.get("staff")]);
//                    String events = String.valueOf(col[headers.get("events")]);

//                    HashMap<String, Employee> deserializedStaff = new HashMap<String, Employee>();
//                    HashMap<String, Event> deserializedEvents = new HashMap<String, Event>();

                    // Deserialize Course Hashmap File
//                    try (FileInputStream fileInputStream = new FileInputStream(staff);
//                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
//
//                        // Read the object from the stream and cast it to a HashMap
//                        HashMap<String, Employee> staffHashMap = (HashMap<String, Employee>) objectInputStream.readObject();
//
//                        // Now you have your HashMap back
//                        deserializedStaff = staffHashMap;
//
//                    } catch (IOException | ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    try (FileInputStream fileInputStream = new FileInputStream(events);
//                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
//
//                        // Read the object from the stream and cast it to a HashMap
//                        HashMap<String, Event> eventsHashMap = (HashMap<String, Event>) objectInputStream.readObject();
//
//                        // This may get accessed if
//                        deserializedEvents = eventsHashMap;
//
//                    } catch (IOException | ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }

                    // I need to create an exact copy of the teaching assistant with all their courses, sessions, calendar
                    InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject = new InMemoryEmployeeDataAccessObject();
                    Employee theAdmin = inMemoryEmployeeDataAccessObject.getByID(courseAdmin);

                    // Would want to implement this using the hashmap ser files and course factory.

                    Course course = new Course(courseName, courseCode, theAdmin);


                    courses.put(courseCode, course);
                }
            }
        }
    }

    public void save(Course course) {
        courses.put(course.getCourseCode(), course);
        this.save();
    }

    public Course get(String courseCode) {
        return courses.get(courseCode);
    }

    private void save() {
        // This should save the information as the headers as wanted

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (Course course : courses.values()) {

                String line = String.format("%s,%s,%s", course.getCourseCode(), course.getName(),
                        course.getAdmin().getUID());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Return whether a course exists with their identifier.
     * @param courseCode the co
     * @return whether the course code is inside
     */
    public boolean existsByName(String courseCode) {
        return courses.containsKey(courseCode);
    }

    public String getCourses() {
        StringBuilder courses = new StringBuilder();
        for (Map.Entry<String, Course> entry : this.courses.entrySet()) {
            courses.append(entry.getKey()).append("\n");
        }
        return courses.toString();
    }
}
