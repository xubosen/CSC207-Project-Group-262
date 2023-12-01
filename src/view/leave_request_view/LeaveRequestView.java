package view.leave_request_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

import interface_adapter.request_leave.LeaveRequestViewModel;
import interface_adapter.ViewManagerModel;

public class LeaveRequestView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "leaveRequest";
    private final String dashboardViewName;

    private final ViewManagerModel viewManagerModel;
    private JComboBox<Integer> startDay, startMonth, startYear;
    private JComboBox<Integer> endDay, endMonth, endYear;
    final JButton returnButton;

    public LeaveRequestView(LeaveRequestViewModel leaveRequestViewModel, ViewManagerModel viewManagerModel, String dashboardViewName) throws IOException  {
        this.viewManagerModel = viewManagerModel;
        this.dashboardViewName = dashboardViewName;

        setLayout(new GridLayout(1, 2)); // 1 row, 2 columns layout

        // Left Panel with Icon
        JPanel leftPanel = new JPanel();
        try {
            BufferedImage image = ImageIO.read(new File("./src/images/leaveRequestIcon.jpeg")); // Replace with your image path
            JLabel logo = new JLabel(new ImageIcon(image));
            leftPanel.add(logo);
        } catch (IOException ex) {
            leftPanel.add(new JLabel("Image not found")); // Fallback text
        }

        // Right Panel with Date Selection and Buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Date Selection Components
        initializeDateComponents();

        // Panels for start and end dates
        JPanel startDatePanel = new JPanel();
        startDatePanel.add(new JLabel("Start Date:"));
        startDatePanel.add(startDay);
        startDatePanel.add(startMonth);
        startDatePanel.add(startYear);

        JPanel endDatePanel = new JPanel();
        endDatePanel.add(new JLabel("End Date:"));
        endDatePanel.add(endDay);
        endDatePanel.add(endMonth);
        endDatePanel.add(endYear);

        rightPanel.add(startDatePanel);
        rightPanel.add(endDatePanel);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Request Leave");
        submitButton.addActionListener(e -> handleSubmit());
        buttonsPanel.add(submitButton);

        returnButton = new JButton("Return to Dashboard");
        // Add action listener for returnButton if needed
        returnButton.addActionListener(this);
        buttonsPanel.add(returnButton);

        rightPanel.add(buttonsPanel);

        // Add panels to the main panel
        add(leftPanel);
        add(rightPanel);
    }

    private void addDateSelectionComponent(JPanel panel, String label, JComboBox<Integer> day, JComboBox<Integer> month, JComboBox<Integer> year) {
        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel(label));
        datePanel.add(day);
        datePanel.add(month);
        datePanel.add(year);
        panel.add(datePanel);
    }

    private void initializeDateComponents() {
        // Initialize JComboBoxes for start date
        startDay = new JComboBox<>(getIntArray(1, 31));
        startMonth = new JComboBox<>(getIntArray(1, 12));
        startYear = new JComboBox<>(getIntArray(2020, 2030)); // Adjust range as needed

        // Initialize JComboBoxes for end date
        endDay = new JComboBox<>(getIntArray(1, 31));
        endMonth = new JComboBox<>(getIntArray(1, 12));
        endYear = new JComboBox<>(getIntArray(2020, 2030)); // Adjust range as needed

        // Get current date
        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        int todayMonth = today.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int todayYear = today.get(Calendar.YEAR);

        // Set default start and end dates to today's date
        startDay.setSelectedItem(todayDay);
        startMonth.setSelectedItem(todayMonth);
        startYear.setSelectedItem(todayYear);

        endDay.setSelectedItem(todayDay);
        endMonth.setSelectedItem(todayMonth);
        endYear.setSelectedItem(todayYear);
    }


    private Integer[] getIntArray(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().toArray(Integer[]::new);
    }

    private void handleSubmit() {
        int startDayValue = (int) startDay.getSelectedItem();
        int startMonthValue = (int) startMonth.getSelectedItem();
        int startYearValue = (int) startYear.getSelectedItem();

        int endDayValue = (int) endDay.getSelectedItem();
        int endMonthValue = (int) endMonth.getSelectedItem();
        int endYearValue = (int) endYear.getSelectedItem();

        // Print or process the selected dates
        System.out.println("Start Date: " + startDayValue + "/" + startMonthValue + "/" + startYearValue);
        System.out.println("End Date: " + endDayValue + "/" + endMonthValue + "/" + endYearValue);
        // Further processing can be done here (like validation, sending data to backend, etc.)
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == returnButton) {
            viewManagerModel.setActiveView(dashboardViewName);
            viewManagerModel.firePropertyChanged();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
};
