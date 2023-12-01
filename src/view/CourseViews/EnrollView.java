package view.CourseViews;

import interface_adapter.UserState;
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

// What is mySessionsViewName for?
public class EnrollView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Invite to Course";
    
    // Functionality variables
    private EnrollViewModel enrollViewModel;
    private ViewManagerModel viewManagerModel;
    private EnrollController enrollController;
    private UserState curUserState;
    

    // Variables for setting up UI
    private JButton invite;
    private JButton close;
    private final String HEADING_TEXT = "Invite To Course";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToEnrollInputField = new JTextField(10);
    private JTextField courseCodeInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);
    
    // Variables for Linking to other views
    private String myCoursesViewName;
    
    

    public EnrollView(EnrollController enrollController, EnrollViewModel enrollViewModel,
                      ViewManagerModel viewManagerModel, UserState curUserState){
        this.enrollController = enrollController;
        this.enrollViewModel = enrollViewModel;
        this.viewManagerModel = viewManagerModel;
        this.curUserState = curUserState;
        enrollViewModel.addPropertyChangeListener(this);
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

        JPanel courseCodePanel = new JPanel();
        courseCodePanel.add(new JLabel("Course Code:"));
        courseCodePanel.add(courseCodeInputField);
        inputFieldPanel.add(courseCodePanel);

        JPanel userToEnrollPanel = new JPanel();
        userToEnrollPanel.add(new JLabel("User to Enroll:"));
        userToEnrollPanel.add(userToEnrollInputField);
        inputFieldPanel.add(userToEnrollPanel);

        return inputFieldPanel;
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

    private void formatInputField() {
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

        courseCodeInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                EnrollState currentState = enrollViewModel.getState();
                currentState.setCourseCode(courseCodeInputField.getText());
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
            EnrollState curState = enrollViewModel.getState();

            String inviteeID = curState.getUserInvited();
            String courseCode = curState.getCourseCode();
            String curUserID = curUserState.getUserID();

            enrollController.enroll(curUserID, inviteeID, courseCode);

        // Close the window and return to the dashboard
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
        EnrollState curState = enrollViewModel.getState();
        errorDisplayField.setText(curState.getInviteResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }
    
    public void linkViews(String myCoursesViewName) {
        this.myCoursesViewName = myCoursesViewName;
    }
}
