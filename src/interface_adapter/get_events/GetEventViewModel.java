package interface_adapter.get_events;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetEventViewModel extends ViewModel {
    private GetEventState state = new GetEventState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GetEventViewModel() {
        super("Get Events");
    }

    public GetEventState getState() {
        return state;
    }

    public void setState(GetEventState state) {
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
