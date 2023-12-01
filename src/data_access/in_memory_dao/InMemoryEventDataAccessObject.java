package data_access.in_memory_dao;

import entity.ClassSession;
import entity.Employee;
import entity.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryEventDataAccessObject {
    private Map<String, Event> events;

    public InMemoryEventDataAccessObject() {
        events = new HashMap<String, Event>();
    }

    public InMemoryEventDataAccessObject(HashMap<String, Event> events){
        this.events = new HashMap<String, Event>(events);
    }

    public void save(Event event) {
        this.events.put(event.getEventID(), event);
    }

    public void addEvent(Event event) {
        this.events.put(event.getEventID(), event);
    }

    public boolean existsByID(String eventID) {
        return events.containsKey(eventID);
    }

    public Event getByID(String eventID) {
        return events.get(eventID);
    }

    public boolean removeEvent(Event event) {
        if (existsByID(event.getEventID())) {
            this.events.remove(event.getEventID());
            return true;
        }
        return false;
    }

    public Set<String> getAllIDs() {
        return events.keySet();
    }

    public HashMap<String, Event> getEvents() {
        return new HashMap<>(events);
    }
}
