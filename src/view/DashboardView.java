package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interface_adapter.ViewManagerModel;

public class DashboardView extends JPanel implements ActionListener {

    public final String viewName = "dashboard";

    private final ViewManagerModel viewManagerModel;
    private final String leaveRequestViewName;
    private final String myCoursesViewName;
    private final String myEventsViewName;

    private final JButton coursesButton = new JButton("Courses");
    private final JButton eventsButton = new JButton("Events");
    private final JButton sessionsButton = new JButton("Sessions");
    private final JButton calendarButton = new JButton("Calendar");
    private final JButton leavesOfAbsencesButton = new JButton("Leaves of Absences");
    private final JButton employeeInformationButton = new JButton("Employee Information");

    public DashboardView(ViewManagerModel viewManagerModel, String leaveRequestViewName, String myCoursesViewName, String myEventsViewName) {
        this.viewManagerModel = viewManagerModel;
        this.leaveRequestViewName = leaveRequestViewName;
        this.myCoursesViewName = myCoursesViewName;
        this.myEventsViewName = myEventsViewName;

        // Set layout to GridLayout
        setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns, and gaps

        // Style and add buttons
        addStyledButton(coursesButton);
        addStyledButton(eventsButton);
        addStyledButton(sessionsButton);
        addStyledButton(calendarButton);
        addStyledButton(leavesOfAbsencesButton);
        addStyledButton(employeeInformationButton);
    }

    private void addStyledButton(JButton button) {
        styleButton(button);
        add(button);
        button.addActionListener(this);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(new Color(100, 149, 237)); // Example color - Cornflower Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leavesOfAbsencesButton) {
            viewManagerModel.setActiveView(leaveRequestViewName);
            viewManagerModel.firePropertyChanged();
        } else if  (e.getSource() == coursesButton) {
            viewManagerModel.setActiveView(myCoursesViewName);
            viewManagerModel.firePropertyChanged();
        }
        else if (e.getSource() == eventsButton) {  // Handle Events button click
            viewManagerModel.setActiveView("my events view");  // Assuming the view name for MyEventsView is "my events view"
            viewManagerModel.firePropertyChanged();
        }
        // Other links to be fixed.
    }
}
