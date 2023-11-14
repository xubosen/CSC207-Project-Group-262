package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interface_adapter.ViewManagerModel;

import javax.swing.*;


public class MySessionsView extends JPanel implements ActionListener {

    // Buttons
    private final JButton inviteButton = new JButton("Invite");
    private final JButton backButton = new JButton("Back");
    private ViewManagerModel viewManagerModel;

    // View names
    public final String viewName = "my sessions view";
    private String myCoursesViewName = "my courses view";
    private String inviteViewName = "enroll";

    public MySessionsView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeInviteButton(gbc);
        makeBackButton(gbc);
    }

    private GridBagConstraints formatScreenLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private void makeHeading(GridBagConstraints gbc) {
        JLabel headingLabel = new JLabel("My Sessions", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font for the heading
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 0; // Align to the first row
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        add(headingLabel, gbc);
    }

    private void makeInviteButton(GridBagConstraints gbc) {
        inviteButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 1; // Align to the second row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        inviteButton.setPreferredSize(new Dimension(inviteButton.getPreferredSize().width, 150));
        add(inviteButton, gbc);
        inviteButton.addActionListener(this);
    }

    private void makeBackButton(GridBagConstraints gbc) {
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0; // Align to the first column
        gbc.gridy = 2; // Align to the second row
        gbc.weightx = 1; // Take up all horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        gbc.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 150));
        add(backButton, gbc);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inviteButton) {
            viewManagerModel.setActiveView(this.inviteViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(this.myCoursesViewName);
            viewManagerModel.firePropertyChanged();
        }
    }
}
