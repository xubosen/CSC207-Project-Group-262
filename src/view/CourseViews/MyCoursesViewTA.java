package view.CourseViews;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// TODO: Need to change this to not have a hard coded csc207 button and instead pull from the employees courses.
public class MyCoursesViewTA extends JPanel implements ActionListener {
    public final String viewName = "my courses ta view";
    private final ViewManagerModel viewManagerModel;

    // Variables for UI elements
    private final JButton backButton = new JButton("Back");
    private final JButton csc207Button = new JButton("CSC207");

    // Variables for linking to other views
    private String dashboardViewName;


    public MyCoursesViewTA(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeBackButton(gbc);
        makeCourseButtons(gbc);
    }

    private GridBagConstraints formatScreenLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private void makeHeading(GridBagConstraints gbc) {
        JLabel headingLabel = new JLabel("My Courses", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font for the heading
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 0; // Align to the first row
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        add(headingLabel, gbc);
    }


    private void makeBackButton(GridBagConstraints gbc) {
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 1; // Align to the fourth row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 100));
        add(backButton, gbc);
        backButton.addActionListener(this);
    }

    private void makeCourseButtons(GridBagConstraints gbc) {
        csc207Button.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font for the button
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 2; // Align to the fifth row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        csc207Button.setPreferredSize(new Dimension(csc207Button.getPreferredSize().width, 100));
        add(csc207Button, gbc);
        csc207Button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        }
    }

    public void linkViews(String dashboardViewName) {
        this.dashboardViewName = dashboardViewName;
    }
}