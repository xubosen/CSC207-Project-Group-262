package view;

import javax.swing.*;
import java.awt.*;
public class MyCoursesView extends JFrame{

    public MyCoursesView() {
        // Set up the frame
        setTitle("My Courses");
        setSize(1000, 600); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the GridBagLayout for content pane
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Create a label for the heading with bold style and larger font size
        JLabel headingLabel = new JLabel("My Courses", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font for the heading
        constraints.gridx = 0; // Align to the first column
        constraints.gridy = 0; // Align to the first row
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        constraints.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        getContentPane().add(headingLabel, constraints);

        // Create the button with larger font
        JButton csc207Button = new JButton("Csc207");
        csc207Button.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font for the button
        constraints.gridx = 0; // Align to the first column
        constraints.gridy = 1; // Align to the second row
        constraints.weightx = 1; // Take up all horizontal space
        constraints.fill = GridBagConstraints.HORIZONTAL; // Fill the horizontal space
        constraints.insets = new Insets(10, 0, 0, 0); // Top padding
        // Increase the button height
        csc207Button.setPreferredSize(new Dimension(csc207Button.getPreferredSize().width, 150));
        getContentPane().add(csc207Button, constraints);
    }

    public static void main(String[] args) {
        // Run the GUI form
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyCoursesView().setVisible(true);
            }
        });
    }
}