package entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * entity.Calendar object used to store calendar events
 */

public class Calendar {

    private HashMap<DateTimeSpan, CalendarEvent> events;

    public Calendar(){
        events = new HashMap<DateTimeSpan, CalendarEvent>();
    }

    /**
     * Adds an event to the calendar
     * @param event
     * @return true if the event does not overlap with any other events
     */
    public boolean addEvent(CalendarEvent event){
        DateTimeSpan newTimeSpan = event.getDateTimeSpan();
        for (DateTimeSpan existingTime : events.keySet()) {
            if (existingTime.overlaps(newTimeSpan)) {
                return false;
            }
        }
        events.put(event.getDateTimeSpan(), event);
        return true;
    }

    /**
     * Removes an event from the calendar
     * @param event
     * @return true if the event was removed
     */
    public boolean removeEvent(CalendarEvent event) {
        if (events.containsKey(event.getDateTimeSpan())) {
            events.remove(event.getDateTimeSpan());
            return true;
        }
        return false;
    }

    /**
     * @return a copy of the events in the calendar as a list
     */
    public ArrayList<CalendarEvent> listCalendarEvents() {
        return new ArrayList<>(events.values());
    }
}