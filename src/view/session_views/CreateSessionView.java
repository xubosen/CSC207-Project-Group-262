package view.session_views;

import interface_adapter.UserState;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_session.CreateSessionController;
import interface_adapter.create_session.CreateSessionState;
import interface_adapter.create_session.CreateSessionViewModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CreateSessionView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "create session";

    // Variables for Functionality
    private CreateSessionViewModel createSessionViewModel;
    private ViewManagerModel viewManagerModel;
    private CreateSessionController createSessionController;
    private UserState userState;


    // Variables for setting up UI
    private JButton createSession;
    private JButton close;
    private final String HEADING_TEXT = "Create New Session";
    private String error_message = "";
    private JTextField sessionNameTextField = new JTextField(10);
    private JTextField sessionIDTextField = new JTextField(10);
    private JTextField sessionDescriptionTextField = new JTextField(10);
    private JTextField parentEventTextField = new JTextField(10);
    private JTextField locationTextField = new JTextField(10);

    private JTextField startTimeTextField = new JTextField(15);
    private JTextField endTimeTextField = new JTextField(15);

    private JLabel errorDisplayField = new JLabel(error_message);

    // Variables for linking to other views
    private String mySessionsViewName;

    /**
     * Initializer for the little panel that pops up when you click create event in the myCoursesView.
     * @param createSessionController Controller that takes in user input to get some output data
     * @param createSessionViewModel The View Model that this pulls information from
     * @param viewManagerModel Manages the current view to display
     */
    public CreateSessionView(CreateSessionController createSessionController,
                             CreateSessionViewModel createSessionViewModel, ViewManagerModel viewManagerModel,
                             UserState userState){
        this.createSessionController = createSessionController;
        this.createSessionViewModel = createSessionViewModel;
        this.viewManagerModel = viewManagerModel;
        createSessionViewModel.addPropertyChangeListener(this);
        this.userState = userState;
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

        JPanel inputFields = setUpInputFields();
        mainPanel.add(inputFields);

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

        JPanel sessionNamePanel = new JPanel();
        sessionNamePanel.add(new JLabel("Session Name: "));
        sessionNamePanel.add(sessionNameTextField);
        inputFieldPanel.add(sessionNamePanel);

        JPanel sessionIDPanel = new JPanel();
        sessionIDPanel.add(new JLabel("Session ID: "));
        sessionIDPanel.add(sessionIDTextField);
        inputFieldPanel.add(sessionIDPanel);

        JPanel sessionDescriptionPanel = new JPanel();
        sessionDescriptionPanel.add(new JLabel("Session Description: "));
        sessionDescriptionPanel.add(sessionDescriptionTextField);
        inputFieldPanel.add(sessionDescriptionPanel);

        JPanel locationPanel = new JPanel();
        locationPanel.add(new JLabel("Location: "));
        locationPanel.add(locationTextField);
        inputFieldPanel.add(locationPanel);

        JPanel parentEventPanel = new JPanel();
        parentEventPanel.add(new JLabel("Parent Event ID: "));
        parentEventPanel.add(parentEventTextField);
        inputFieldPanel.add(parentEventPanel);

        inputFieldPanel.add(new JLabel("Time Range (yyyy-mm-dd hh:mm)"));
        JPanel timePanel = new JPanel();
        timePanel.add(startTimeTextField);
        timePanel.add(new JLabel(" to "));
        timePanel.add(endTimeTextField);
        inputFieldPanel.add(timePanel);

        return inputFieldPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        createSession = new JButton("Create");
        createSession.addActionListener(this);
        buttonPanel.add(createSession);

        close = new JButton("Close");
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
        CreateSessionState currentState = createSessionViewModel.getState();

        sessionIDTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setSessionID(sessionIDTextField.getText());
                createSessionViewModel.setState(currentState);
            }
        });

        sessionNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setSessionName(sessionNameTextField.getText());
                createSessionViewModel.setState(currentState);
            }
        });

        sessionDescriptionTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setDescription(sessionDescriptionTextField.getText());
                createSessionViewModel.setState(currentState);
            }
        });

        parentEventTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setParentEventID(parentEventTextField.getText());
                createSessionViewModel.setState(currentState);
            }
        });

        locationTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setLocation(locationTextField.getText());
                createSessionViewModel.setState(currentState);
            }
        });

        startTimeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setStartTime(extractTimeField(startTimeTextField));
                createSessionViewModel.setState(currentState);
            }
        });

        endTimeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setEndTime(extractTimeField(endTimeTextField));
                createSessionViewModel.setState(currentState);
            }
        });
    }

    private LocalDateTime extractTimeField(JTextField field){
        String text = field.getText();
        try{
            errorDisplayField.setText("");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(text, formatter);

        } catch (Exception e){
            errorDisplayField.setText("Invalid time format");
            errorDisplayField.setForeground(Color.RED);
            return null;
        }
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        sessionNameTextField.setBorder(BorderFactory.createCompoundBorder(sessionNameTextField.getBorder(), paddingBorder));
        sessionIDTextField.setBorder(BorderFactory.createCompoundBorder(sessionIDTextField.getBorder(), paddingBorder));
        sessionDescriptionTextField.setBorder(BorderFactory.createCompoundBorder(sessionDescriptionTextField.getBorder(), paddingBorder));
        parentEventTextField.setBorder(BorderFactory.createCompoundBorder(parentEventTextField.getBorder(), paddingBorder));
        locationTextField.setBorder(BorderFactory.createCompoundBorder(locationTextField.getBorder(), paddingBorder));

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == createSession) {
            CreateSessionState curState = createSessionViewModel.getState();

            String userID = userState.getUserID();
            String sessionName = curState.getSessionName();
            String sessionID = curState.getSessionID();
            String sessionDescription = curState.getDescription();
            String location = curState.getLocation();
            LocalDateTime startTime = curState.getStartTime();
            LocalDateTime endTime = curState.getEndTime();
            String parentEvent = curState.getParentEventID();

            createSessionController.createSession(userID, sessionID, sessionName, sessionDescription, location,
                    startTime, endTime, parentEvent);

        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(mySessionsViewName);
            viewManagerModel.firePropertyChanged();

        } else {
            throw new RuntimeException("Something went wrong with the buttons");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Display the response message
        CreateSessionState curState = createSessionViewModel.getState();
        errorDisplayField.setText(curState.getSessionCreationResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String mySessionsViewName) {
        this.mySessionsViewName = mySessionsViewName;
    }
}