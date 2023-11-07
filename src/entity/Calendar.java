package entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * entity.Calendar object used to store calendar events
 */

public class Calendar {

    private HashMap<DateTimeSpan, CalendarEvent> timeToCalEvents;

    public Calendar(){
        timeToCalEvents = new HashMap<DateTimeSpan, CalendarEvent>();
    }

    /**
     * Adds an event to the calendar
     * @param calEvent
     * @return true if the event does not overlap with any other events
     */
    public boolean addEvent(CalendarEvent calEvent){
        DateTimeSpan newTimeSpan = calEvent.getDateTimeSpan();

        // Loop through all existing events and check if the new event overlaps with any of them
        for (DateTimeSpan existingTime : timeToCalEvents.keySet()) {

            // If the new event overlaps with any existing event, return false
            if (existingTime.overlaps(newTimeSpan)) {
                return false;
            }
        }

        // If the new event does not overlap with any existing events, add it to the calendar and return true
        timeToCalEvents.put(calEvent.getDateTimeSpan(), calEvent);
        return true;
    }

    /**
     * Removes an event from the calendar
     * @param calEvent
     * @return true if the event was removed
     */
    public boolean removeEvent(CalendarEvent calEvent) {
        if (timeToCalEvents.containsKey(calEvent.getDateTimeSpan())) {
            timeToCalEvents.remove(calEvent.getDateTimeSpan());
            return true;
        }
        return false;
    }

    /**
     * @return a copy of the calendar events in the calendar as a list
     */
    public ArrayList<CalendarEvent> listCalendarEvents() {
        return new ArrayList<>(timeToCalEvents.values());
    }
}