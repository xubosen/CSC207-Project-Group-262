package interface_adapter;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InviteToSessionViewModel extends ViewModel{
    public final String Invite_BUTTON_LABEL = "Invite";
    public final String CLOSE_BUTTON_LABEL = "Close";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private InviteToSessionState state = new InviteToSessionState();

    public InviteToSessionViewModel() {
        super("Session Enrollment");
    }

    public InviteToSessionState getState() {
        return state;
    }

    public void setState(InviteToSessionState state) {
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
