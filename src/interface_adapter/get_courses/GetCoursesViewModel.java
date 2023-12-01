package interface_adapter.get_courses;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GetCoursesViewModel extends ViewModel {
    private GetCoursesState state = new GetCoursesState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GetCoursesViewModel() {
        super("Get Courses");
    }

    public GetCoursesState getState() {
        return state;
    }

    public void setState(GetCoursesState state) {
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
