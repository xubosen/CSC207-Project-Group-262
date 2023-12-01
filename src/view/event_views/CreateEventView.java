package view.event_views;

import interface_adapter.UserState;
import interface_adapter.create_event.CreateEventController;
import interface_adapter.create_event.CreateEventState;
import interface_adapter.create_event.CreateEventViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateEventView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "create event view";

    // Variables for Functionality
    private CreateEventViewModel createEventViewModel;
    private ViewManagerModel viewManagerModel;
    private CreateEventController createEventController;
    private UserState curUserState;


    // Variables for setting up UI
    private JButton createNewEvent;
    private JButton close;
    private final String HEADING_TEXT = "Create Event";
    private String error_message = "";
    private JTextField eventNameTextField = new JTextField(10);
    private JTextField eventIDTextField = new JTextField(10);
    private JTextField typeOfEventTextField = new JTextField(10);
    private JTextField courseCodeTextField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    // Variables for linking to other views
    private String myEventsViewName;

    /**
     * Initializer for the little panel that pops up when you click create event in the myCoursesView.
     * @param createEventController Controller that takes in user input to get some output data
     * @param createEventViewModel The View Model that this pulls information from
     * @param viewManagerModel Manages the current view to display
     */
    public CreateEventView(CreateEventController createEventController, CreateEventViewModel createEventViewModel,
                           ViewManagerModel viewManagerModel, UserState curUserState){
        this.createEventController = createEventController;
        this.createEventViewModel = createEventViewModel;
        this.viewManagerModel = viewManagerModel;
        createEventViewModel.addPropertyChangeListener(this);

        this.curUserState = curUserState;
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

        JPanel eventNamePanel = new JPanel();
        eventNamePanel.add(new JLabel("Event Name:"));
        eventNamePanel.add(eventNameTextField);
        inputFieldPanel.add(eventNamePanel);

        JPanel eventIDPanel = new JPanel();
        eventIDPanel.add(new JLabel("Event ID:"));
        eventIDPanel.add(eventIDTextField);
        inputFieldPanel.add(eventIDPanel);

        JPanel typeOfEventPanel = new JPanel();
        typeOfEventPanel.add(new JLabel("Event Type:"));
        typeOfEventPanel.add(typeOfEventTextField);
        inputFieldPanel.add(typeOfEventPanel);

        JPanel courseCodePanel = new JPanel();
        courseCodePanel.add(new JLabel("Course Code:"));
        courseCodePanel.add(courseCodeTextField);
        inputFieldPanel.add(courseCodePanel);

        return inputFieldPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        createNewEvent = new JButton(createEventViewModel.Create_EVENT_BUTTON_LABEL);
        createNewEvent.addActionListener(this);
        buttonPanel.add(createNewEvent);

        close = new JButton(createEventViewModel.CLOSE_BUTTON_LABEL);
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
        CreateEventState currentState = createEventViewModel.getState();

        // Set up the functionality of event ID text field
        eventIDTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setEventID(eventIDTextField.getText());
                createEventViewModel.setState(currentState);
            }
        });

        // Set up the functionality of event name text field
        eventNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setEventName(eventNameTextField.getText());
                createEventViewModel.setState(currentState);
            }
        });

        // Set up the functionality of event type text field
        typeOfEventTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setEventType(typeOfEventTextField.getText().toLowerCase());
                createEventViewModel.setState(currentState);
            }
        });

        // Set up the functionality of course code text field
        courseCodeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setCourseCode(courseCodeTextField.getText());

                createEventViewModel.setState(currentState);
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        eventNameTextField.setBorder(BorderFactory.createCompoundBorder(
                eventNameTextField.getBorder(), paddingBorder));

        eventIDTextField.setBorder(BorderFactory.createCompoundBorder(
                eventIDTextField.getBorder(), paddingBorder));

        typeOfEventTextField.setBorder(BorderFactory.createCompoundBorder(
                typeOfEventTextField.getBorder(), paddingBorder));

        courseCodeTextField.setBorder(BorderFactory.createCompoundBorder(
                courseCodeTextField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the create new event button, call the controller and pass in the courses to create
        if (event.getSource() == createNewEvent) {
            CreateEventState curState = createEventViewModel.getState();

            String eventID = curState.getEventID();
            String eventName = curState.getEventName();
            String eventType = curState.getEventType();
            String courseCode = curState.getCourseCode();
            String curUserID = curUserState.getUserID();

            createEventController.createEvent(eventName, eventID, eventType, curUserID, courseCode);

        // Close the window and return to my events view
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(myEventsViewName);
            viewManagerModel.firePropertyChanged();

        } else {
            throw new RuntimeException("Something went wrong with the buttons");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Display the response message
        CreateEventState curState = createEventViewModel.getState();
        errorDisplayField.setText(curState.getEventCreationResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String myEventsViewName) {
        this.myEventsViewName = myEventsViewName;
    }
}