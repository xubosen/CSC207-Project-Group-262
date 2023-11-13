package entity;

import java.util.HashMap;

public class InstructorFactory {
    public Instructor create(String userID, String name, String email, String password,
                                    HashMap<String, ClassSession> sessions, HashMap<String, Course> courses) {
        Instructor instructor = new Instructor(userID, name, email, password);

        for (HashMap.Entry<String, ClassSession> entry : sessions.entrySet()) {
            // Maybe I don't need to include getting the key
            String key = entry.getKey();

            ClassSession value = entry.getValue();
            instructor.addSession(value);
        }

        for (HashMap.Entry<String, Course> entry : courses.entrySet()) {
            // Maybe I don't need to include getting the key
            String key = entry.getKey();

            Course value = entry.getValue();
            instructor.addCourse(value);
        }

        instructor.makeCalendar();

        return instructor;
    }
}
