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

public class RemoveFromEventView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "Remove From Event";
    private RemoveFromEventViewModel removeFromEventViewModel;
    private ViewManagerModel viewManagerModel;
    private String currentEventViewName;
    private RemoveFromEventController removeFromEventController;
    private String userToRemove = "";

    // Variables for setting up UI
    private JButton remove;
    private JButton close;
    private final String HEADING_TEXT = "Remove From Event";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToRemoveInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    public RemoveFromEventView(RemoveFromEventController controller, RemoveFromEventViewModel viewModel, ViewManagerModel viewManagerModel, String currentEventViewName) {
        this.viewManagerModel = viewManagerModel;
        this.currentEventViewName = currentEventViewName;
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
                RemoveFromEventState currentState = removeFromEventViewModel.getState();
                currentState.setUserRemoved(userToRemoveInputField.getText());
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
            String eventID = "001"; // TODO: get the event ID of the event the user is currently viewing
            removeFromEventController.removeFromEvent(userToRemove, eventID);

            // Close the window
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(currentEventViewName);
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
}
