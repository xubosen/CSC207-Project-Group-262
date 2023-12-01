package interface_adapter.create_session;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateSessionViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CreateSessionState state = new CreateSessionState();

    public CreateSessionViewModel() {
        super("Create Event");
    }

    public CreateSessionState getState() {
        return state;
    }

    public void setState(CreateSessionState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
