package view.event_views;

import interface_adapter.UserState;
import interface_adapter.ViewManagerModel;
import interface_adapter.get_events.GetEventController;
import interface_adapter.get_events.GetEventViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MyEventsViewTA extends JPanel implements PropertyChangeListener, ActionListener {
    public final String viewName = "my events ta view";
    // Variables for Functionality
    private ViewManagerModel viewManagerModel;
    private ArrayList<String> events = new ArrayList<>();
    private UserState userState;
    private GetEventController getEventController;
    private GetEventViewModel getEventViewModel;

    // Buttons
    private final JButton createEventButton = new JButton("Create Event");
    private final JButton backButton = new JButton("Back");
    private final JButton refreshButton = new JButton("Refresh");

    // Variables for linking to other views
    private String createEventViewName;
    private String dashboardViewName;


    public MyEventsViewTA(ViewManagerModel viewManagerModel, GetEventController getEventController,
                                  GetEventViewModel getEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getEventController = getEventController;
        this.getEventViewModel = getEventViewModel;
        this.getEventViewModel.addPropertyChangeListener(this);

        this.userState = userState;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeHeaderButtons(gbc);
        makeEventsList(gbc);
    }

    private GridBagConstraints formatScreenLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private void makeHeading(GridBagConstraints gbc) {
        JLabel headingLabel = new JLabel("My Events", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(headingLabel, gbc);
    }

    private void makeHeaderButtons(GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel();

        formatButton(refreshButton);
        formatButton(backButton);

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
        if (button.getActionListeners().length == 0) {
            button.addActionListener(this);
        }
    }

    private void makeEventsList(GridBagConstraints gbc) {
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

        JButton courseButton;
        for (String eventID : events) {
            courseButton = new JButton(eventID);
            formatButton(courseButton);
            scrollPanel.add(courseButton, gbcScroll);
        }

        add(labelsScroll, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == refreshButton) {
            getEventController.getEvent(userState.getUserID());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        events = getEventViewModel.getState().getEventIDs();

        removeAll();
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeHeaderButtons(gbc);
        makeEventsList(gbc);
        revalidate();
    }

    public void linkViews(String dashboardViewName) {
        this.dashboardViewName = dashboardViewName;
    }
}