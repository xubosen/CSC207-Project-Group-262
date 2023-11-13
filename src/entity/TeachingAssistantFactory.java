package entity;

import java.util.HashMap;

public class TeachingAssistantFactory {
    public TeachingAssistant create(String userID, String name, String email, String password,
                                    HashMap<String, ClassSession> sessions, HashMap<String, Course> courses) {
        TeachingAssistant teachingAssistant = new TeachingAssistant(userID, name, email, password);

        for (HashMap.Entry<String, ClassSession> entry : sessions.entrySet()) {
            // Maybe I don't need to include getting the key
            String key = entry.getKey();

            ClassSession value = entry.getValue();
            teachingAssistant.addSession(value);
        }

        for (HashMap.Entry<String, Course> entry : courses.entrySet()) {
            // Maybe I don't need to include getting the key
            String key = entry.getKey();

            Course value = entry.getValue();
            teachingAssistant.addCourse(value);
        }

        teachingAssistant.makeCalendar();

        return teachingAssistant;
    }
}
