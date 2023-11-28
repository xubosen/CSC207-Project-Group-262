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
    private final HashMap<String, Employee> accounts = new HashMap<>();

    /**
     * Initializer for the EmployeeDAO
     * @param databasePath
     * @throws IOException
     */
    public FileEmployeeDataAccessObject(String databasePath) throws IOException {
        // TODO: Need to figure out how to convert this to clean Architecture.
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
            // So this would be the part that we would convert to an interactor that creates the employee entities
            // and put it into accounts.
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
