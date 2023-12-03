package view.course_views;

import interface_adapter.UserState;
import interface_adapter.ViewManagerModel;
import interface_adapter.get_courses.GetCoursesController;
import interface_adapter.get_courses.GetCoursesState;
import interface_adapter.get_courses.GetCoursesViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MyCoursesViewInstructor extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "my courses instructor view";

    // Variables for Functionality
    private final ViewManagerModel viewManagerModel;
    private GetCoursesController getCoursesController;
    private GetCoursesViewModel getCoursesViewModel;
    private UserState userState;
    private ArrayList<String> courses = new ArrayList<>();



    // Variables for UI elements
    private final JButton createCoursesButton = new JButton("Create Course");

    private final JButton inviteToCourseButton = new JButton("Invite to Course");
    private final JButton removeFromCourseButton = new JButton("Remove from Course");
    private final JButton refreshButton = new JButton("Refresh");
    private final JButton backButton = new JButton("Back");


    // Variables for linking to other views
    private String dashboardViewName;
    private String createCourseViewName;
    private String inviteToCourseViewName;
    private String removeFromCourseViewName;



    public MyCoursesViewInstructor(ViewManagerModel viewManagerModel, GetCoursesController getCoursesController,
                                   GetCoursesViewModel getCoursesViewModel, UserState userState) {
        this.viewManagerModel = viewManagerModel;
        this.getCoursesController = getCoursesController;
        this.getCoursesViewModel = getCoursesViewModel;
        this.getCoursesViewModel.addPropertyChangeListener(this);

        this.userState = userState;

        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeHeaderButtons(gbc);

        makeCourseList(gbc);
    }

    private GridBagConstraints formatScreenLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private void makeHeading(GridBagConstraints gbc) {
        JLabel headingLabel = new JLabel("My Courses", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font for the heading
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        add(headingLabel, gbc);
    }

    private void makeHeaderButtons(GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel();

        formatButton(createCoursesButton);
        formatButton(inviteToCourseButton);
        formatButton(removeFromCourseButton);
        formatButton(refreshButton);
        formatButton(backButton);

        buttonPanel.add(createCoursesButton);
        buttonPanel.add(inviteToCourseButton);
        buttonPanel.add(removeFromCourseButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        add(buttonPanel, gbc);
    }

    private void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 50));
        if (button.getActionListeners().length == 0) {
            button.addActionListener(this);
        }
    }

    private void makeCourseList(GridBagConstraints gbc) {
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
        for (String courseID : courses) {
            courseButton = new JButton(courseID);
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
        } else if (e.getSource() == createCoursesButton) {
            viewManagerModel.setActiveView(createCourseViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == inviteToCourseButton) {
            viewManagerModel.setActiveView(inviteToCourseViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == removeFromCourseButton) {
            viewManagerModel.setActiveView(removeFromCourseViewName);
            viewManagerModel.firePropertyChanged();
        } else if (e.getSource() == refreshButton) {
            getCoursesController.getCourses(userState.getUserID());
        }
    }

    public void linkViews(String dashboardViewName, String createCourseViewName, String removeFromCourseViewName,
                          String inviteToCourseViewName) {
        this.dashboardViewName = dashboardViewName;
        this.createCourseViewName = createCourseViewName;
        this.removeFromCourseViewName = removeFromCourseViewName;
        this.inviteToCourseViewName = inviteToCourseViewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        GetCoursesState curState = getCoursesViewModel.getState();
        courses = curState.getCourses();

        removeAll();
        GridBagConstraints gbc = formatScreenLayout();
        makeHeading(gbc);
        makeHeaderButtons(gbc);
        makeCourseList(gbc);
        revalidate();
        repaint();
    }
}