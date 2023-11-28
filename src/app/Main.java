package app;

import data_access.*;
import interface_adapter.*;
import interface_adapter.create_event.CreateEventController;
import interface_adapter.create_event.CreateEventPresenter;
import interface_adapter.create_event.CreateEventViewModel;
import interface_adapter.enroll.EnrollController;
import interface_adapter.enroll.EnrollPresenter;
import interface_adapter.enroll.EnrollViewModel;
import interface_adapter.remove_from_session.RemoveFromSessionController;
import interface_adapter.remove_from_session.RemoveFromSessionPresenter;
import interface_adapter.remove_from_session.RemoveFromSessionViewModel;
import use_case.create_event.CreateEventInteractor;
import use_case.LoginInteractor;
import use_case.enroll.EnrollInteractor;
import interface_adapter.create_course.CreateCourseController;
import interface_adapter.create_course.CreateCoursePresenter;
import interface_adapter.create_course.CreateCourseViewModel;
import use_case.create_course.CreateCourseInteractor;
import use_case.EventAdditionInteractor;
import use_case.remove_from_session.RemoveFromSessionInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    // TODO: Iterate through logged in users information to create their views for sessions, events and courses.
    // TODO: Should TA's be able to remove themselves from the course in gui
    // TOdo: What happens when the course admin removes themselves. Maybe they just can't remove themselves.
    // TODO: Should the course admin be the only one allowed to remove people. They are the only one that can add so maybe only person that can remove.
    // TODO: Ask the TA how they save the information to the database when the program runs. Does it periodically update the database off of in memory or does it only update the database when program is close.
    // Answer to above todo was to save it any point the system updates the InMemoryDAO but we decided to update on program close.
    public static void main(String[] args) throws IOException {
        // We use JFrame to Build the main program window.
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
        LeaveRequestViewModel leaveRequestViewModel = new LeaveRequestViewModel();

        // Initialize my sessions view
        MySessionsView mySessionsView= new MySessionsView(viewManagerModel);
        views.add(mySessionsView, mySessionsView.viewName);

        // Instantiate MyCoursesView
        MyCoursesView myCoursesView = new MyCoursesView(viewManagerModel);
        views.add(myCoursesView, myCoursesView.viewName);

        // Instantiate LeaveRequestView
        LeaveRequestView leaveRequestView = new LeaveRequestView(leaveRequestViewModel, viewManagerModel, "dashboard");
        views.add(leaveRequestView, leaveRequestView.viewName);

        // Initialize MyEvents view
        MyEventsView myEventsView = new MyEventsView(viewManagerModel);
        views.add(myEventsView, myEventsView.viewName);

        //Instantiate View Models
        DashboardViewModel dashboardViewModel = new DashboardViewModel(); // Instantiate DashboardViewModel

        // Instantiate DashboardView.
        DashboardView dashboardView = new DashboardView(viewManagerModel, leaveRequestView.viewName, myCoursesView.viewName);
        views.add(dashboardView, dashboardView.viewName);

        // Instantiate LoginView with reference to DashboardView's name.
        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, dashboardViewModel);
        views.add(loginView, loginView.viewName);

        // Pull from the employees collection to make in memory employees.
        // main should only call interactor
        // This would initialize the DAO calls database to retrieve json files.
        // Interactor would take json files and create the entities InMemory.
        // The factory helps interactor make fleshed out entities.

        // Make all In Memory DAO
        ArrayList<Object> allDAO = MakeAllDAO();
        InMemoryEmployeeDataAccessObject employeeDAO = (InMemoryEmployeeDataAccessObject) allDAO.get(0);
        InMemoryCourseDataAccessObject courseDAO = (InMemoryCourseDataAccessObject) allDAO.get(1);
        InMemoryEventDataAccessObject eventDAO = (InMemoryEventDataAccessObject) allDAO.get(2);
        InMemorySessionDataAccessObject sessionDAO = (InMemorySessionDataAccessObject) allDAO.get(3);

        // Instantiate CreateUseCaseView
        instantiateCreateCourseUseCase(employeeDAO, courseDAO, views, viewManagerModel);

        // Instantiate CreateEventUseCaseView
        instantiateCreateEventUseCase(employeeDAO, eventDAO, courseDAO, views, viewManagerModel);

        // Instantiate EnrollUseCaseView
        instantiateEnrollUseCase(employeeDAO, courseDAO, views, viewManagerModel, mySessionsView);

        // Instantiate RemoveFromSessionView
        instantiateRemoveFromSessionUseCase(employeeDAO, sessionDAO, views, viewManagerModel, mySessionsView);

        // Instantiate EventAddition
        instantiateEventAdditionUseCase(employeeDAO, eventDAO, views, viewManagerModel, myEventsView);

        // Set the initial view.
        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

    private static ArrayList<Object> MakeAllDAO() throws IOException {
        // TODO: Consider saving the FileDAO as well
        ArrayList<Object> allDAO = new ArrayList<>();
        FileEmployeeDataAccessObject employeeDataAccessObject;
        try {
            employeeDataAccessObject = new FileEmployeeDataAccessObject("./employeeInformation.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InMemoryEmployeeDataAccessObject employeeDAO = new InMemoryEmployeeDataAccessObject(employeeDataAccessObject.getAccount());
        allDAO.add(employeeDAO);

        // Pull from the courses collection to make in memory courses.
        // Error occurs here
        FileCourseDataAccessObject courseDataAccessObject = new FileCourseDataAccessObject("./courseInformation.csv", employeeDAO);
        InMemoryCourseDataAccessObject courseDAO;
        courseDAO = new InMemoryCourseDataAccessObject(courseDataAccessObject.getCourses());
        allDAO.add(courseDAO);

        // Pull from events collection to make in memory events.
        FileEventDataAccessObject eventDataAccessObject = new FileEventDataAccessObject("./eventInformation.csv", employeeDAO, courseDAO);
        InMemoryEventDataAccessObject eventDAO;
        eventDAO = new InMemoryEventDataAccessObject(eventDataAccessObject.getEvents());
        allDAO.add(eventDAO);

        // Lastly pull from sessions collection to make the sessions for all employees.
        FileSessionDataAccessObject sessionDataAccessObject = new FileSessionDataAccessObject("./sessionInformation.csv", employeeDAO, eventDAO);
        InMemorySessionDataAccessObject sessionDAO;
        sessionDAO = new InMemorySessionDataAccessObject(sessionDataAccessObject.getSessions());
        allDAO.add(sessionDAO);

        return allDAO;
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

    private static void instantiateCreateCourseUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                       InMemoryCourseDataAccessObject courseDAO, JPanel views,
                                                       ViewManagerModel viewManagerModel) {
        CreateCourseViewModel createCourseViewModel = new CreateCourseViewModel();
        CreateCoursePresenter createCoursePresenter = new CreateCoursePresenter(createCourseViewModel);
        CreateCourseInteractor createCourseInteractor = new CreateCourseInteractor(createCoursePresenter,
                employeeDAO, courseDAO);
        CreateCourseController createCourseController = new CreateCourseController(createCourseInteractor);
        CreateCourseView createCourseView = new CreateCourseView(createCourseController, createCourseViewModel,
                viewManagerModel);
        views.add(createCourseView.viewName, createCourseView);
    }

    private static void instantiateCreateEventUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                       InMemoryEventDataAccessObject eventDAO,
                                                       InMemoryCourseDataAccessObject courseDAO, JPanel views,
                                                       ViewManagerModel viewManagerModel) {
        CreateEventViewModel createEventViewModel = new CreateEventViewModel();
        CreateEventPresenter createEventPresenter = new CreateEventPresenter(createEventViewModel);
        CreateEventInteractor createEventInteractor = new CreateEventInteractor(createEventPresenter, employeeDAO,
                eventDAO, courseDAO);
        CreateEventController createEventController = new CreateEventController(createEventInteractor);
        CreateEventView createEventView = new CreateEventView(createEventController, createEventViewModel,
                viewManagerModel);
        views.add(createEventView.viewName, createEventView);
    }
}