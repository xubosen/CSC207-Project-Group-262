package view.intro_views;

import interface_adapter.ViewManagerModel;
import interface_adapter.sign_up.SignUpController;
import interface_adapter.sign_up.SignUpState;
import interface_adapter.sign_up.SignUpViewModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignUpView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up view";

    // Variables for Functionality
    private SignUpViewModel signUpViewModel;
    private ViewManagerModel viewManagerModel;
    private SignUpController signUpController;


    // Variables for setting up UI
    private JButton sign_up;
    private JButton close;
    private final String HEADING_TEXT = "Sign Up";
    private String error_message = "";
    private JTextField usernameTextField = new JTextField(10);
    private JTextField nameTextField = new JTextField(10);
    private JTextField emailTextField = new JTextField(10);
    private JTextField passwordTextField = new JTextField(10);
    private JTextField roleTextField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    // Variables for linking to other views
    private String logInView;

    /**
     * Initializer for the little panel that pops up when you click create event in the myCoursesView.
     * @param signUpController Controller that takes in user input to get some output data
     * @param signUpViewModel The View Model that this pulls information from
     * @param viewManagerModel Manages the current view to display
     */
    public SignUpView(SignUpController signUpController, SignUpViewModel signUpViewModel,
                      ViewManagerModel viewManagerModel){
        this.signUpController = signUpController;
        this.signUpViewModel = signUpViewModel;
        this.viewManagerModel = viewManagerModel;
        signUpViewModel.addPropertyChangeListener(this);

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

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Username:"));
        usernamePanel.add(usernameTextField);
        inputFieldPanel.add(usernamePanel);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameTextField);
        inputFieldPanel.add(namePanel);

        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("Email:"));
        emailPanel.add(emailTextField);
        inputFieldPanel.add(emailPanel);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        passwordPanel.add(passwordTextField);
        inputFieldPanel.add(passwordPanel);

        JPanel rolePanel = new JPanel();
        rolePanel.add(new JLabel("Role (Instructor/TA):"));
        rolePanel.add(roleTextField);
        inputFieldPanel.add(rolePanel);

        return inputFieldPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        sign_up = new JButton("Sign Up");
        sign_up.addActionListener(this);
        buttonPanel.add(sign_up);

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
        SignUpState currentState = signUpViewModel.getState();

        usernameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setUsername(usernameTextField.getText());
                signUpViewModel.setState(currentState);
            }
        });

        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setName(nameTextField.getText());
                signUpViewModel.setState(currentState);
            }
        });

        emailTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setEmail(emailTextField.getText());
                signUpViewModel.setState(currentState);
            }
        });

        // Set up the functionality of course code text field
        passwordTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setPassword(passwordTextField.getText());
                signUpViewModel.setState(currentState);
            }
        });

        roleTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setRole(roleTextField.getText().toLowerCase());
                signUpViewModel.setState(currentState);
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        usernameTextField.setBorder(BorderFactory.createCompoundBorder(usernameTextField.getBorder(), paddingBorder));
        nameTextField.setBorder(BorderFactory.createCompoundBorder(nameTextField.getBorder(), paddingBorder));
        emailTextField.setBorder(BorderFactory.createCompoundBorder(emailTextField.getBorder(), paddingBorder));
        passwordTextField.setBorder(BorderFactory.createCompoundBorder(passwordTextField.getBorder(), paddingBorder));
        roleTextField.setBorder(BorderFactory.createCompoundBorder(roleTextField.getBorder(), paddingBorder));

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the create new event button, call the controller and pass in the courses to create
        if (event.getSource() == sign_up) {
            SignUpState curState = signUpViewModel.getState();

            String username = curState.getUsername();
            String name = curState.getName();
            String email = curState.getEmail();
            String password = curState.getPassword();
            String role = curState.getRole();

            signUpController.signUp(username, name, email, password, role);

        // Close the window and return to log in screen
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(logInView);
            viewManagerModel.firePropertyChanged();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Display the response message
        SignUpState curState = signUpViewModel.getState();
        errorDisplayField.setText(curState.getSignupResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String logInView) {
        this.logInView = logInView;
    }
}