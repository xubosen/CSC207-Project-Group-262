package data_access;

import entity.ClassSession;
import entity.Employee;
import entity.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryEventDataAccessObject {
    private Map<String, Event> event = new HashMap<String, Event>();
    public InMemoryEventDataAccessObject(HashMap<String, Event> events) {
        event = events;
    }

    public void save(Event event) {
        this.event.put(event.getEventID(), event);
    }

    public boolean existsByID(String eventID) {
        return event.containsKey(eventID);
    }

    public Event getEvent(String eventID) {
        return event.get(eventID);
    }

    public Set<String> getAllEventIDS() {
        return event.keySet();
    }

    public void addEvent(Event event) {
        this.event.put(event.getEventID(), event);
    }

    public boolean removeEvent(Event event) {
        if (existsByID(event.getEventID())) {
            this.event.remove(event.getEventID());
            return true;
        }
        return false;
    }
}
