package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RemoveFromSessionViewModel extends ViewModel{
    public final String REMOVE_BUTTON_LABEL = "Remove";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private RemoveFromSessionState state = new RemoveFromSessionState();

    public RemoveFromSessionViewModel() {
        super("Remove User");
    }

    public RemoveFromSessionState getState() {
        return state;
    }

    public void setState(RemoveFromSessionState state) {
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
