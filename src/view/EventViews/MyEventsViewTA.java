package view.EventViews;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyEventsViewTA extends JPanel implements ActionListener {
    public final String viewName = "my events ta view";
    private final ViewManagerModel viewManagerModel;

    // Buttons
    private final JButton addToEventButton = new JButton("Add To Event");
    private final JButton removeFromEventButton = new JButton("Remove From Event");
    private final JButton backButton = new JButton("Back");

    // Variables for linking to other views
    private String addToEventViewName;
    private String removeFromEventViewName;
    private String dashboardViewName;



    public MyEventsViewTA(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeAddToEventButton(gbc);
        makeRemoveFromEventButton(gbc);
        makeBackButton(gbc);
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

    private void makeAddToEventButton(GridBagConstraints gbc) {
        addToEventButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        addToEventButton.setPreferredSize(new Dimension(addToEventButton.getPreferredSize().width, 150));
        add(addToEventButton, gbc);
        addToEventButton.addActionListener(this);
    }

    private void makeRemoveFromEventButton(GridBagConstraints gbc) {
        removeFromEventButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        removeFromEventButton.setPreferredSize(new Dimension(removeFromEventButton.getPreferredSize().width, 150));
        add(removeFromEventButton, gbc);
        removeFromEventButton.addActionListener(this);
    }

    private void makeBackButton(GridBagConstraints gbc) {
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 150));
        add(backButton, gbc);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToEventButton) {
            viewManagerModel.setActiveView(addToEventViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == removeFromEventButton) {
            viewManagerModel.setActiveView(removeFromEventViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        }
    }

    public void linkViews(String addToEventViewName, String removeFromEventViewName, String dashboardViewName) {
        this.addToEventViewName = addToEventViewName;
        this.removeFromEventViewName = removeFromEventViewName;
        this.dashboardViewName = dashboardViewName;
    }
}