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

public class FileEmployeeDataAccessObject {
    private final File databaseFiles;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    public final HashMap<String, Employee> accounts = new HashMap<>();

    public FileEmployeeDataAccessObject(String databasePath) throws IOException {
        this.databaseFiles = new File(databasePath);

//        databaseFiles = new File(databasePath);
//        headers.put("databaseLink", 0);
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFiles))) {
//            String header = reader.readLine();
//
//            assert header.equals("databaseLink");
//            String row;
//            while ((row = reader.readLine()) != null) {
//                String[] col = row.split(",");
//                String uri = String.valueOf(col[headers.get("databaseLink")]);
//            }
//        }

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
                    JsonToEmployee jsonToEmployee = new JsonToEmployee(employeeInJsonForm);
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

                ArrayList<String> courseList = new ArrayList<>();

                for (Course course : employees.getCourses().values()) {
                    courseList.add(course.getCourseCode());
                }

                ArrayList<String> sessionList = new ArrayList<>();

                for (ClassSession session : employees.getSessions().values()) {
                    sessionList.add(session.getSessionID());
                }

                Document employeeDoc = new Document("userID", employees.getUID()).
                        append("password", employees.getPassword()).append("name", employees.getName()).
                        append("email", employees.getEmail()).append("courses", courseList).
                        append("sessions", sessionList).append("role", employees.getClass().toString());

                documents.add(employeeDoc);

            }

            collection.deleteMany(new Document()); // Replaces all of the collection with nothing
            collection.insertMany(documents); // Re adds all the current generated employees into the database
            // This was done to prevent any duplicate entry of employees.
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

    public HashMap<String, Employee> getAccount() {
        return accounts;
    }
}
