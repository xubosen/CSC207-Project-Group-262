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
//public class FileEventDataAccessObject {
//    private final File databaseFiles;
//    private final Map<String, Integer> headers = new LinkedHashMap<>();
//
//    private static final HashMap<String, Event> events = new HashMap<>();
//    public FileEventDataAccessObject(String databasePath,
//                                     InMemoryEmployeeDataAccessObject inMemoryEmployeeDataAccessObject,
//                                      InMemoryCourseDataAccessObject inMemoryCourseDataAccessObject) throws IOException {
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
//                    String eventInJsonForm = cursor.next().toJson();
//                    JsonToEvent jsonToEvent = new JsonToEvent(eventInJsonForm, inMemoryCourseDataAccessObject,
//                            inMemoryEmployeeDataAccessObject);
//                    Event event = jsonToEvent.convert();
//                    events.put(event.getEventID(), event);
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//    }
//
//    public void save(Event event) throws IOException {
//        events.put(event.getEventID(), event);
//        this.save();
//    }
//
//    public Event get(String eventID) {
//        return events.get(eventID);
//    }
//
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
//
//            for (Event event : events.values()) {
//                ArrayList<String> staffList = new ArrayList<>();
//                for (Employee employee : event.listStaff().values()) {
//                    staffList.add(employee.getUID());
//                }
//
//                ArrayList<String> sessionsList = new ArrayList<>();
//
//                for (ClassSession session : event.getSessions().values()) {
//                    sessionsList.add(session.getSessionID());
//                }
//
//                Document eventDoc = new Document("name", event.getName()).
//                        append("eventID", event.getEventID()).append("course", event.getCourse()).
//                        append("sessions", sessionsList).append("staff", staffList).
//                        append("event_type", event.getClass());
//
//                // This adds the employee information to the mongodb
//                documents.add(eventDoc);
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
//     * Return whether a event exists with their identifier.
//     * @param eventID
//     * @return whether the event code is inside
//     */
//    public boolean existsByCode(String eventID) {
//        return events.containsKey(eventID);
//    }
//
//    /**
//     * Get the string form of the events in the DAO.
//     * @return string of events
//     */
//    public String getEventsString() {
//        StringBuilder events = new StringBuilder();
//        for (Map.Entry<String, Event> entry : this.events.entrySet()) {
//            events.append(entry.getKey()).append("\n");
//        }
//        return events.toString();
//    }
//
//    /**
//     * Getter for events Hashmap
//     * @return events Hashmap of the DAO
//     */
//    public HashMap<String, Event> getEvents() {
//        return events;
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
