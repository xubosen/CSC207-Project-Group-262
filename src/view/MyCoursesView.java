package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCoursesView extends JPanel implements ActionListener {
    public final String viewName = "my courses view";
    private final JButton csc207Button = new JButton("CSC207");
    private final String mySessionsViewName = "my sessions view";
    private final ViewManagerModel viewManagerModel;

    private final JButton backButton = new JButton("Back");

    public MyCoursesView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
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
        gbc.gridy = 1; // Align to the second row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        csc207Button.setPreferredSize(new Dimension(csc207Button.getPreferredSize().width, 150));
        add(csc207Button, gbc);
        csc207Button.addActionListener(this);
    }

    private void makeBackButton(GridBagConstraints gbc) {
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 2; // Align to the second row
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
        }
    }
}