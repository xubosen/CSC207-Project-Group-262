package interface_adapter.get_events;

import java.util.ArrayList;

public class GetEventState {
    private ArrayList<String> eventIDs = new ArrayList<>();

    public GetEventState() {
    }

    public ArrayList<String> getEventIDs() {
        return eventIDs;
    }

    public void setEventIDs(ArrayList<String> eventIDs) {
        this.eventIDs = eventIDs;
    }
}
