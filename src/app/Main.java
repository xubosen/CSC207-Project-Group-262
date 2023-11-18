package app;

import data_access.HardCodedDAO;
import data_access.InMemoryCourseDataAccessObject;
import data_access.InMemoryEmployeeDataAccessObject;
import data_access.InMemorySessionDataAccessObject;
import interface_adapter.*;
import use_case.EnrollInteractor;
import use_case.RemoveFromSessionInteractor;
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

        // Set the size of the application window.
        application.setSize(1000, 600);

        CardLayout cardLayout = new CardLayout();

        // Panel that contains the views.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // Manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // ViewModels for the views.
        LoginViewModel loginViewModel = new LoginViewModel();

        // Initialize my sessions view
        MySessionsView mySessionsView= new MySessionsView(viewManagerModel);
        views.add(mySessionsView, mySessionsView.viewName);

        // Instantiate MyCoursesView
        MyCoursesView myCoursesView = new MyCoursesView(viewManagerModel);
        views.add(myCoursesView, myCoursesView.viewName);

        // Instantiate LeaveRequestView
        LeaveRequestView leaveRequestView = new LeaveRequestView();
        views.add(leaveRequestView, leaveRequestView.viewName);

        // Instantiate DashboardView.
        DashboardView dashboardView = new DashboardView(viewManagerModel, leaveRequestView.viewName, myCoursesView.viewName);
        views.add(dashboardView, dashboardView.viewName);

        // Instantiate LoginView with reference to DashboardView's name.
        LoginView loginView = new LoginView(loginViewModel, viewManagerModel, dashboardView.viewName);
        views.add(loginView, loginView.viewName);

        // Load Data (TODO: Replace this with our actual database access once it is implemented.)
        HardCodedDAO dataAccess = new HardCodedDAO();
        InMemoryEmployeeDataAccessObject employeeDAO = dataAccess.getEmployeeDAO();
        InMemoryCourseDataAccessObject courseDAO = dataAccess.getCourseDAO();
        InMemorySessionDataAccessObject sessionDAO = dataAccess.getSessionDAO();

        // Instantiate EnrollView
        instantiateEnrollUseCase(employeeDAO, courseDAO, views, viewManagerModel, mySessionsView);

        // Instantiate RemoveFromSessionView
        instantiateRemoveFromSessionUseCase(employeeDAO, sessionDAO, views, viewManagerModel, mySessionsView);

        // Set the initial view.
        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

    private static void instantiateEnrollUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                          InMemoryCourseDataAccessObject courseDAO,
                                          JPanel views, ViewManagerModel viewManagerModel,
                                          MySessionsView mySessionsView) {
        EnrollViewModel enrollViewModel = new EnrollViewModel();
        EnrollPresenter enrollPresenter = new EnrollPresenter(enrollViewModel);
        EnrollInteractor enrollInteractor = new EnrollInteractor(enrollPresenter, employeeDAO, courseDAO);
        EnrollController enrollController = new EnrollController(enrollInteractor);
        EnrollView enrollView = new EnrollView(enrollController, enrollViewModel, viewManagerModel,
                mySessionsView.viewName);
        views.add(enrollView.viewName, enrollView);
    }

    private static void instantiateRemoveFromSessionUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                          InMemorySessionDataAccessObject sessionsDAO,
                                          JPanel views, ViewManagerModel viewManagerModel,
                                          MySessionsView mySessionsView) {
        // To test this, set active view to "Remove From Sessions"
        RemoveFromSessionViewModel removeFromSessionViewModel = new RemoveFromSessionViewModel();
        RemoveFromSessionPresenter removeFromSessionPresenter = new RemoveFromSessionPresenter(
                removeFromSessionViewModel);
        RemoveFromSessionInteractor removeFromSessionInteractor = new RemoveFromSessionInteractor(
                removeFromSessionPresenter, employeeDAO, sessionsDAO);
        RemoveFromSessionController removeFromSessionController = new RemoveFromSessionController(
                removeFromSessionInteractor);
        RemoveFromSessionView removeFromSessionView = new RemoveFromSessionView(removeFromSessionController,
                removeFromSessionViewModel, viewManagerModel, mySessionsView.viewName);
        views.add(removeFromSessionView.viewName, removeFromSessionView);
    }

}