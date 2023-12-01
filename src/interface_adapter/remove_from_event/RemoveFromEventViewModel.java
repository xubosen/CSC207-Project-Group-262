package interface_adapter.remove_from_event;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RemoveFromEventViewModel extends ViewModel {
    public final String REMOVE_BUTTON_LABEL = "Remove";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private RemoveFromEventState state = new RemoveFromEventState();

    public RemoveFromEventViewModel() {
        super("Remove User");
    }

    public RemoveFromEventState getState() {
        return state;
    }

    public void setState(RemoveFromEventState state) {
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}