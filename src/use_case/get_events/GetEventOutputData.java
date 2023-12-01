package use_case.get_events;

import java.util.ArrayList;

public class GetEventOutputData {
    private ArrayList<String> eventIDs;

    public GetEventOutputData(ArrayList<String> eventIDs) {
        this.eventIDs = eventIDs;
    }

    public ArrayList<String> getEventIDs() {
        return eventIDs;
    }
}
