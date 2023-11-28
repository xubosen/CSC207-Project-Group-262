package data_access;

import com.mongodb.client.*;
import entity.ClassSession;
import entity.Employee;
import entity.Event;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// TODO: implement an interface in between so I can follow Clean Architecture
public class FileSessionDataAccessObject {
    private static final HashMap<String, ClassSession> sessions = new HashMap<>();
    public FileSessionDataAccessObject(InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
                                       InMemoryEventDataAccessObject inMemoryEventDataAccessObject) {

        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        // TODO: The uri shouldn't be hardcoded, it should be stored in a file so you can access multiple different databases.
        // Even in this case where we only have one database it is still recommended.
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("sessions");

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

    public void save(ClassSession session) {
        sessions.put(session.getSessionID(), session);
        this.save();
    }

    public ClassSession get(String sessionID) {
        return sessions.get(sessionID);
    }

    private void save() {
        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("sessions");

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
                dateList.add(session.toCalendarEvent().getDateTimeSpan().getStart().toString());
                dateList.add(session.toCalendarEvent().getDateTimeSpan().getEnd().toString());

                Document eventDoc = new Document("sessionID", session.getSessionID()).
                        append("name", session.getName()).append("cal_event", dateList).
                        append("staff", staffList).append("Location", session.getLocation()).
                        append("eventID", session.getEvent().getEventID());

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
}
