package view.session_views;



import interface_adapter.remove_from_session.RemoveFromSessionController;
import interface_adapter.remove_from_session.RemoveFromSessionState;
import interface_adapter.remove_from_session.RemoveFromSessionViewModel;
import interface_adapter.ViewManagerModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

public class RemoveFromSessionView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Remove From Session";

    // Variables for Functionality
    private RemoveFromSessionViewModel removeFromSessionViewModel;
    private ViewManagerModel viewManagerModel;
    private RemoveFromSessionController removeFromSessionController;


    // Variables for setting up UI
    private JButton remove;
    private JButton close;
    private final String HEADING_TEXT = "Remove From Session";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToRemoveInputField = new JTextField(10);
    private JTextField sessionIDInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    // Variables for Linking to other views
    private String mySessionViewName;


    public RemoveFromSessionView(RemoveFromSessionController controller, RemoveFromSessionViewModel viewModel,
                                 ViewManagerModel viewManagerModel){
        this.viewManagerModel = viewManagerModel;
        this.removeFromSessionController = controller;
        this.removeFromSessionViewModel = viewModel;
        removeFromSessionViewModel.addPropertyChangeListener(this);

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

        JPanel sessionIDPanel = new JPanel();
        sessionIDPanel.add(new JLabel("Session ID:"));
        sessionIDPanel.add(sessionIDInputField);
        inputFieldPanel.add(sessionIDPanel);

        JPanel userToRemovePanel = new JPanel();
        userToRemovePanel.add(new JLabel("User to Remove:"));
        userToRemovePanel.add(userToRemoveInputField);
        inputFieldPanel.add(userToRemovePanel);

        return inputFieldPanel;
    }

    private JPanel setUpButtons() {
        JPanel buttonPanel = new JPanel();

        remove = new JButton(removeFromSessionViewModel.REMOVE_BUTTON_LABEL);
        remove.addActionListener(this);
        buttonPanel.add(remove);

        close = new JButton(removeFromSessionViewModel.CLOSE_BUTTON_LABEL);
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
        RemoveFromSessionState currentState = removeFromSessionViewModel.getState();

        userToRemoveInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                currentState.setUserRemoved(userToRemoveInputField.getText());
                removeFromSessionViewModel.setState(currentState);
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
                currentState.setSessionRemovedFrom(sessionIDInputField.getText());
                removeFromSessionViewModel.setState(currentState);
            }
        });
    }

    private void setUpInputFieldStyle() {
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        userToRemoveInputField.setBorder(BorderFactory.createCompoundBorder(
                userToRemoveInputField.getBorder(), paddingBorder));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When the user clicks the remove button, call the controller to try to remove the user from the current session
        if (event.getSource() == remove) {
            RemoveFromSessionState curState = removeFromSessionViewModel.getState();

            String userToRemove = curState.getUserRemoved();
            String sessionID = curState.getSessionRemovedFrom();

            removeFromSessionController.removeFromSession(userToRemove, sessionID);

        // Close the window
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(mySessionViewName);
            viewManagerModel.firePropertyChanged();

        // Something went wrong. Throw an error.
        } else {
            throw new RuntimeException("Something went wrong with the buttons");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Display the response message
        RemoveFromSessionState curState = removeFromSessionViewModel.getState();
        errorDisplayField.setText(curState.getRemoveResponseMessage());
        errorDisplayField.setForeground(Color.BLUE);
    }

    public void linkViews(String mySessionViewName) {
        this.mySessionViewName = mySessionViewName;
    }
}
