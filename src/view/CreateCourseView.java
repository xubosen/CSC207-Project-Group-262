package view;

import entity.Instructor;
import entity.TeachingAssistant;
import interface_adapter.create_course.CreateCourseController;
import interface_adapter.create_course.CreateCourseState;
import interface_adapter.create_course.CreateCourseViewModel;
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

public class CreateCourseView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "create course view";
    private CreateCourseViewModel createCourseViewModel;
    private ViewManagerModel viewManagerModel;
    private String myCoursesViewName = "my courses view";
    private CreateCourseController createCourseController;
    private String courseToCreate = "";

    // Variables for setting up UI
    private JButton createNewCourse;
    private JButton close;
    private final String HEADING_TEXT = "Create Course";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField courseCodeTextField = new JTextField(10);
    private JTextField courseNameTextField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    /**
     * Initializer for the little panel that pops up when you click make course in the my courses view.
     * @param createCourseController Controller that takes in user input to get some output data
     * @param createCourseViewModel The View Model that this pulls information from
     * @param viewManagerModel Manages the current view to display
     * TODO: Ask about this variable purpose inside the Remove from session view.
     */
    public CreateCourseView(CreateCourseController createCourseController, CreateCourseViewModel createCourseViewModel,
                      ViewManagerModel viewManagerModel){
        this.createCourseController = createCourseController;
        this.createCourseViewModel = createCourseViewModel;
        this.viewManagerModel = viewManagerModel;
        createCourseViewModel.addPropertyChangeListener(this);
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
        mainPanel.add(courseCodeTextField);
        mainPanel.add(courseNameTextField);
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

        createNewCourse = new JButton(createCourseViewModel.CREATE_COURSE_BUTTON_LABEL);
        createNewCourse.addActionListener(this);
        buttonPanel.add(createNewCourse);

        close = new JButton(createCourseViewModel.CLOSE_BUTTON_LABEL);
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
        courseCodeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                CreateCourseState currentState = createCourseViewModel.getState();
                // This might be a little bit different because it isn't just one input field
                currentState.setCourseCreated(courseCodeTextField.getText());
                createCourseViewModel.setState(currentState);
                System.out.println(currentState.getCourseCreated());
            }
        });
        courseNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                CreateCourseState currentState = createCourseViewModel.getState();
                // This might be a little bit different because it isn't just one input field
                currentState.setCourseName(courseNameTextField.getText());
                createCourseViewModel.setState(currentState);
                System.out.println(currentState.getCourseName());
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        courseCodeTextField.setBorder(BorderFactory.createCompoundBorder(
                courseCodeTextField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the create new course button, call the controller and pass in the courses to create
        if (event.getSource() == createNewCourse) {
            String courseCode = createCourseViewModel.getState().getCourseCreated();
            // TODO: Fix this by changing up the state part
            String courseName = createCourseViewModel.getState().getCourseName();

            // TODO: Get rid of this hardcoded part once we know who is logged in
            Instructor alexander = new Instructor("the main 27", "Alexander Phan",
                    "alexander.phan@mail.utoronto.ca", "champagne");
            // Need to find a way to get current user that is signed in and then their user id.
            createCourseController.createCourse(courseCode, courseName, alexander.getUID());

            // Close the window and return to the myCourses view
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
        CreateCourseState curState = createCourseViewModel.getState();
        errorDisplayField.setText(curState.getCourseCreationResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }
}
