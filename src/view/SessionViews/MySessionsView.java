package view.SessionViews;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySessionsView extends JPanel implements ActionListener {
    public final String viewName = "my sessions view";
    private final String createSessionViewName = "create session view";
    private final ViewManagerModel viewManagerModel;
    private final JButton backButton = new JButton("Back");
    private final JButton createSessionButton = new JButton("Add Session");

    public MySessionsView(ViewManagerModel viewManagerModel) {
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
        JLabel headingLabel = new JLabel("My Sessions", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(headingLabel, gbc);
    }

    private void makeEventButtons(GridBagConstraints gbc) {
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
            viewManagerModel.setActiveView(createSessionViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView("dashboard view");
            viewManagerModel.firePropertyChanged();
        }
    }
}