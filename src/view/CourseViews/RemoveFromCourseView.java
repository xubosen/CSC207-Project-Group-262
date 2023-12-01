package view.CourseViews;

import interface_adapter.*;
import interface_adapter.remove_from_course.RemoveFromCourseController;
import interface_adapter.remove_from_course.RemoveFromCourseState;
import interface_adapter.remove_from_course.RemoveFromCourseViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

public class RemoveFromCourseView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Remove From Course";
    private RemoveFromCourseViewModel removeFromCourseViewModel;
    private ViewManagerModel viewManagerModel;
    private RemoveFromCourseController removeFromCourseController;


    // Variables for setting up UI
    private JButton remove;
    private JButton close;
    private final String HEADING_TEXT = "Remove From Course";
    private String error_message = "";
    private JTextField userToRemoveInputField = new JTextField(10);
    private JTextField courseToRemoveFromInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);


    // Variables for linking to other views
    private String myCoursesViewName;


    public RemoveFromCourseView(RemoveFromCourseController controller, RemoveFromCourseViewModel viewModel,
                                ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.removeFromCourseController = controller;
        this.removeFromCourseViewModel = viewModel;
        removeFromCourseViewModel.addPropertyChangeListener(this);

        setupUI();
    }

    private void setupUI() {
        // Format UI
        GridBagConstraints gbc = formatScreenLayout();

        // Make components of UI
        JLabel headingLabel = makeHeading();
        JPanel buttonPanel = setUpButtons();
        formatInputFields();
        setUpErrorDisplayField();

        // Add components to UI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(headingLabel);

        JPanel inputPanel = setUpInputFields();
        mainPanel.add(inputPanel);

        mainPanel.add(errorDisplayField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        this.add(mainPanel, gbc);
    }

    private GridBagConstraints formatScreenLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private JLabel makeHeading() {
        JLabel heading = new JLabel(HEADING_TEXT);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        return heading;
    }

    private JPanel setUpInputFields() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JPanel userToRemovePanel = new JPanel();
        userToRemovePanel.add(new JLabel("User to remove:"));
        userToRemovePanel.add(userToRemoveInputField);
        inputPanel.add(userToRemovePanel);

        JPanel courseToRemoveFromPanel = new JPanel();
        courseToRemoveFromPanel.add(new JLabel("Course to remove from:"));
        courseToRemoveFromPanel.add(courseToRemoveFromInputField);
        inputPanel.add(courseToRemoveFromPanel);

        return inputPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        remove = new JButton(removeFromCourseViewModel.REMOVE_BUTTON_LABEL);
        remove.addActionListener(this);
        buttonPanel.add(remove);

        close = new JButton(removeFromCourseViewModel.CLOSE_BUTTON_LABEL);
        close.addActionListener(this);
        buttonPanel.add(close);

        return buttonPanel;
    }

    private void setUpErrorDisplayField() {
        errorDisplayField.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void formatInputFields() {
        setUpInputFieldFunctionality();
        setUpInputFieldStyle();
    }

    private void setUpInputFieldFunctionality() {
        RemoveFromCourseState currentState = removeFromCourseViewModel.getState();

        userToRemoveInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setUserRemoved(userToRemoveInputField.getText());
                removeFromCourseViewModel.setState(currentState);
            }
        });

        courseToRemoveFromInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setCourseRemovedFrom(courseToRemoveFromInputField.getText());
                removeFromCourseViewModel.setState(currentState);
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        userToRemoveInputField.setBorder(BorderFactory.createCompoundBorder(
                userToRemoveInputField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == remove) {
            RemoveFromCourseState currentState = removeFromCourseViewModel.getState();
            String userToRemove = currentState.getUserRemoved();
            String courseCode = currentState.getCourseRemovedFrom();

            removeFromCourseController.removeFromCourse(userToRemove, courseCode);

        // Close the window
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(myCoursesViewName);
            viewManagerModel.firePropertyChanged();

        // Something went wrong. Throw an error.
        } else {
            throw new RuntimeException("Something went wrong with the buttons");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Display the response message
        RemoveFromCourseState curState = removeFromCourseViewModel.getState();
        errorDisplayField.setText(curState.getRemoveResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String myCoursesViewName) {
        this.myCoursesViewName = myCoursesViewName;
    }
}
