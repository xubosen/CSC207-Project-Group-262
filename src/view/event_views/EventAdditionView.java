package view.event_views;

import interface_adapter.UserState;
import interface_adapter.add_to_event.EventAdditionViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_to_event.EventAdditionController;
import interface_adapter.add_to_event.EventAdditionState;

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

    // Functionality variables
    private EventAdditionViewModel eventAdditionViewModel;
    private ViewManagerModel viewManagerModel;
    private EventAdditionController eventAdditionController;
    private String myEventsViewName;
    private UserState curUserState;


    // Variables for setting up UI
    private JButton invite;
    private JButton close;
    private final String HEADING_TEXT = "Invite To Event";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField eventIDInputField = new JTextField(10);
    private JTextField userToInviteInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    public EventAdditionView(EventAdditionController eventAdditionController, EventAdditionViewModel eventAdditionViewModel,
                             ViewManagerModel viewManagerModel, UserState curUserState){
        this.eventAdditionController = eventAdditionController;
        this.eventAdditionViewModel = eventAdditionViewModel;
        this.viewManagerModel = viewManagerModel;
        eventAdditionViewModel.addPropertyChangeListener(this);
        this.curUserState = curUserState;
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

        JPanel inputFieldPanel = setUpInputFields();
        mainPanel.add(inputFieldPanel);

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
        JPanel inputFieldPanel = new JPanel();
        inputFieldPanel.setLayout(new BoxLayout(inputFieldPanel, BoxLayout.Y_AXIS));

        JLabel eventIDLabel = new JLabel("Event ID");
        eventIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputFieldPanel.add(eventIDLabel);
        inputFieldPanel.add(eventIDInputField);

        JLabel userToInviteLabel = new JLabel("User to Invite");
        userToInviteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputFieldPanel.add(userToInviteLabel);
        inputFieldPanel.add(userToInviteInputField);

        return inputFieldPanel;
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
        userToInviteInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                EventAdditionState currentState = eventAdditionViewModel.getState();
                currentState.setUserInvited(userToInviteInputField.getText());
                eventAdditionViewModel.setState(currentState);
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
                EventAdditionState currentState = eventAdditionViewModel.getState();
                currentState.setEventID(eventIDInputField.getText());
                eventAdditionViewModel.setState(currentState);
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        userToInviteInputField.setBorder(BorderFactory.createCompoundBorder(
                userToInviteInputField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the invite button, call a controller and pass in the employee to enroll
        if (event.getSource() == invite) {
            EventAdditionState curState = eventAdditionViewModel.getState();

            String inviteeID = curState.getUserInvited();
            String eventID = curState.getEventID();

            eventAdditionController.addEmployeeToEvent(curUserState.getUserID(), inviteeID, eventID);

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
        // Display the response message
        EventAdditionState curState = eventAdditionViewModel.getState();
        errorDisplayField.setText(curState.getInviteResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String myEventsViewName) {
        this.myEventsViewName = myEventsViewName;
    }
}