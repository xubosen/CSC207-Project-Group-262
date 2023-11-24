package view;

import interface_adapter.*;

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
    private String currentCourseViewName;
    private RemoveFromCourseController removeFromCourseController;
    private String userToRemove = "";

    // Variables for setting up UI
    private JButton remove;
    private JButton close;
    private final String HEADING_TEXT = "Remove From Course";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToRemoveInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    public RemoveFromCourseView(RemoveFromCourseController controller, RemoveFromCourseViewModel viewModel, ViewManagerModel viewManagerModel, String currentCourseViewName) {
        this.viewManagerModel = viewManagerModel;
        this.currentCourseViewName = currentCourseViewName;
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
        setUpInputField();
        setUpErrorDisplayField();

        // Add components to UI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(headingLabel);
        mainPanel.add(userToRemoveInputField);
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

    private void setUpInputField() {
        setUpInputFieldFunctionality();
        setUpInputFieldStyle();
    }

    private void setUpInputFieldFunctionality() {
        userToRemoveInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                RemoveFromCourseState currentState = removeFromCourseViewModel.getState();
                currentState.setUserRemoved(userToRemoveInputField.getText());
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
        // When the user clicks the remove button, call the controller to try to remove the user from the current course
        if (event.getSource() == remove) {
            String userToRemove = removeFromCourseViewModel.getState().getUserRemoved();
            String courseCode = "001"; // TODO: get the courseCode of the Course the user is currently viewing
            removeFromCourseController.removeFromCourse(userToRemove, courseCode);

            // Close the window
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(currentCourseViewName);
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
}
