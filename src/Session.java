import entity.CalendarEvent;

public class Session {
    private CalendarEvent dateTime;
    // private ? people // TODO: need to figure out what data structure to use
    private String location;
    private Event event;
    private boolean hasOccured;
    private String sessionID;

    public Session(String location, Event event, String sessionID) {
        // TODO: implement this method & change the parameters
        this.location = location;
        this.event = event;
        this.hasOccured = hasOccured;
        this.sessionID = sessionID;
    }

    public boolean addPerson(Employee person) {
        // TODO: implement this method
        return true;
    }

    public boolean removePerson(String userID) {
        // TODO: implement this method
        return true;
    }

    public Event getEvent() {
        return this.event;
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public String getLocation() {
        return this.location;
    }

    public CalendarEvent getDateTime() {
        return this.dateTime;
    }

    public boolean changeLocation(String newLocation) {
        this.location = newLocation;
        return true;
    }

    public boolean changeTime(CalendarEvent newTime) {
        this.dateTime = newTime;
        return true;
    }
}
