package view.EventViews;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyEventsView extends JPanel implements ActionListener {
    public final String viewName = "my events view";
    private final JButton lecturesButton = new JButton("Lectures");
    private final JButton tutorialsButton = new JButton("Tutorials");
    private final String createEventViewName = "create event view";
    private final ViewManagerModel viewManagerModel;

    private final JButton backButton = new JButton("Back");
    private final JButton createEventButton = new JButton("Create Event");

    public MyEventsView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeEventButtons(gbc);
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

    private void makeEventButtons(GridBagConstraints gbc) {
        createEventButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        createEventButton.setPreferredSize(new Dimension(createEventButton.getPreferredSize().width, 150));
        add(createEventButton, gbc);
        createEventButton.addActionListener(this);

        lecturesButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        lecturesButton.setPreferredSize(new Dimension(lecturesButton.getPreferredSize().width, 150));
        add(lecturesButton, gbc);
        lecturesButton.addActionListener(this);

        tutorialsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        tutorialsButton.setPreferredSize(new Dimension(tutorialsButton.getPreferredSize().width, 150));
        add(tutorialsButton, gbc);
        tutorialsButton.addActionListener(this);
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
        if (e.getSource() == lecturesButton) {
            System.out.println("Lectures button pressed");
            viewManagerModel.setActiveView("event addition");
            viewManagerModel.firePropertyChanged();
            // Perform action for Lectures button
        } else if (e.getSource() == tutorialsButton) {
            System.out.println("Tutorials button pressed");
            viewManagerModel.setActiveView("event addition");
            viewManagerModel.firePropertyChanged();
            // Perform action for Tutorials button
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView("dashboard");
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == createEventButton) {
            viewManagerModel.setActiveView(createEventViewName);
            viewManagerModel.firePropertyChanged();
        }
    }
}