package interface_adapter.sign_up;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SignUpViewModel extends ViewModel {
    private SignUpState state;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SignUpViewModel() {
        super("Sign Up");
        state = new SignUpState();
    }

    public SignUpState getState() {
        return state;
    }

    public void setState(SignUpState state) {
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
