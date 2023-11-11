package data_access;

import entity.Employee;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryEmployeeDataAccessObject {
    private final Map<String, Employee> employee = new HashMap<String, Employee>();

    @Override
    public void save(Employee employee) {
        this.employee.put(employee.getUID(), employee);
    }

    public boolean existsByID(String userId) {
        return employee.containsKey(userId);
    }

    public Employee getEmployee(String userId) {
        return employee.get(userId);
    }

    public Set<String> getAllEmployeeIDs() {
        return employee.keySet();
    }

}
