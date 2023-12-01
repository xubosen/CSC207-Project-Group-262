package view.Organizational_Views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interface_adapter.ViewManagerModel;

public class DashboardView extends JPanel implements ActionListener {

    public final String viewName = "dashboard";

    private final ViewManagerModel viewManagerModel;

    private JLabel welcomeLabel; // Label for the welcome message

    private final JButton coursesButton = new JButton("Courses");
    private final JButton eventsButton = new JButton("Events");
    private final JButton sessionsButton = new JButton("Sessions");
    private final JButton calendarButton = new JButton("Calendar");
    private final JButton leavesOfAbsencesButton = new JButton("Leaves of Absences");
    private final JButton employeeInformationButton = new JButton("Employee Information");

    // Variables for linking to other views
    private String myCoursesViewName;
    private String myEventsViewName;
    private String mySessionsViewName;
    private String leaveRequestViewName;
    private String calendarViewName;


    public DashboardView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;

        // Set layout to BoxLayout for horizontal alignment
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Create welcome label panel and set preferred size
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomeLabel = new JLabel("Welcome, Simon Xu", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(welcomeLabel);
        welcomePanel.setPreferredSize(new Dimension(400, 600)); // 400px width

        // Panel for buttons with GridLayout
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns, and gaps
        buttonsPanel.setPreferredSize(new Dimension(600, 600)); // 600px width
        addStyledButton(coursesButton, buttonsPanel);
        addStyledButton(eventsButton, buttonsPanel);
        addStyledButton(sessionsButton, buttonsPanel);
        addStyledButton(calendarButton, buttonsPanel);
        addStyledButton(leavesOfAbsencesButton, buttonsPanel);
        addStyledButton(employeeInformationButton, buttonsPanel);

        // Add panels to DashboardView
        add(welcomePanel);
        add(buttonsPanel);
    }

    private void addStyledButton(JButton button, JPanel panel) {
        styleButton(button);
        panel.add(button); // Add the button to the panel
        button.addActionListener(this);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(new Color(10, 186, 181)); // Example color - Cornflower Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == coursesButton) {
            viewManagerModel.setActiveView(myCoursesViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == eventsButton) {
            viewManagerModel.setActiveView(myEventsViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == sessionsButton) {
            viewManagerModel.setActiveView(mySessionsViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == calendarButton) {
            viewManagerModel.setActiveView(calendarViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == leavesOfAbsencesButton) {
            viewManagerModel.setActiveView(leaveRequestViewName);
            viewManagerModel.firePropertyChanged();
        }
    }

    public void linkViews(String myCoursesViewName, String myEventsViewName, String mySessionsViewName) {
        this.myCoursesViewName = myCoursesViewName;
        this.myEventsViewName = myEventsViewName;
        this.mySessionsViewName = mySessionsViewName;
    }
}
