package view;

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
    private CreateEventViewModel createEventViewModel;
    private ViewManagerModel viewManagerModel;
    private String myEventsViewName = "my events view";
    private CreateEventController createEventController;
//    private String eventToCreate = ""; Unecessary until we save it to database

    // Variables for setting up UI
    private JButton createNewEvent;
    private JButton close;
    private final String HEADING_TEXT = "Create Event";
//    private final String INPUT_FIELD_LABEL = ""; This might useful to indicate what to put inside the object.
    private String error_message = "";
    private JTextField eventNameTextField = new JTextField(10);
    private JTextField eventIDTextField = new JTextField(10);
    private JTextField typeOfEventTextField = new JTextField(10);
    private JTextField courseCodeTextField = new JTextField(10);
    // TODO: Need to include location still and also is there any way to have the left
    //  side of the text box tell you what to put in

    private JLabel errorDisplayField = new JLabel(error_message);

    /**
     * Initializer for the little panel that pops up when you click create event in the myCoursesView.
     * @param createEventController Controller that takes in user input to get some output data
     * @param createEventViewModel The View Model that this pulls information from
     * @param viewManagerModel Manages the current view to display
     */
    public CreateEventView(CreateEventController createEventController, CreateEventViewModel createEventViewModel,
                            ViewManagerModel viewManagerModel){
        this.createEventController = createEventController;
        this.createEventViewModel = createEventViewModel;
        this.viewManagerModel = viewManagerModel;
        createEventViewModel.addPropertyChangeListener(this);
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
        mainPanel.add(eventNameTextField);
        mainPanel.add(eventIDTextField);
        mainPanel.add(typeOfEventTextField);
        mainPanel.add(courseCodeTextField);
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

    private void setUpInputField() {
        setUpInputFieldFunctionality();
        setUpInputFieldStyle();
    }

    private void setUpInputFieldFunctionality() {
        eventNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                CreateEventState currentState = createEventViewModel.getState();
                // This might be a little bit different because it isn't just one input field
                currentState.setCourseName(eventNameTextField.getText());
                createEventViewModel.setState(currentState);
            }
        });
        eventIDTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                CreateEventState currentState = createEventViewModel.getState();
                // This might be a little bit different because it isn't just one input field
                currentState.setEventID(eventIDTextField.getText());
                createEventViewModel.setState(currentState);
            }
        });
        typeOfEventTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                CreateEventState currentState = createEventViewModel.getState();
                // This might be a little bit different because it isn't just one input field
                currentState.setEventType(typeOfEventTextField.getText());
                createEventViewModel.setState(currentState);
            }
        });
        courseCodeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                CreateEventState currentState = createEventViewModel.getState();
                // This might be a little bit different because it isn't just one input field
                currentState.setCourseCode(courseCodeTextField.getText());
                createEventViewModel.setState(currentState);
            }
        });
    }

    // TODO Same thing here about not being sure if separate
    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        eventNameTextField.setBorder(BorderFactory.createCompoundBorder(
                eventNameTextField.getBorder(), paddingBorder));

        eventIDTextField.setBorder(BorderFactory.createCompoundBorder(
                eventIDTextField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the create new event button, call the controller and pass in the courses to create
        if (event.getSource() == createNewEvent) {
            String eventID = createEventViewModel.getState().getEventID();
            // TODO: Fix this by changing up the state part
            String eventName = createEventViewModel.getState().getEventName();
            String eventType = createEventViewModel.getState().getEventType();
            String creatorID = createEventViewModel.getState().getCreatorID();
            String courseCode = createEventViewModel.getState().getCourseCode();
            // Need to find a way to get current user that is signed in and then their user id.

            createEventController.createEvent(eventName, eventID, eventType, creatorID, courseCode);

            // Close the window and return to the dashboard
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
}
