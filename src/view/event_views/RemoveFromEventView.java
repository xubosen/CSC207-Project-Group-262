package view.event_views;

import interface_adapter.*;
import interface_adapter.remove_from_event.RemoveFromEventController;
import interface_adapter.remove_from_event.RemoveFromEventState;
import interface_adapter.remove_from_event.RemoveFromEventViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

public class RemoveFromEventView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "Remove From Event";

    // Variables for Functionality
    private RemoveFromEventViewModel removeFromEventViewModel;
    private ViewManagerModel viewManagerModel;
    private RemoveFromEventController removeFromEventController;

    // Variables for setting up UI
    private JButton remove;
    private JButton close;
    private final String HEADING_TEXT = "Remove From Event";
    private String error_message = "";
    private JTextField eventIDInputField = new JTextField(10);
    private JTextField userToRemoveInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    // Variables for linking to other views
    private String myEventsViewName;


    public RemoveFromEventView(RemoveFromEventController controller, RemoveFromEventViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.removeFromEventController = controller;
        this.removeFromEventViewModel = viewModel;
        removeFromEventViewModel.addPropertyChangeListener(this);

        setupUI();
    }

    private void setupUI() {
        // Format UI
        GridBagConstraints gbc = formatScreenLayout();

        // Make components of UI
        JLabel headingLabel = makeHeading();
        JPanel buttonPanel = setUpButtons();
        formatInputField();
        setUpErrorDisplayField();

        // Add components to UI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(headingLabel);

        JPanel inputPanel = makeInputFields();
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

    private JPanel makeInputFields() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel eventIDLabel = new JLabel("Event ID: ");
        eventIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(eventIDLabel);
        inputPanel.add(eventIDInputField);

        JLabel userToRemoveLabel = new JLabel("User to Remove: ");
        userToRemoveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(userToRemoveLabel);
        inputPanel.add(userToRemoveInputField);

        return inputPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        remove = new JButton(removeFromEventViewModel.REMOVE_BUTTON_LABEL);
        remove.addActionListener(this);
        buttonPanel.add(remove);

        close = new JButton(removeFromEventViewModel.CLOSE_BUTTON_LABEL);
        close.addActionListener(this);
        buttonPanel.add(close);

        return buttonPanel;
    }

    private void setUpErrorDisplayField() {
        errorDisplayField.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void formatInputField() {
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
                RemoveFromEventState currentState = removeFromEventViewModel.getState();
                currentState.setUserRemoved(userToRemoveInputField.getText());
                removeFromEventViewModel.setState(currentState);
            }
        });

        eventIDInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                RemoveFromEventState currentState = removeFromEventViewModel.getState();
                currentState.setEventRemovedFrom(eventIDInputField.getText());
                removeFromEventViewModel.setState(currentState);
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
        // When the user clicks the remove button, call the controller to try to remove the user from the current event
        if (event.getSource() == remove) {
            String userToRemove = removeFromEventViewModel.getState().getUserRemoved();
            String eventID = removeFromEventViewModel.getState().getEventRemovedFrom();
            removeFromEventController.removeFromEvent(userToRemove, eventID);

        // Close the window
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(myEventsViewName);
            viewManagerModel.firePropertyChanged();

            // Something went wrong. Throw an error.
        } else {
            throw new RuntimeException("Something went wrong with the buttons");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Display the response message
        RemoveFromEventState curState = removeFromEventViewModel.getState();
        errorDisplayField.setText(curState.getRemoveResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String myEventsViewName) {
        this.myEventsViewName = myEventsViewName;
    }
}
