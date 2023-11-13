package data_access;

import entity.Employee;
import java.util.HashMap;
import java.util.Set;

public class InMemoryEmployeeDataAccessObject {
    private final HashMap<String, Employee> employee = new HashMap<String, Employee>();

    public void save(Employee employee) {
        this.employee.put(employee.getUID(), employee);
    }

    public boolean existsByID(String userId) {
        return employee.containsKey(userId);
    }

    public Employee getByID(String userId) {
        return employee.get(userId);
    }

    public Set<String> getAllIDs() {
        return employee.keySet();
    }

}
