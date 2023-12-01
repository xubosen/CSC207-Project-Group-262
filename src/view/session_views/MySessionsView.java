package view.session_views;

import interface_adapter.UserState;
import interface_adapter.ViewManagerModel;
import interface_adapter.get_sessions.GetSessionsController;
import interface_adapter.get_sessions.GetSessionsState;
import interface_adapter.get_sessions.GetSessionsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MySessionsView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "my sessions";

    // Variables for Functionality
    private final ViewManagerModel viewManagerModel;
    private ArrayList<String> sessions = new ArrayList<>();
    private GetSessionsController getSessionsController;
    private GetSessionsViewModel getSessionsViewModel;
    private UserState curUserState;


    // Buttons
    private final JButton backButton = new JButton("Back");
    private final JButton createSessionButton = new JButton("Add Session");
    private final JButton inviteToSessionButton = new JButton("Invite To Session");
    private final JButton removeFromSessionButton = new JButton("Remove From Session");
    private final JButton refreshButton = new JButton("Refresh");

    // Variables for linking to other views
    private String createSessionViewName;
    private String inviteToSessionViewName;
    private String removeFromSessionViewName;
    private String dashboardViewName;


    public MySessionsView(ViewManagerModel viewManagerModel, GetSessionsController getSessionsController,
                          GetSessionsViewModel getSessionsViewModel, UserState curUserState) {
        this.viewManagerModel = viewManagerModel;
        this.getSessionsController = getSessionsController;
        this.getSessionsViewModel = getSessionsViewModel;
        this.getSessionsViewModel.addPropertyChangeListener(this);
        this.curUserState = curUserState;

        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeHeaderButtons(gbc);
        makeSessionsList(gbc);
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

    private void makeHeaderButtons(GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel();

        formatButton(createSessionButton);
        formatButton(inviteToSessionButton);
        formatButton(removeFromSessionButton);
        formatButton(refreshButton);
        formatButton(backButton);

        buttonPanel.add(createSessionButton);
        buttonPanel.add(inviteToSessionButton);
        buttonPanel.add(removeFromSessionButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);
    }

    private void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 50));
        button.addActionListener(this);
    }

    private void makeSessionsList(GridBagConstraints gbc) {
        // Set position on page
        gbc.gridx = 0;
        gbc.gridy = 2;

        // Fill remaining page space
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel scrollPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.weightx = 1;
        gbcScroll.fill = GridBagConstraints.HORIZONTAL;
        gbcScroll.gridwidth = GridBagConstraints.REMAINDER;

        JScrollPane labelsScroll = new JScrollPane(scrollPanel);

        JButton session;
        for (String sessionID : sessions) {
            session = new JButton(sessionID);
            formatButton(session);
            scrollPanel.add(session, gbcScroll);
        }

        add(labelsScroll, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createSessionButton) {
            viewManagerModel.setActiveView(createSessionViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == inviteToSessionButton) {
            viewManagerModel.setActiveView(inviteToSessionViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == removeFromSessionButton) {
            viewManagerModel.setActiveView(removeFromSessionViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == refreshButton) {
            getSessionsController.getSessions(curUserState.getUserID());
        }
    }

    public void linkViews(String createSessionViewName, String inviteToSessionViewName, String removeFromSessionViewName, String dashboardViewName) {
        this.createSessionViewName = createSessionViewName;
        this.inviteToSessionViewName = inviteToSessionViewName;
        this.removeFromSessionViewName = removeFromSessionViewName;
        this.dashboardViewName = dashboardViewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GetSessionsState curState = getSessionsViewModel.getState();
        sessions = curState.getSessions();
        System.out.println(sessions);

        removeAll();
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeHeaderButtons(gbc);
        makeSessionsList(gbc);
        revalidate();
        repaint();
    }
}