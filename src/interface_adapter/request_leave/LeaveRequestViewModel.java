package interface_adapter.request_leave;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LeaveRequestViewModel extends ViewModel {
    // Constants for button labels or other UI elements specific to the leave request view
    public final String REQUEST_LEAVE_BUTTON_LABEL = "Request Leave";
    public final String CANCEL_BUTTON_LABEL = "Cancel";

    // State attributes specific to leave request creation
    private boolean leaveCreationSuccessful;
    private String leaveCreationResponseMessage;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LeaveRequestViewModel() {
        super("leave request view");
    }

    // Setter for leave creation success status
    public void setLeaveCreationSuccessful(boolean leaveCreationSuccessful) {
        this.leaveCreationSuccessful = leaveCreationSuccessful;
        firePropertyChanged();
    }

    // Setter for leave creation response message
    public void setLeaveCreationResponseMessage(String leaveCreationResponseMessage) {
        this.leaveCreationResponseMessage = leaveCreationResponseMessage;
        firePropertyChanged();
    }

    // Fire property change to notify observers
    public void firePropertyChanged() {
        support.firePropertyChange("leaveCreationStatus", null, new LeaveCreationStatus(leaveCreationSuccessful, leaveCreationResponseMessage));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Inner class to encapsulate the leave creation status
    public class LeaveCreationStatus {
        private final boolean successful;
        private final String message;

        public LeaveCreationStatus(boolean successful, String message) {
            this.successful = successful;
            this.message = message;
        }

        // Getters for the status and message
        public boolean isSuccessful() {
            return successful;
        }

        public String getMessage() {
            return message;
        }
    }
}
