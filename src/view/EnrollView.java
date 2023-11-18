package view;

import interface_adapter.enroll.EnrollViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.enroll.EnrollController;
import interface_adapter.enroll.EnrollState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

public class EnrollView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "enroll";
    private EnrollViewModel enrollViewModel;
    private ViewManagerModel viewManagerModel;
    private String mySessionsViewName;
    private EnrollController enrollController;
    private String userToEnroll = "";

    // Variables for setting up UI
    private JButton invite;
    private JButton close;
    private final String HEADING_TEXT = "Invite To Course";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToEnrollInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    public EnrollView(EnrollController enrollController, EnrollViewModel enrollViewModel,
                      ViewManagerModel viewManagerModel, String mySessionsViewName){
        this.enrollController = enrollController;
        this.enrollViewModel = enrollViewModel;
        this.viewManagerModel = viewManagerModel;
        this.mySessionsViewName = mySessionsViewName;
        enrollViewModel.addPropertyChangeListener(this);
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

        invite = new JButton(enrollViewModel.ENROLL_BUTTON_LABEL);
        invite.addActionListener(this);
        buttonPanel.add(invite);

        close = new JButton(enrollViewModel.CLOSE_BUTTON_LABEL);
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
                EnrollState currentState = enrollViewModel.getState();
                currentState.setUserInvited(userToEnrollInputField.getText());
                enrollViewModel.setState(currentState);
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
            String inviteeID = enrollViewModel.getState().getUserInvited();
            String courseCode = "CSC207"; // TODO: Get the course code of the course the user is currently viewing
            enrollController.enroll(inviteeID, courseCode);

        // Close the window and return to the dashboard
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
        EnrollState curState = enrollViewModel.getState();
        errorDisplayField.setText(curState.getInviteResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }
}
