package entity;

public class Instructor extends Employee{
    public Instructor(String userID, String name, String email, String password) {
        super(userID, name, email, password);
        this.role = "instructor";
    }
}