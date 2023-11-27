package data_access;

import entity.Employee;

public interface EmployeeDataAccessInterface {
    boolean existsByName(String identifier);

    Employee findByName(String identifier);

    void save(Employee employee);
}