package view;

import interface_adapter.LoginState;
import interface_adapter.LoginViewModel;

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
    public LoginView(LoginViewModel loginViewModel) throws IOException {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

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
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
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
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

}