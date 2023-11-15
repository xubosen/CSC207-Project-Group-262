package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventAdditionViewModel extends ViewModel{
    public final String EVENT_ADDITION_BUTTON_LABEL = "Invite";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private EventAdditionState state = new EventAdditionState();

    public EventAdditionViewModel() {
        super("Event Enrollment");
    }

    public EventAdditionState getState() {
        return state;
    }

    public void setState(EventAdditionState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        System.out.println(state.getUserInvited());
        System.out.println(state.isInviteSuccessful());
        System.out.println(state.getInviteResponseMessage());
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}