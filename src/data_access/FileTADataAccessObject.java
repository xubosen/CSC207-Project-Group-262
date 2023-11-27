package data_access;

import entity.ClassSession;
import entity.Course;
import entity.TeachingAssistant;
import entity.TeachingAssistantFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;

// Either extend or implements serializable
public class FileTADataAccessObject implements Serializable {

    private final File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    public final Map<String, TeachingAssistant> accounts = new HashMap<>();

    private TeachingAssistantFactory teachingAssistantFactory;

    public FileTADataAccessObject(String csvPath, TeachingAssistantFactory teachingAssistantFactory) throws IOException {
        this.teachingAssistantFactory = teachingAssistantFactory;

        csvFile = new File(csvPath);
        headers.put("userID", 0);
        headers.put("name", 1);
        headers.put("email", 2);
        headers.put("password", 3);

        headers.put("courses", 4);

        headers.put("sessions", 5);

        // headers.put("calendar", 6);

        // Must recast the serializable information back into original object type

        // Uses ObjectInputStream and ObjectOutputStream

        if (csvFile.length() == 0) {
            save();
        } else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();

                // For later: clean this up by creating a new Exception subclass and handling it in the UI.
                assert header.equals("userID,name,email,password,courses,sessions");

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

                    // String calendar = String.valueOf(col[headers.get("calendar")]);


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
                    TeachingAssistant teachingAssistant1 = teachingAssistantFactory.create(username, name, email,
                            password, deserializedSessions, deserializedCourses);

                    accounts.put(username, teachingAssistant1);
                }
            }
        }
    }

    public void save(TeachingAssistant teachingAssistant) {
        accounts.put(teachingAssistant.getUserID(), teachingAssistant);
        this.save();
    }

    public TeachingAssistant get(String userID) {
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

            for (TeachingAssistant teachingAssistants : accounts.values()) {

                String courseSerFile = teachingAssistants.getUserID() + "Courses.ser";
                try
                {
                    FileOutputStream fos =
                            new FileOutputStream(courseSerFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(teachingAssistants.getCourses());
                    oos.close();
                    fos.close();
                }catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }

                // This writes the .ser file
                String sessionSerFile = teachingAssistants.getUserID() + "Sessions.ser";
                try
                {
                    FileOutputStream fos =
                            new FileOutputStream(sessionSerFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(teachingAssistants.getSessions());
                    oos.close();
                    fos.close();
                }catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }

                String line = String.format("%s,%s,%s,%s,%s,%s",
                        teachingAssistants.getUserID(), teachingAssistants.getName(), teachingAssistants.getEmail(),
                        teachingAssistants.getPassword(), courseSerFile, sessionSerFile);
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
        for (Map.Entry<String, TeachingAssistant> entry : this.accounts.entrySet()) {
            accounts.append(entry.getKey()).append("\n");
        }
        return accounts.toString();
    }

    public Map<String, TeachingAssistant> getAccount() {
        return accounts;
    }
}
