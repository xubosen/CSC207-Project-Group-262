package interface_adapter.create_session;

import java.time.LocalDateTime;

public class CreateSessionState {
    private String sessionID = "";
    private String sessionName = "";
    private String description = "";
    private String location = "";
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String parentEventID = "";
    private boolean sessionCreationSuccessful = false;
    private String sessionCreationResponseMessage = "";

    public CreateSessionState(CreateSessionState copy) {
        this.sessionID = copy.getSessionID();
        this.sessionName = copy.getSessionName();
        this.description = copy.getDescription();
        this.location = copy.getLocation();
        this.startTime = copy.getStartTime();
        this.endTime = copy.getEndTime();
        this.parentEventID = copy.getParentEventID();
        this.sessionCreationSuccessful = copy.isSessionCreationSuccessful();
        this.sessionCreationResponseMessage = copy.getSessionCreationResponseMessage();
    }

    /**
     * Initializer if there is no input.
     */
    public CreateSessionState() {
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location of the session.
     * @return The location of the session.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the session.
     * @param location The location of the session.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the start time of the session.
     * @return The start time of the session.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the session.
     * @param startTime The start time of the session.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the session.
     * @return The end time of the session.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the session.
     * @param endTime The end time of the session.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the parent event ID of the session.
     * @return The parent event ID of the session.
     */
    public String getParentEventID() {
        return parentEventID;
    }

    /**
     * Sets the parent event ID of the session.
     * @param parentEventID The parent event ID of the session.
     */
    public void setParentEventID(String parentEventID) {
        this.parentEventID = parentEventID;
    }

    /**
     * Gets whether the session creation was successful.
     * @return Whether the session creation was successful.
     */
    public boolean isSessionCreationSuccessful() {
        return sessionCreationSuccessful;
    }

    /**
     * Sets whether the session creation was successful.
     * @param sessionCreationSuccessful Whether the session creation was successful.
     */
    public void setSessionCreationSuccessful(boolean sessionCreationSuccessful) {
        this.sessionCreationSuccessful = sessionCreationSuccessful;
    }

    /**
     * Gets the session creation response message.
     * @return The session creation response message.
     */
    public String getSessionCreationResponseMessage() {
        return sessionCreationResponseMessage;
    }

    /**
     * Sets the session creation response message.
     * @param sessionCreationResponseMessage The session creation response message.
     */
    public void setSessionCreationResponseMessage(String sessionCreationResponseMessage) {
        this.sessionCreationResponseMessage = sessionCreationResponseMessage;
    }
}