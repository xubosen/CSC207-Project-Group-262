package view;



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
    private RemoveFromSessionViewModel removeFromSessionViewModel;
    private ViewManagerModel viewManagerModel;
    private String currentSessionViewName;
    private RemoveFromSessionController removeFromSessionController;
    private String userToEnroll = "";

    // Variables for setting up UI
    private JButton remove;
    private JButton close;
    private final String HEADING_TEXT = "Remove From Session";
    private final String INPUT_FIELD_LABEL = "";
    private String error_message = "";
    private JTextField userToRemoveInputField = new JTextField(10);
    private JLabel errorDisplayField = new JLabel(error_message);

    public RemoveFromSessionView(RemoveFromSessionController controller, RemoveFromSessionViewModel viewModel,
                                 ViewManagerModel viewManagerModel, String currentSessionViewName){
        this.viewManagerModel = viewManagerModel;

        this.currentSessionViewName = currentSessionViewName;
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
        setUpInputField();
        setUpErrorDisplayField();

        // Add components to UI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(headingLabel);
        mainPanel.add(userToRemoveInputField);
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

    private void setUpInputField() {
        setUpInputFieldFunctionality();
        setUpInputFieldStyle();
    }

    private void setUpInputFieldFunctionality() {
        userToRemoveInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                RemoveFromSessionState currentState = removeFromSessionViewModel.getState();
                currentState.setUserRemoved(userToRemoveInputField.getText());
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
            String userToRemove = removeFromSessionViewModel.getState().getUserRemoved();
            String sessionID = "001"; // TODO: get the session ID of the session the user is currently viewing
            removeFromSessionController.removeFromSession(userToRemove, sessionID);

        // Close the window
        } else if (event.getSource() == close) {
            viewManagerModel.setActiveView(currentSessionViewName);
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
}
