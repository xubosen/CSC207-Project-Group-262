package data_access;

import com.mongodb.BasicDBList;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.ClassSession;
import entity.Course;
import entity.TeachingAssistant;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class EmployeeCrudInsert {
    public static void main(String[] args) {
        // TODO: Still need to fix inserting a hashmap for Course and Sessions.
        // Maybe when inputting courses/session/staff/events could just be array list of ids.
        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("employees");

            // insert code goes here
            List<Document> documents = new ArrayList<>();

            TeachingAssistant simon = new TeachingAssistant("xubosen", "Simon",
                    "simonxu@mail.utoronto.ca", "456");
            TeachingAssistant yooham = new TeachingAssistant("yoohamj", "Yooham",
                    "yoohamj@mail.utoronto.ca", "789");
//
//            Course csc207 = new Course("Software Design", "CSC207", yooham);
//
//            yooham.addCourse(csc207);

            HashMap<String, String> temp = new HashMap<String, String>();

            Document xubosen = new Document("userID", simon.getUID()).
                    append("password", simon.getPassword()).append("name", simon.getName()).
                    append("email", simon.getEmail()).append("courses", temp).
                    append("sessions", simon.getSessions());

            Document yoohamj = new Document("userID", yooham.getUID()).
                    append("password", yooham.getPassword()).append("name", yooham.getName()).
                    append("email", yooham.getEmail()).append("courses", yooham.getCourses()).
                    append("sessions", yooham.getSessions()).append("role", yooham.getClass().toString());

            documents.add(xubosen);
            documents.add(yoohamj);



            collection.insertMany(documents);
        }
    }
}