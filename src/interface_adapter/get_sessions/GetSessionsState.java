package interface_adapter.get_sessions;

import java.util.ArrayList;

public class GetSessionsState {
    private ArrayList<String> sessions;

    public GetSessionsState() {
        sessions = new ArrayList<String>();
    }

    public ArrayList<String> getSessions() {
        return new ArrayList<>(sessions);
    }

    public void setSessions(ArrayList<String> sessions) {
        this.sessions = new ArrayList<>(sessions);
    }
}
