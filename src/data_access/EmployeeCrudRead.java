package data_access;

import com.mongodb.client.*;
import org.bson.Document;

/**
 * Just a test file to see if it can read from the database.
 */
public class EmployeeCrudRead {
    public static void main(String[] args) {
        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("employees");

            // find code goes here
            MongoCursor<Document> cursor = collection.find().iterator();

            // iterate code goes here
            try {
                while (cursor.hasNext()) {

                    System.out.println(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }
        }
    }
}