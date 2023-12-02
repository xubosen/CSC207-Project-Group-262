package interface_adapter.remove_from_course;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RemoveFromCourseViewModel extends ViewModel {
    public final String REMOVE_BUTTON_LABEL = "Remove";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private RemoveFromCourseState state = new RemoveFromCourseState();

    public RemoveFromCourseViewModel() {
        super("Remove User");
    }

    public RemoveFromCourseState getState() {
        return state;
    }

    public void setState(RemoveFromCourseState state) {
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
