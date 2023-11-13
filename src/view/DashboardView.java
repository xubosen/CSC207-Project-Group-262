package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardView extends JPanel implements ActionListener {

    public final String viewName = "dashboard";

    // Buttons for the dashboard
    private final JButton coursesButton = new JButton("Courses");
    private final JButton eventsButton = new JButton("Events");
    private final JButton sessionsButton = new JButton("Sessions");
    private final JButton calendarButton = new JButton("Calendar");
    private final JButton leavesOfAbsencesButton = new JButton("Leaves of Absences");
    private final JButton employeeInformationButton = new JButton("Employee Information");

    public DashboardView() {
        // Set layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Style and add buttons to the view
        styleButton(coursesButton);
        styleButton(eventsButton);
        styleButton(sessionsButton);
        styleButton(calendarButton);
        styleButton(leavesOfAbsencesButton);
        styleButton(employeeInformationButton);

        // Add action listeners to the buttons
        coursesButton.addActionListener(this);
        eventsButton.addActionListener(this);
        sessionsButton.addActionListener(this);
        calendarButton.addActionListener(this);
        leavesOfAbsencesButton.addActionListener(this);
        employeeInformationButton.addActionListener(this);
    }

    private void styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50)); // Set preferred size for medium-sized buttons
        this.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button click events
        // Your action handling code here
    }
}
