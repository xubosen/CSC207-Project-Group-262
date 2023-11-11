package data_access;

import entity.Employee;
import entity.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryEventDataAccessObject {
    private final Map<String, Event> event = new HashMap<String, Event>();

    public void save(Event event) {
        this.event.put(event.getEventId(), event);
    }

    public boolean existsByID(String eventId) {
        return event.containsKey(eventId);
    }

    public Event getEvent(String eventId) {
        return event.get(eventId);
    }

    public Set<String> getAllEventIDS() {
        return event.keySet();
    }
}
