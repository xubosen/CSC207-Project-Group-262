package data_access.in_memory_dao;

import entity.Employee;

import java.util.HashMap;
import java.util.Set;

public class InMemoryEmployeeDataAccessObject {
    private HashMap<String, Employee> employees;

    public InMemoryEmployeeDataAccessObject() {
        employees = new HashMap<String, Employee>();
    }

    public InMemoryEmployeeDataAccessObject(HashMap<String, Employee> employees) {
        this.employees = new HashMap<>(employees);
    }

    public void save(Employee employee) {
        this.employees.put(employee.getUID(), employee);
    }

    public boolean existsByID(String userId) {
        return employees.containsKey(userId);
    }

    public Employee getByID(String userId) {
        return employees.get(userId);
    }

    public Set<String> getAllIDs() {
        return employees.keySet();
    }

    public HashMap<String, Employee> getEmployees() {
        return new HashMap<>(employees);
    }

    public void addEmployee(Employee employee) {
        this.employees.put(employee.getUID(), employee);
    }

//    public boolean removeEmployee(Employee employee) {
//        if (this.employees.containsKey(employee.getUID())){
//            this.employees.remove(employee.getUID());
//            return true;
//        }
//        return false;
//    }
}
