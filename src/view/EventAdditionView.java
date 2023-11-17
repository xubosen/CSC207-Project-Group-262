package view;

import interface_adapter.EventAdditionViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.EventAdditionController;
import interface_adapter.EventAdditionState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

public class EventAdditionView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "event addition";
    private EventAdditionViewModel eventAdditionViewModel;
    private ViewManagerModel viewManagerModel;
    private String myEventsViewName;
    private EventAdditionController eventAdditionController;
    private String userToEnroll = "";

    // Variables for setting up UI
    private JButton invite;
    private JButton close;
    private final String HEADING_TEXT = "Invite To Event";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToEnrollInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    public EventAdditionView(EventAdditionController eventAdditionController, EventAdditionViewModel eventAdditionViewModel,
                      ViewManagerModel viewManagerModel, String myEventsViewName){
        this.eventAdditionController = eventAdditionController;
        this.eventAdditionViewModel = eventAdditionViewModel;
        this.viewManagerModel = viewManagerModel;
        this.myEventsViewName = myEventsViewName;
        eventAdditionViewModel.addPropertyChangeListener(this);
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
        mainPanel.add(userToEnrollInputField);
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

        invite = new JButton(eventAdditionViewModel.EVENT_ADDITION_BUTTON_LABEL);
        invite.addActionListener(this);
        buttonPanel.add(invite);

        close = new JButton(eventAdditionViewModel.CLOSE_BUTTON_LABEL);
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
        userToEnrollInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                EventAdditionState currentState = eventAdditionViewModel.getState();
                currentState.setUserInvited(userToEnrollInputField.getText());
                eventAdditionViewModel.setState(currentState);
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        userToEnrollInputField.setBorder(BorderFactory.createCompoundBorder(
                userToEnrollInputField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the invite button, call a controller and pass in the employee to enroll
        if (event.getSource() == invite) {
            String inviteeID = eventAdditionViewModel.getState().getUserInvited();
            String eventID = "CSC207 Lecture";
            System.out.println(inviteeID);
            eventAdditionController.addEmployeeToEvent(inviteeID, eventID);

            // Close the window and return to the previous screen
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
        System.out.println("Listener is triggered");

        // Display the response message
        EventAdditionState curState = eventAdditionViewModel.getState();
        errorDisplayField.setText(curState.getInviteResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }
}