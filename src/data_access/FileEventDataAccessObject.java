package data_access;

import com.mongodb.client.*;
import entity.*;
import org.bson.Document;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class FileEventDataAccessObject {

    private static final HashMap<String, Event> events = new HashMap<>();
    public FileEventDataAccessObject(InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
                                      InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject) {

        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("events");

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

    public void save(Event event) {
        events.put(event.getEventID(), event);
        this.save();
    }

    public Event get(String eventID) {
        return events.get(eventID);
    }

    private void save() {
        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("courses");

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

                Document eventDoc = new Document("name", event.getName()).
                        append("eventID", event.getEventID()).append("course", event.getCourse()).
                        append("sessions", sessionsList).append("staff", staffList).
                        append("event_type", event.getClass());

                // This adds the employee information to the mongodb
                documents.add(eventDoc);

            }

            // Need to not use insertMany because it can create duplicate entries within mongodb.
            // Could search using userID and delete past data before inserting the new data.
            collection.insertMany(documents);
        }
    }


    /**
     * Return whether a course exists with their identifier.
     * @param eventID
     * @return whether the course code is inside
     */
    public boolean existsByCode(String eventID) {
        return events.containsKey(eventID);
    }

    public String getEventsString() {
        StringBuilder events = new StringBuilder();
        for (Map.Entry<String, Event> entry : this.events.entrySet()) {
            events.append(entry.getKey()).append("\n");
        }
        return events.toString();
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }
}
