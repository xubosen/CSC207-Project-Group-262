package data_access;

import entity.Employee;
import entity.EmployeeFactory;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileEmployeeDataAccessObject implements EmployeeDataAccessInterface {

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, Employee> accounts = new HashMap<>();
    private EmployeeFactory employeeFactory;

    public FileEmployeeDataAccessObject(String csvPath, EmployeeFactory employeeFactory) throws IOException {
        this.employeeFactory = employeeFactory;

        csvFile = new File(csvPath);
        headers.put("userID", 0);
        headers.put("name", 1);
        headers.put("email", 2);
        headers.put("password", 3);

        if (csvFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();
                assert header.equals("userID,name,email,password");

                String row;
                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",");
                    String userID = col[headers.get("userID")];
                    String name = col[headers.get("name")];
                    String email = col[headers.get("email")];
                    String password = col[headers.get("password")];
                    Employee employee = employeeFactory.create(userID, name, email, password);
                    accounts.put(userID, employee);
                }
            }
        }
    }

    @Override
    public void save(Employee employee) {
        accounts.put(employee.getUserID(), employee);
        this.save();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (Employee employee : accounts.values()) {
                String line = "%s,%s,%s,%s".formatted(
                        employee.getUserID(), employee.getName(), employee.getEmail(), employee.getPassword());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.values().stream().anyMatch(emp -> emp.getName().equals(identifier));
    }

    @Override
    public Employee findByName(String identifier) {
        return accounts.get(identifier);
    }
}
