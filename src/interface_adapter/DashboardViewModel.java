package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel extends ViewModel {

    public final String TITLE_LABEL = "Dashboard View";

    // Define labels for dashboard elements if necessary
    public final String COURSES_LABEL = "Courses";
    public final String EVENTS_LABEL = "Events";
    public final String SESSIONS_LABEL = "Sessions";
    public final String CALENDAR_LABEL = "Calendar";
    public final String LEAVES_OF_ABSENCES_LABEL = "Leaves of Absences";
    public final String EMPLOYEE_INFO_LABEL = "Employee Information";

    private DashboardState state = new DashboardState(); // You will need to define a DashboardState class

    public DashboardViewModel() {
        super("dashboard");
    }

    public void setState(DashboardState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This method allows for notifying the view of state changes
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public DashboardState getState() {
        return state;
    }

    // Additional methods for managing dashboard-specific data and behavior
}
