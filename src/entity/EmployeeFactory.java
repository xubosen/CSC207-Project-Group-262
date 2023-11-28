package entity;

import java.util.HashMap;
import java.util.Objects;

import java.time.LocalDateTime;

public class EmployeeFactory {
    /**
     * Requires: password is valid.
     * @param name
     * @param password
     * @return
     */

    public Employee create(String userID, String name, String email, String password) {
        return new Employee(userID, name, email, password);
    }
}
