package interface_adapter.get_sessions;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetSessionsViewModel extends ViewModel {
    private GetSessionsState state = new GetSessionsState();
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GetSessionsViewModel() {
        super("Get Sessions");
    }

    public GetSessionsState getState() {
        return state;
    }

    public void setState(GetSessionsState state) {
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
