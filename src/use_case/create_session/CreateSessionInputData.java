package use_case.create_session;

import entity.DateTimeSpan;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateSessionInputData {
    private String sessionID;
    private String sessionName;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String parentEventID;


    public CreateSessionInputData(String sessionID, String sessionName, String description, String location,
                                  LocalDateTime startTime, LocalDateTime endTime, String parentEventID) {
        this.sessionID = sessionID;
        this.sessionName = sessionName;
        this.description = description;
        this.location = location;

        this.startTime = startTime;
        this.endTime = endTime;

        this.parentEventID = parentEventID;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public DateTimeSpan getDateTimeSpan() {
        return new DateTimeSpan(startTime, endTime);
    }

    public String getParentEventID() {
        return parentEventID;
    }
}