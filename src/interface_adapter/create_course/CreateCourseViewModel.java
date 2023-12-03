package interface_adapter.create_course;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateCourseViewModel extends ViewModel {
    public final String CREATE_COURSE_BUTTON_LABEL = "Create Course";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CreateCourseState state = new CreateCourseState();
    public CreateCourseViewModel() {
        super("create course view");
    }

    public CreateCourseState getState() {
        return state;
    }

    public void setState(CreateCourseState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
