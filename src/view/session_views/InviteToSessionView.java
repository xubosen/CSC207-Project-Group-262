package view.session_views;

import interface_adapter.UserState;
import interface_adapter.invite_to_session.InviteToSessionViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.invite_to_session.InviteToSessionController;
import interface_adapter.invite_to_session.InviteToSessionState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

public class InviteToSessionView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "invite to session";

    // Variables for functionality
    private InviteToSessionViewModel inviteToSessionViewModel;
    private ViewManagerModel viewManagerModel;
    private InviteToSessionController inviteToSessionController;
    private UserState userState;


    // Variables for setting up UI
    private JButton invite;
    private JButton close;
    private final String HEADING_TEXT = "Invite To Session";
    private String error_message = "";
    private JTextField sessionIDInputField = new JTextField(10);
    private JTextField userToEnrollInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    // Variables for linking to other views
    private String mySessionsViewName;

    public InviteToSessionView(InviteToSessionController inviteToSessionController, InviteToSessionViewModel inviteToSessionViewModel,
                               ViewManagerModel viewManagerModel, UserState userState){
        this.inviteToSessionController = inviteToSessionController;
        this.inviteToSessionViewModel = inviteToSessionViewModel;
        this.viewManagerModel = viewManagerModel;
        inviteToSessionViewModel.addPropertyChangeListener(this);
        this.userState = userState;
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

        JPanel sessionIDPanel = new JPanel();
        sessionIDPanel.add(new JLabel("Session ID: "));
        sessionIDPanel.add(sessionIDInputField);
        inputPanel.add(sessionIDPanel);

        JPanel userToAddPanel = new JPanel();
        userToAddPanel.add(new JLabel("User to Add: "));
        userToAddPanel.add(userToEnrollInputField);
        inputPanel.add(userToAddPanel);

        return inputPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        invite = new JButton(inviteToSessionViewModel.Invite_BUTTON_LABEL);
        invite.addActionListener(this);
        buttonPanel.add(invite);

        close = new JButton(inviteToSessionViewModel.CLOSE_BUTTON_LABEL);
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
        InviteToSessionState currentState = inviteToSessionViewModel.getState();

        userToEnrollInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setUserInvited(userToEnrollInputField.getText());
                inviteToSessionViewModel.setState(currentState);
            }
        });

        sessionIDInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setSessionID(sessionIDInputField.getText());
                inviteToSessionViewModel.setState(currentState);
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

            String inviteeID = inviteToSessionViewModel.getState().getUserInvited();
            String sessionID = inviteToSessionViewModel.getState().getSessionID();
            inviteToSessionController.invite(inviteeID, userState.getUserID(), sessionID);

        // Close the window and return to the previous screen
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(mySessionsViewName);
            viewManagerModel.firePropertyChanged();

        // Something went wrong. Throw an error.
        } else {
            throw new RuntimeException("Something went wrong with the buttons");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

        // Display the response message
        InviteToSessionState curState = inviteToSessionViewModel.getState();
        errorDisplayField.setText(curState.getInviteResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String mySessionsViewName) {
        this.mySessionsViewName = mySessionsViewName;
    }
}