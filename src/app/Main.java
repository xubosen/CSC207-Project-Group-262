package app;

import data_access.*;
import interface_adapter.*;
import use_case.EnrollInteractor;
import use_case.EventAdditionInteractor;
import use_case.RemoveFromSessionInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    // TODO: Make sure that course admin type is Instructor.
    // TODO: Should TA's be able to remove themselves from the course in gui
    // TOdo: What happens when the course admin removes themselves. Maybe they just can't remove themselves.
    // TODO: Should cal event be stored as a separate database
    // TODO: Should the course admin be the only one allowed to remove people. They are the only one that can add so maybe only person that can remove.
    // TODO: Ask the TA how they save the information to the database when the program runs. Does it periodically update the database off of in memory or does it only update the database when program is close.
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

        // Initialize MyEvents view
        MyEventsView myEventsView = new MyEventsView(viewManagerModel);
        views.add(myEventsView, myEventsView.viewName);

        // Instantiate DashboardView.
        DashboardView dashboardView = new DashboardView(viewManagerModel, leaveRequestView.viewName, myCoursesView.viewName, myEventsView.viewName);
        views.add(dashboardView, dashboardView.viewName);

        // Instantiate LoginView with reference to DashboardView's name.
        LoginView loginView = new LoginView(loginViewModel, viewManagerModel, dashboardView.viewName);
        views.add(loginView, loginView.viewName);


        // Pull from the employees collection to make in memory employees.
        FileEmployeeDataAccessObject employeeDataAccessObject;
        try {
        employeeDataAccessObject = new FileEmployeeDataAccessObject("./database.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InMemoryEmployeeDataAccessObject employeeDAO1 = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());

        // Pull from the courses collection to make in memory courses.
        FileCourseDataAccessObject courseDataAccessObject = new FileCourseDataAccessObject(employeeDAO1);
        InMemoryCourseDataAccessObject courseDAO1;
        courseDAO1 = new InMemoryCourseDataAccessObject(courseDataAccessObject.getCourses());

        // Pull from events collection to make in memory events.
        FileEventDataAccessObject eventDataAccessObject = new FileEventDataAccessObject(employeeDAO1, courseDAO1);
        InMemoryEventDataAccessObject eventDAO1;
        eventDAO1 = new InMemoryEventDataAccessObject(eventDataAccessObject.getEvents());

        // Lastly pull from sessions collection to make the sessions for all employees.
        FileSessionDataAccessObject sessionDataAccessObject = new FileSessionDataAccessObject(employeeDAO1, eventDAO1);
        InMemorySessionDataAccessObject sessionDAO1;
        sessionDAO1 = new InMemorySessionDataAccessObject(sessionDataAccessObject.getSessions());


        instantiateEnrollUseCase(employeeDAO1, courseDAO1, views, viewManagerModel, mySessionsView);

        // Instantiate RemoveFromSessionView
        instantiateRemoveFromSessionUseCase(employeeDAO1, sessionDAO1, views, viewManagerModel, mySessionsView);

        // Instantiate EventAddition
        instantiateEventAdditionUseCase(employeeDAO1, eventDAO1, views, viewManagerModel, myEventsView);

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

    private static void instantiateEventAdditionUseCase(InMemoryEmployeeDataAccessObject employeeDAO, InMemoryEventDataAccessObject eventDAO, JPanel views, ViewManagerModel viewManagerModel, MyEventsView myEventsView) {

        EventAdditionViewModel eventAdditionViewModel = new EventAdditionViewModel();
        EventAdditionPresenter eventAdditionPresenter = new EventAdditionPresenter(eventAdditionViewModel);
        EventAdditionInteractor eventAdditionInteractor = new EventAdditionInteractor(eventAdditionPresenter, employeeDAO, eventDAO);
        EventAdditionController eventAdditionController = new EventAdditionController(eventAdditionInteractor);
        EventAdditionView eventAdditionView = new EventAdditionView(eventAdditionController, eventAdditionViewModel, viewManagerModel, myEventsView.viewName);
        views.add(eventAdditionView.viewName, eventAdditionView);

    }

}