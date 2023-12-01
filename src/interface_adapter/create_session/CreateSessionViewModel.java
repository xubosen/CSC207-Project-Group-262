package interface_adapter.create_session;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateSessionViewModel extends ViewModel {
    public final String Create_EVENT_BUTTON_LABEL = "Create Event";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // TODO: complete the todo of asking simon inside the CreateCourseState Class
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
        System.out.println(state.getEventID());
        System.out.println(state.getEventName());
        System.out.println(state.getEventCreationResponseMessage());
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
