package data_access;

import entity.ClassSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemorySessionDataAccessObject {
    private HashMap<String, ClassSession> session = new HashMap<String, ClassSession>();

    public InMemorySessionDataAccessObject(HashMap<String, ClassSession> sessions) {
        session = sessions;
    }

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

    public void addSession(ClassSession session1) {
        session.put(session1.getSessionID(), session1);
    }

    public boolean removeSession(ClassSession session1) {
        if (existsByID(session1.getSessionID())) {
            session.remove(session1.getSessionID());
            return true;
        }
        return false;
    }
}
