package entity;

import java.util.HashMap;
import java.util.Objects;

public class TeachingAssistant extends Employee {
    public TeachingAssistant(String userID, String name, String email, String password) {
        super(userID, name, email, password);
        this.role = "ta";
    }
}