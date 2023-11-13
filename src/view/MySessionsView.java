package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interface_adapter.ViewManagerModel;

import javax.swing.*;


public class MySessionsView extends JPanel implements ActionListener {
    public final String viewName = "MySessionsView";
    private final JButton inviteButton = new JButton("Invite");
    private final JButton backButton = new JButton("Back");
    private ViewManagerModel viewManagerModel;

    private String myCoursesViewName;
    private String inviteViewName;

    public MySessionsView (ViewManagerModel viewManagerModel, String inviteViewName, String myCoursesViewName) {
        this.viewManagerModel = viewManagerModel;
        this.myCoursesViewName = myCoursesViewName;
        this.inviteViewName = inviteViewName;

        setSize(1000, 600);
        setLayout(new GridLayout(4, 2));

        add(inviteButton);
        add(backButton);

        inviteButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inviteButton) {
            viewManagerModel.setActiveView(this.inviteViewName);
        } else if (e.getSource() == backButton) {
            viewManagerModel.setActiveView(this.myCoursesViewName);
        }
    }
}
