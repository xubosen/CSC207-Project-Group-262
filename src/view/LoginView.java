package view;

import interface_adapter.LoginState;
import interface_adapter.LoginViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.LoginViewModel;
import interface_adapter.LoginController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.Border;


public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final ViewManagerModel viewManagerModel;

    private final String dashboardViewName;

    private final LoginController loginController;

    /**
     * The username chosen by the user
     */
    final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();
    /**
     * The password
     */
    final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    final JButton logIn;
    final JButton signUp;
    final JButton cancel;

    /**
     * A window with a title and a JButton.
     */
    public LoginView(LoginViewModel loginViewModel, ViewManagerModel viewManagerModel, LoginController loginController, String dashboardViewName) throws IOException {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;
        this.loginController = loginController;
        this.dashboardViewName = dashboardViewName;

        JLabel title = new JLabel("University of Toronto HR System");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        BufferedImage image = ImageIO.read(new File("./src/images/logo.jpeg"));
        JLabel logo = new JLabel(new ImageIcon(image));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // From here the elements of the right column

        // Create welcome message
        JLabel welcomeMessage = new JLabel("<html><center>Welcome to the <b>University of Toronto</b> Human Resources Manager<br>Connecting Staff, Faculty, and Students</center></html>", JLabel.CENTER);
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);


        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);

        JPanel buttons = new JPanel();
        logIn = new JButton(loginViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(logIn);
        signUp = new JButton(loginViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(loginViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        logIn.addActionListener(this);
        signUp.addActionListener(this);
        cancel.addActionListener(this);

        usernameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText() + e.getKeyChar());
                loginViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        passwordInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState currentState = loginViewModel.getState();
                currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                loginViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(logIn)) {
                    // Update the password in LoginState right before executing the login process
                    LoginState currentState = loginViewModel.getState();
                    currentState.setPassword(new String(passwordInputField.getPassword()));
                    loginViewModel.setState(currentState);

                    // Execute login process
                    loginController.execute(
                            loginViewModel.getState().getUsername(),
                            loginViewModel.getState().getPassword()
                    );
                }
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Adding the title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Adding the logo
        gbc.gridy = 1;
        this.add(logo, gbc);

        // Setting up the right column for inputs and buttons
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Top, left, bottom, right padding

        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        // Apply the border to the text fields
        usernameInputField.setBorder(BorderFactory.createCompoundBorder(
                usernameInputField.getBorder(), paddingBorder));
        passwordInputField.setBorder(BorderFactory.createCompoundBorder(
                passwordInputField.getBorder(), paddingBorder));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(welcomeMessage); // Add the welcome message here
        // Add a vertical strut for spacing after the welcome message
        rightPanel.add(Box.createVerticalStrut(20)); // Adjust the height (20) as needed

        rightPanel.add(usernameInfo);
        rightPanel.add(usernameErrorField);
        rightPanel.add(Box.createVerticalStrut(2)); // Smaller strut for closer fields
        rightPanel.add(passwordInfo);
        rightPanel.add(passwordErrorField);
        rightPanel.add(buttons);

        this.add(rightPanel, gbc);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == logIn) {
            // Execute login process
            loginController.execute(
                    loginViewModel.getState().getUsername(),
                    loginViewModel.getState().getPassword()
            );
            System.out.println("Click");
        } else if (evt.getSource() == signUp) {
            // Handle sign up action
        } else if (evt.getSource() == cancel) {
            // Handle cancel action: Close the window
            closeWindow();
        }
    }

    /**
     * Closes the window that contains this panel.
     */
    private void closeWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        // Check if login was successful
        if (state.isLoginSuccessful()) {
            // Switch to the dashboard view on successful login
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        } else {
            // Display error message
                JOptionPane.showMessageDialog(this, state.getLoginError());
            }
        }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }
}