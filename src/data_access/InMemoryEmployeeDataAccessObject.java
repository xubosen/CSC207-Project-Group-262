package data_access;

import entity.Employee;
import java.util.HashMap;
import java.util.Set;

public class InMemoryEmployeeDataAccessObject {
    private HashMap<String, Employee> employee;

    public InMemoryEmployeeDataAccessObject() {
        employee = new HashMap<String, Employee>();
    }

    public InMemoryEmployeeDataAccessObject(HashMap<String, Employee> employees) {
        employee = employees;
    }

    public void save(Employee employee) {
        this.employee.put(employee.getUserID(), employee);
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

    public void addEmployee(Employee employee) {
        this.employee.put(employee.getUserID(), employee);
    }

    public boolean removeEmployee(Employee employee) {
        if (this.employee.containsKey(employee.getUserID())){
            this.employee.remove(employee.getUserID());
            return true;
        }
        return false;
    }

}
