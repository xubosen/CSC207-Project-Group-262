package app;

import data_access.InMemoryCourseDataAccessObject;
import data_access.InMemoryEmployeeDataAccessObject;
import interface_adapter.*;
import use_case.EnrollInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // We use JFrame ato Build the main program window.
        // The main panel contains cards and layout.

        // The main application window.
        JFrame application = new JFrame("Human Resources Manager");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // Panel that contains the views.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // Manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // ViewModels for the views.
        LoginViewModel loginViewModel = new LoginViewModel();

        // Instantiate LeaveReqeustView
        LeaveRequestView leaveRequestView = new LeaveRequestView();
        views.add(leaveRequestView, leaveRequestView.viewName);

        // Instantiate MyCoursesView
        MyCoursesView myCoursesView = new myCoursesView();
        views.add(myCoursesView, myCoursesView.viewName);

        // Instantiate MySessionsView
        MySessionsView mySessionsView = new MySessionsView();
        views.add(mySessionsView, mySessionsView.viewName);

        // Instantiate DashboardView.
        DashboardView dashboardView = new DashboardView(viewManagerModel, leaveRequestView.viewName, myCoursesView.viewName);
        views.add(dashboardView, dashboardView.viewName);

        // Instantiate LoginView with reference to DashboardView's name.
        LoginView loginView = new LoginView(loginViewModel, viewManagerModel, dashboardView.viewName);
        views.add(loginView, loginView.viewName);

        // Instantiate Enroll Use Case
        InMemoryCourseDataAccessObject courseDAO = new InMemoryCourseDataAccessObject();
        InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject();
        EnrollViewModel enrollViewModel = new EnrollViewModel();
        EnrollPresenter enrollPresenter = new EnrollPresenter(enrollViewModel);
        EnrollInteractor enrollInteractor = new EnrollInteractor(enrollPresenter, employeeDAO, courseDAO);
        EnrollController enrollController = new EnrollController(enrollInteractor);
        EnrollView enrollView = new EnrollView(enrollController, enrollViewModel, viewManagerModel,
                mySessionsView.viewName);

        // Set the initial view.
        views.add(enrollView, enrollView.viewName);


        viewManagerModel.setActiveView(enrollView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}