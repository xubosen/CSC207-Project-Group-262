package interface_adapter.create_event;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateEventViewModel extends ViewModel {
    public final String Create_EVENT_BUTTON_LABEL = "Create Event";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CreateEventState state = new CreateEventState();
    public CreateEventViewModel() {
        super("create event view");
    }

    public CreateEventState getState() {
        return state;
    }

    public void setState(CreateEventState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
