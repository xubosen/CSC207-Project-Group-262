package data_access;

import entity.ClassSession;
import entity.Course;
import entity.Instructor;
import entity.InstructorFactory;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileInstructorDataAccessObject {
    private final File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    public static final Map<String, Instructor> accounts = new HashMap<>();

    private InstructorFactory instructorFactory;

    public FileInstructorDataAccessObject(String csvPath, InstructorFactory instructorFactory) throws IOException {
        this.instructorFactory = instructorFactory;

        csvFile = new File(csvPath);
        headers.put("userID", 0);
        headers.put("name", 1);
        headers.put("email", 2);
        headers.put("password", 3);

        headers.put("courses", 4);

        headers.put("sessions", 5);

        headers.put("calendar", 6);

        // Must recast the serializable information back into original object type

        // Uses ObjectInputStream and ObjectOutputStream

        if (csvFile.length() == 0) {
            save();
        } else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();

                // For later: clean this up by creating a new Exception subclass and handling it in the UI.
                assert header.equals("userID,name,email,password,courses,sessions,calendar");

                String row;

                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",");
                    String username = String.valueOf(col[headers.get("userID")]);
                    String name = String.valueOf(col[headers.get("name")]);
                    String email = String.valueOf(col[headers.get("email")]);
                    String password = String.valueOf(col[headers.get("password")]);
                    String courses = String.valueOf(col[headers.get("courses")]);
                    String sessions = String.valueOf(col[headers.get("sessions")]);

                    // Maybe just keep calendar out of the headers because it would make the calendar off of sessions
                    // and it's a different data type than String
                    String calendar = String.valueOf(col[headers.get("calendar")]);

                    //
                    HashMap<String, ClassSession> deserializedSessions = new HashMap<String, ClassSession>();
                    HashMap<String, Course> deserializedCourses = new HashMap<String, Course>();

                    // Deserialize Course Hashmap File
                    try (FileInputStream fileInputStream = new FileInputStream(courses);
                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                        // Read the object from the stream and cast it to a HashMap
                        HashMap<String, Course> courseHashMap = (HashMap<String, Course>) objectInputStream.readObject();

                        // Now you have your HashMap back
                        deserializedCourses = courseHashMap;

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    try (FileInputStream fileInputStream = new FileInputStream(sessions);
                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                        // Read the object from the stream and cast it to a HashMap
                        HashMap<String, ClassSession> sessionHashMap = (HashMap<String, ClassSession>) objectInputStream.readObject();

                        // This may get accessed if
                        deserializedSessions = sessionHashMap;

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    // I need to create an exact copy of the teaching assistant with all their courses, sessions, calendar
                    Instructor instructor = instructorFactory.create(username, name, email,
                            password, deserializedSessions, deserializedCourses);

                    accounts.put(username, instructor);
                }
            }
        }
    }

    public void save(Instructor instructor) {
        accounts.put(instructor.getUID(), instructor);
        this.save();
    }

    public Instructor get(String userID) {
        return accounts.get(userID);
    }

    private void save() {
        // This should save the information as the headers as wanted
        // TODO: Something like phanale4,Alexander,alexander.phan@mail.utoronto.ca,password,phanale4Courses.ser,phanale4Sessions.ser

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (Instructor instructor : accounts.values()) {

                String courseSerFile = instructor.getUID() + "Courses.ser";

                String sessionSerFile = instructor.getUID() + "Sessions.ser";

                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                        instructor.getUID(), instructor.getName(), instructor.getEmail(),
                        instructor.getPassword(), courseSerFile, sessionSerFile);
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Return whether a Teaching Assistant sts with user identifier.
     * @param userID the username to check.
     * @return whether a Teaching Assistant exists with username identifier
     */
    public boolean existsByName(String userID) {
        return accounts.containsKey(userID);
    }

    public String getAccounts() {
        StringBuilder accounts = new StringBuilder();
        for (HashMap.Entry<String, Instructor> entry : this.accounts.entrySet()) {
            accounts.append(entry.getKey()).append("\n");
        }
        return accounts.toString();
    }
}
