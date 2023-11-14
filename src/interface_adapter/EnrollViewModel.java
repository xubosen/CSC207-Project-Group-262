package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EnrollViewModel extends ViewModel{
    public final String ENROLL_BUTTON_LABEL = "Invite";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private EnrollState state = new EnrollState();

    public EnrollViewModel() {
        super("Course Enrollment");
    }

    public EnrollState getState() {
        return state;
    }

    public void setState(EnrollState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        System.out.println(state.getUserInvited());
        System.out.println(state.isInviteSuccessful());
        System.out.println(state.getInviteResponseMessage());
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}