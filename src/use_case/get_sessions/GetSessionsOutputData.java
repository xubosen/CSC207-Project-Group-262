package use_case.get_sessions;

import java.util.ArrayList;

public class GetSessionsOutputData {
    private ArrayList<String> sessions;

    public GetSessionsOutputData(ArrayList<String> sessions) {
        this.sessions = sessions;
    }

    public ArrayList<String> getSessions() {
        return sessions;
    }
}
