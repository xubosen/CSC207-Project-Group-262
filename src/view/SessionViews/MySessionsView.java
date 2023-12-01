package view.SessionViews;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySessionsView extends JPanel implements ActionListener {
    public final String viewName = "my sessions";
    private final ViewManagerModel viewManagerModel;

    // Buttons
    private final JButton backButton = new JButton("Back");
    private final JButton createSessionButton = new JButton("Add Session");
    private JButton inviteToSessionButton = new JButton("Invite To Session");
    private final JButton removeFromSessionButton = new JButton("Remove From Session");

    // Variables for linking to other views
    private String createSessionViewName;
    private String inviteToSessionViewName;
    private String removeFromSessionViewName;
    private String dashboardViewName;


    public MySessionsView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeCreateSessionButton(gbc);
        makeInviteToSessionButton(gbc);
        makeRemoveFromSessionButton(gbc);
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
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(headingLabel, gbc);
    }

    private void makeCreateSessionButton(GridBagConstraints gbc) {
        createSessionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        createSessionButton.setPreferredSize(new Dimension(createSessionButton.getPreferredSize().width, 150));
        add(createSessionButton, gbc);
        createSessionButton.addActionListener(this);
    }

    private void makeInviteToSessionButton(GridBagConstraints gbc) {
        inviteToSessionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        inviteToSessionButton.setPreferredSize(new Dimension(inviteToSessionButton.getPreferredSize().width, 150));
        add(inviteToSessionButton, gbc);
        inviteToSessionButton.addActionListener(this);
    }

    private void makeRemoveFromSessionButton(GridBagConstraints gbc) {
        removeFromSessionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        removeFromSessionButton.setPreferredSize(new Dimension(removeFromSessionButton.getPreferredSize().width, 150));
        add(removeFromSessionButton, gbc);
        removeFromSessionButton.addActionListener(this);
    }

    private void makeBackButton(GridBagConstraints gbc) {
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 150));
        add(backButton, gbc);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createSessionButton) {
//            viewManagerModel.setActiveView(createSessionViewName);
//            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == inviteToSessionButton) {
            viewManagerModel.setActiveView(inviteToSessionViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == removeFromSessionButton) {
            viewManagerModel.setActiveView(removeFromSessionViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        }
    }

    public void linkViews(String createSessionViewName, String inviteToSessionViewName, String removeFromSessionViewName, String dashboardViewName) {
        this.createSessionViewName = createSessionViewName;
        this.inviteToSessionViewName = inviteToSessionViewName;
        this.removeFromSessionViewName = removeFromSessionViewName;
        this.dashboardViewName = dashboardViewName;
    }
}