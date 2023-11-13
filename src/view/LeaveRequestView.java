package view;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.stream.IntStream;

public class LeaveRequestView extends JPanel {
    private JComboBox<Integer> startDay, startMonth, startYear;
    private JComboBox<Integer> endDay, endMonth, endYear;

    public LeaveRequestView() {
        setLayout(new GridLayout(3, 1)); // Layout for start date, end date, and submit button

        // Initialize JComboBoxes for start date
        startDay = new JComboBox<>(getIntArray(1, 31));
        startMonth = new JComboBox<>(getIntArray(1, 12));
        startYear = new JComboBox<>(getIntArray(2020, 2030)); // Adjust range as needed

        // Initialize JComboBoxes for end date
        endDay = new JComboBox<>(getIntArray(1, 31));
        endMonth = new JComboBox<>(getIntArray(1, 12));
        endYear = new JComboBox<>(getIntArray(2020, 2030)); // Adjust range as needed

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

        // Submit button
        JButton submitButton = new JButton("Request Leave");
        submitButton.addActionListener(e -> handleSubmit());

        // Add panels to LeaveRequestView
        add(startDatePanel);
        add(endDatePanel);
        add(submitButton);
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
}
