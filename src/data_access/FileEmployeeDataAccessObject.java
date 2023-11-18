package data_access;

import com.mongodb.client.*;
import entity.ClassSession;
import entity.Course;
import entity.Employee;
import entity.EmployeeFactory;
import entity.TeachingAssistant;
import entity.TeachingAssistantFactory;
import org.bson.Document;



import java.io.*;
import java.util.*;

public class FileEmployeeDataAccessObject implements Serializable {
    public final Map<String, Employee> accounts = new HashMap<>();

    private EmployeeFactory employeeFactory;

    public FileEmployeeDataAccessObject(EmployeeFactory employeeFactory) throws IOException {
        this.employeeFactory = employeeFactory;


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
                    String employeeInJsonForm = cursor.next().toJson();
                    JsonToEmployee jsonToEmployee = new JsonToEmployee(employeeFactory, employeeInJsonForm);
                    Employee employee = jsonToEmployee.convert();
                    accounts.put(employee.getUID(), employee);
                }
            } finally {
                cursor.close();
            }
        }
    }


    public void save(Employee employee) {
        accounts.put(employee.getUID(), employee);
        this.save();
    }

    public Employee get(String userID) {
        return accounts.get(userID);
    }

    private void save() {
        String uri = "mongodb+srv://shinyarkeus:KWhU7JJK5BiBcQ0c@projecthr.u9tq0zb.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("hrsystem_database");
            MongoCollection<Document> collection = db.getCollection("employees");

            // insert code goes here
            List<Document> documents = new ArrayList<>();

            for (Employee employees : accounts.values()) {
                Document employeeDoc = new Document("userID", employees.getUID()).
                        append("password", employees.getPassword()).append("name", employees.getName()).
                        append("email", employees.getEmail()).append("courses", employees.getCourses()).
                        append("sessions", employees.getSessions()).append("role", employees.getClass().toString());

                // This adds the employee information to the mongodb
                documents.add(employeeDoc);

            }

            // Need to not use insertMany because it can create duplicate entries within mongodb.
            // Could search using userID and delete past data before inserting the new data.
            collection.insertMany(documents);
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
        for (Map.Entry<String, Employee> entry : this.accounts.entrySet()) {
            accounts.append(entry.getKey()).append("\n");
        }
        return accounts.toString();
    }

    public Map<String, Employee> getAccount() {
        return accounts;
    }
}
