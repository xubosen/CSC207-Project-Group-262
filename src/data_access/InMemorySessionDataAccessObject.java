package data_access;

import entity.ClassSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemorySessionDataAccessObject {
    private final Map<String, ClassSession> session = new HashMap<String, ClassSession>();

    public void save(ClassSession classSession) {
        this.session.put(classSession.getSessionID(), classSession);
    }

    public boolean existsByID(String sessionId) {
        return session.containsKey(sessionId);
    }

    public ClassSession getEvent(String sessionId) {
        return session.get(sessionId);
    }

    public Set<String> getAllEventIDS() {
        return session.keySet();
    }
}
