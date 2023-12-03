package data_access.file_dao;

import com.mongodb.client.*;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import data_access.in_memory_dao.InMemoryEventDataAccessObject;
import entity.ClassSession;
import entity.Employee;
import org.bson.Document;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


// TODO: implement an interface in between so I can follow Clean Architecture
public class FileSessionDataAccessObject {
    private final File databaseFiles;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private static final HashMap<String, ClassSession> sessions = new HashMap<>();
    public FileSessionDataAccessObject(String databasePath,
                                       InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
                                       InMemoryEventDataAccessObject inMemoryEventDataAccessObject) throws IOException {
        this.databaseFiles = new File(databasePath);

        headers.put("databaseLink", 0);
        headers.put("databaseName", 1);
        headers.put("collectionName", 2);

        ArrayList<String> holder = getURIAndDBNames();
        String uri = holder.get(0);
        String databaseName = holder.get(1);
        String collectionName = holder.get(2);

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = db.getCollection(collectionName);

            // find code goes here
            MongoCursor<Document> cursor = collection.find().iterator();

            // iterate code goes here
            try {
                while (cursor.hasNext()) {
                    String sessionInJsonForm = cursor.next().toJson();
                    JsonToSession jsonToSession = new JsonToSession(sessionInJsonForm, inMemoryEmployeeDataAccessObject, inMemoryEventDataAccessObject);
                    ClassSession session = jsonToSession.convert();
                    sessions.put(session.getSessionID(), session);
                }
            } finally {
                cursor.close();
            }
        }
    }

    public boolean save(HashMap<String, ClassSession> sessions) throws IOException {
        this.sessions.clear();
        this.sessions.putAll(sessions);

        try {
            this.save();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public ClassSession get(String sessionID) {
        return sessions.get(sessionID);
    }

    private void save() throws IOException {
        ArrayList<String> holder = getURIAndDBNames();
        String uri = holder.get(0);
        String databaseName = holder.get(1);
        String collectionName = holder.get(2);

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = db.getCollection(collectionName);

            // insert code goes here
            List<Document> documents = new ArrayList<>();


            for (ClassSession session : sessions.values()) {
                ArrayList<String> staffList = new ArrayList<>();
                for (Employee employee : session.listStaff()) {
                    staffList.add(employee.getUID());
                }

                // DateTimeSpanStorage
                ArrayList<String> dateList = new ArrayList<>();
                dateList.add(session.toCalendarEvent().getName());
                dateList.add(session.toCalendarEvent().getDescription());
                dateList.add(session.toCalendarEvent().getDateTimeSpan().getStart().toString().replace("T", " "));
                dateList.add(session.toCalendarEvent().getDateTimeSpan().getEnd().toString().replace("T", " "));

                Document eventDoc = new Document("sessionID", session.getSessionID()).
                        append("session_name", session.getName()).append("cal_event", dateList).
                        append("staff", staffList).append("location", session.getLocation()).
                        append("eventID", session.getEvent().getEventID());

                // This adds the employee information to the mongodb
                documents.add(eventDoc);

            }

            // Need to not use insertMany because it can create duplicate entries within mongodb.
            // Could search using userID and delete past data before inserting the new data.
            collection.deleteMany(new Document()); // Replaces all of the collection with nothing
            collection.insertMany(documents);
        }
    }


    /**
     * Return whether a session exists with their identifier.
     * @param sessionID
     * @return whether the session ID is stored inside the sessions Hashmap
     */
    public boolean existsByID(String sessionID) {
        return sessions.containsKey(sessionID);
    }

    public String getSessionsString() {
        StringBuilder sessions = new StringBuilder();
        for (Map.Entry<String, ClassSession> entry : this.sessions.entrySet()) {
            sessions.append(entry.getKey()).append("\n");
        }
        return sessions.toString();
    }

    public HashMap<String, ClassSession> getSessions() {
        return sessions;
    }

    private ArrayList<String> getURIAndDBNames() throws IOException {

        ArrayList<String> uriAndNames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFiles))) {
            String header = reader.readLine();

            assert header.equals("databaseLink,databaseName,collectionName");
            String row;
            while ((row = reader.readLine()) != null) {
                String[] col = row.split(",");
                uriAndNames.add(String.valueOf(col[headers.get("databaseLink")]));
                uriAndNames.add(String.valueOf(col[headers.get("databaseName")]));
                uriAndNames.add(String.valueOf(col[headers.get("collectionName")]));
            }
        }
        return uriAndNames;
    }
}
