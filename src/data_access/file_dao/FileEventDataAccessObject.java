package data_access.file_dao;

import com.mongodb.client.*;
import data_access.in_memory_dao.InMemoryCourseDataAccessObject;
import data_access.in_memory_dao.InMemoryEmployeeDataAccessObject;
import entity.*;
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

public class FileEventDataAccessObject {
    private final File databaseFiles;
    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private static final HashMap<String, Event> events = new HashMap<>();
    public FileEventDataAccessObject(String databasePath,
                                     InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
                                      InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject) throws IOException {
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
                    String eventInJsonForm = cursor.next().toJson();
                    JsonToEvent jsonToEvent = new JsonToEvent(eventInJsonForm, inMemoryCourseDataAccessObject,
                            inMemoryEmployeeDataAccessObject);
                    Event event = jsonToEvent.convert();
                    events.put(event.getEventID(), event);
                }
            } finally {
                cursor.close();
            }
        }
    }

    public boolean save(HashMap<String, Event> events) throws IOException {
        this.events.clear();
        this.events.putAll(events);
        try {
            this.save();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Event get(String eventID) {
        return events.get(eventID);
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


            for (Event event : events.values()) {
                ArrayList<String> staffList = new ArrayList<>();

                for (Employee employee : event.listStaff().values()) {
                    staffList.add(employee.getUID());
                }

                ArrayList<String> sessionsList = new ArrayList<>();

                for (ClassSession session : event.getSessions().values()) {
                    sessionsList.add(session.getSessionID());
                }

                Document eventDoc = new Document("name", event.getName());
                eventDoc.append("eventID", event.getEventID());
                eventDoc.append("course", event.getCourse().getCourseCode());
                eventDoc.append("sessions", sessionsList);
                eventDoc.append("staff", staffList);
                eventDoc.append("event_type", event.getClass().toString());

                // This adds the employee information to the mongodb
                documents.add(eventDoc);
            }

            // Need to not use insertMany because it can create duplicate entries within mongodb.
            // Could search using userID and delete past data before inserting the new data.
            collection.deleteMany(new Document()); // Replaces all of the collection with nothing
            collection.insertMany(documents);
        } catch (Exception e) {
        }
    }

    /**
     * Getter for events Hashmap
     * @return events Hashmap of the DAO
     */
    public HashMap<String, Event> getEvents() {
        return events;
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