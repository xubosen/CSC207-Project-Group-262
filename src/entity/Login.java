package entity;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Login {
    private static final String LOGIN_SUCCESS = "Login successful!";
    private static final String LOGIN_FAILED = "Login failed. Please try again.";

    public void run() {
        // Use try-with-resources to ensure that the resources are closed after use
        try (Scanner scan = new Scanner(new File("the/dir/myFile.extension"));
             Scanner keyboard = new Scanner(System.in)) {

            String user = scan.nextLine();
            String pass = scan.nextLine(); // looks at selected file in scan

            System.out.println("Enter username:");
            String inpUser = keyboard.nextLine();
            System.out.println("Enter password:");
            String inpPass = keyboard.nextLine(); // gets input from user

            if (inpUser.equals(user) && inpPass.equals(pass)) {
                System.out.println(LOGIN_SUCCESS);
            } else {
                System.out.println(LOGIN_FAILED);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The login file was not found.");
        }
    }
}
