package view.CourseViews;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// TODO: Need to change this to not have a hard coded csc207 button and instead pull from the employees courses.
// Also MyCoursesView should have a checker for whether the person who is logged in is a Instructor or not.
// When you click on the courses to see events, that view should differ depending on if you are the course admin.
public class MyCoursesViewAdmin extends JPanel implements ActionListener {
    public final String viewName = "my courses view";
    private final JButton createCoursesButton = new JButton("Create Course");
    private final JButton csc207Button = new JButton("CSC207");
    private final String mySessionsViewName = "my sessions view";
    private final String createCoursesViewName = "create course view";
    private final ViewManagerModel viewManagerModel;

    private final JButton backButton = new JButton("Back");

    public MyCoursesViewAdmin(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeCreateCourseButton(gbc);
        makeCourseButton(gbc);
        makeBackButton(gbc);
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
    private void makeCourseButton(GridBagConstraints gbc) {
        csc207Button.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font for the button
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 2; // Align to the third row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        csc207Button.setPreferredSize(new Dimension(csc207Button.getPreferredSize().width, 150));
        add(csc207Button, gbc);
        csc207Button.addActionListener(this);
    }

    private void makeCreateCourseButton(GridBagConstraints gbc) {
        createCoursesButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font for the button
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 1; // Align to the second row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        createCoursesButton.setPreferredSize(new Dimension(createCoursesButton.getPreferredSize().width, 150));
        add(createCoursesButton, gbc);
        createCoursesButton.addActionListener(this);
    }

    private void makeBackButton(GridBagConstraints gbc) {
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 3; // Align to the fourth row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 150));
        add(backButton, gbc);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == csc207Button) {
            System.out.println("CSC207 button pressed");
            viewManagerModel.setActiveView(mySessionsViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView("dashboard");
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == createCoursesButton) {
            System.out.println("Create Courses button pressed.");
            viewManagerModel.setActiveView(createCoursesViewName);
            viewManagerModel.firePropertyChanged();
        }
    }
}