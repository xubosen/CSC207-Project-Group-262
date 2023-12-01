package app;

// Data Access
import data_access.DataAccessInterface;
import data_access.file_dao.MongoDBDAO;
import data_access.in_memory_dao.*;
import data_access.HardCodedDAO;

// Interface Adapters
import interface_adapter.*;
import interface_adapter.add_to_event.*;
import interface_adapter.create_course.*;
import interface_adapter.create_event.*;
import interface_adapter.create_session.*;
import interface_adapter.enroll.*;
import interface_adapter.get_courses.GetCoursesController;
import interface_adapter.get_courses.GetCoursesPresenter;
import interface_adapter.get_courses.GetCoursesViewModel;
import interface_adapter.get_sessions.GetSessionsController;
import interface_adapter.get_sessions.GetSessionsPresenter;
import interface_adapter.get_sessions.GetSessionsViewModel;
import interface_adapter.invite_to_session.*;
import interface_adapter.login.*;
import interface_adapter.remove_from_course.*;
import interface_adapter.remove_from_session.*;
import interface_adapter.remove_from_event.*;
import interface_adapter.sign_up.*;

// Use Case Interactors
import use_case.add_to_event.EventAdditionInteractor;
import use_case.create_course.CreateCourseInteractor;
import use_case.create_event.CreateEventInteractor;
import use_case.create_session.CreateSessionInteractor;
import use_case.enroll.EnrollInteractor;
import use_case.get_courses.GetCoursesInteractor;
import use_case.get_sessions.GetSessionsInteractor;
import use_case.invite_to_session.InviteToSessionInteractor;
import use_case.log_in.LoginInteractor;
import use_case.remove_from_course.RemoveFromCourseInteractor;
import use_case.remove_from_event.RemoveFromEventInteractor;
import use_case.remove_from_session.RemoveFromSessionInteractor;
import use_case.sign_up.SignupInteractor;

// Views
import view.course_views.*;
import view.event_views.*;
import view.intro_views.LoginView;
import view.intro_views.SignUpView;
import view.session_views.CreateSessionView;
import view.session_views.InviteToSessionView;
import view.session_views.MySessionsView;
import view.session_views.RemoveFromSessionView;
import view.organizational_views.DashboardView;
import view.organizational_views.ViewManager;

// Swing Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static JFrame application;
    private static JPanel views;
    private static ArrayList<JPanel> viewList = new ArrayList<>();
    private static ViewManagerModel viewManagerModel;
    private static ViewManager viewManager;
    private static DataAccessInterface dataAccess;
    private static UserState curUserState;

    public static void main(String[] args) throws IOException {

        // Read From Data Access
        readFromDataAccess();
        InMemoryEmployeeDataAccessObject employeeDAO = dataAccess.getEmployeeDAO();
        InMemoryCourseDataAccessObject courseDAO = dataAccess.getCourseDAO();
        InMemoryEventDataAccessObject eventDAO = dataAccess.getEventDAO();
        InMemorySessionDataAccessObject sessionDAO = dataAccess.getSessionDAO();

        // Initialize Application
        initializeApplication(employeeDAO, courseDAO, eventDAO, sessionDAO);

        // Log In
        curUserState = new UserState("","");

        // Instantiate Views
        String initialViewName = initializeViews(sessionDAO, employeeDAO, courseDAO, eventDAO);

        // Set the initial view.
        viewManagerModel.setActiveView(initialViewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

    private static void initializeApplication(InMemoryEmployeeDataAccessObject employeeDAO,
                                              InMemoryCourseDataAccessObject courseDAO,
                                              InMemoryEventDataAccessObject eventDAO,
                                              InMemorySessionDataAccessObject sessionDAO) {
        // We use JFrame to Build the main program window.
        // The main panel contains cards and layout.

        // The main application window.
        application = new JFrame("CSC207 Project - Class Management HR System");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the size of the application window.
        application.setSize(1000, 600);

        CardLayout cardLayout = new CardLayout();

        // Panel that contains the views.
        views = new JPanel(cardLayout);
        application.add(views);

        // Instantiate ViewManagerModel
        viewManagerModel = new ViewManagerModel();
        viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        // Set save when frame close
        application.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                boolean saveSuccessful = false;
                try {
                    saveSuccessful = dataAccess.saveToDatabase(courseDAO, employeeDAO, eventDAO, sessionDAO);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // If save is not successful, show a confirmation dialog
                if (!saveSuccessful) {
                    int option = JOptionPane.showConfirmDialog(new JFrame(), "Data not saved! Are you sure you " +
                            "want to quit?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                    // if the user chooses yes, close the window
                    if (option == JOptionPane.YES_OPTION) {
                        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        application.dispose();
                    } else {
                        // if the user chooses no, keep the window open
                        application.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    // if the save is successful, close the window
                    application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    application.dispose();
                }
            }
        });
    }

    private static void readFromDataAccess() throws IOException {
//        dataAccess = new HardCodedDAO();
        dataAccess = new MongoDBDAO();
    }

    private static String initializeViews(InMemorySessionDataAccessObject sessionDAO,
                                          InMemoryEmployeeDataAccessObject employeeDAO,
                                          InMemoryCourseDataAccessObject courseDAO,
                                          InMemoryEventDataAccessObject eventDAO) throws IOException {

        // Instantiate LoginUseCaseView
        LoginView loginView = instantiateLoginUseCase(employeeDAO);
        addView(loginView, loginView.viewName);

        // Instantiate SignUpUseCaseView
        SignUpView signUpView = instantiateSignUpUseCase(employeeDAO);
        addView(signUpView, signUpView.viewName);


        // Instantiate DashboardView.
        DashboardView dashboardView = new DashboardView(viewManagerModel, curUserState);
        addView(dashboardView, dashboardView.viewName);



        // Instantiate MyCoursesView
        MyCoursesViewInstructor myCoursesViewInstructor = InstantiateMyCoursesInsView(employeeDAO);
        addView(myCoursesViewInstructor, myCoursesViewInstructor.viewName);

        MyCoursesViewTA myCoursesViewTA = InstantiateMyCoursesTAView(employeeDAO);
        addView(myCoursesViewTA, myCoursesViewTA.viewName);

        // Instantiate CreateCourseUseCaseView
        CreateCourseView createCourseView = instantiateCreateCourseUseCase(employeeDAO, courseDAO);
        addView(createCourseView, createCourseView.viewName);

        // Instantiate Invite to Course Use Case View
        EnrollView enrollView = instantiateEnrollUseCase(employeeDAO, courseDAO);
        addView(enrollView, enrollView.viewName);

        // Instantiate RemoveFromCourseView
        RemoveFromCourseView removeFromCourseView = instantiateRemoveFromCourseUseCase(employeeDAO, courseDAO);
        addView(removeFromCourseView, removeFromCourseView.viewName);



        // Initialize MyEvents view
        MyEventsViewInstructor myEventsViewInstructor = new MyEventsViewInstructor(viewManagerModel);
        addView(myEventsViewInstructor, myEventsViewInstructor.viewName);

        MyEventsViewTA myEventsViewTA = new MyEventsViewTA(viewManagerModel);
        addView(myEventsViewTA, myEventsViewTA.viewName);

        // Instantiate CreateEventUseCaseView
        CreateEventView createEventView = instantiateCreateEventUseCase(employeeDAO, eventDAO, courseDAO);
        addView(createEventView, createEventView.viewName);

        // Instantiate Add to Event Use Case View
        EventAdditionView addToEventView = instantiateEventAdditionUseCase(employeeDAO, eventDAO);
        addView(addToEventView, addToEventView.viewName);

        // Instantiate RemoveFromEventView
        RemoveFromEventView removeFromEventView = instantiateRemoveFromEventUseCase(employeeDAO, eventDAO);
        addView(removeFromEventView, removeFromEventView.viewName);



        // Initialize my Sessions view
        MySessionsView mySessionsView = instantiateMySessionsUseCase(employeeDAO);
        addView(mySessionsView, mySessionsView.viewName);

        // Instantiate CreateSessionUseCaseView
        CreateSessionView createSessionView = instantiateCreateSessionUseCase(eventDAO, sessionDAO);
        addView(createSessionView, createSessionView.viewName);

        // Instantiate Add to Session Use Case View
        InviteToSessionView inviteToSessionView = instantiateInviteToSessionUseCase(employeeDAO, sessionDAO);
        addView(inviteToSessionView, inviteToSessionView.viewName);

        // Instantiate RemoveFromSessionView
        RemoveFromSessionView removeFromSessionView = instantiateRemoveFromSessionUseCase(employeeDAO, sessionDAO);
        addView(removeFromSessionView, removeFromSessionView.viewName);


        // Link Views
        dashboardView.linkViews(myCoursesViewInstructor.viewName, myCoursesViewTA.viewName, myEventsViewInstructor.viewName,
                myEventsViewTA.viewName, mySessionsView.viewName, loginView.viewName);

        // Link Intro Views
        loginView.linkViews(dashboardView.viewName, signUpView.viewName);
        signUpView.linkViews(loginView.viewName);

        // Link Course Views
        myCoursesViewInstructor.linkViews(dashboardView.viewName, createCourseView.viewName, removeFromCourseView.viewName,
                enrollView.viewName);
        myCoursesViewTA.linkViews(dashboardView.viewName);
        createCourseView.linkViews(myCoursesViewInstructor.viewName);
        removeFromCourseView.linkViews(myCoursesViewInstructor.viewName);
        enrollView.linkViews(myCoursesViewInstructor.viewName);

        // Link Event Views
        myEventsViewInstructor.linkViews(createEventView.viewName, addToEventView.viewName, removeFromEventView.viewName,
                dashboardView.viewName);
        myEventsViewTA.linkViews(dashboardView.viewName, addToEventView.viewName, removeFromEventView.viewName);
        createEventView.linkViews(myEventsViewInstructor.viewName);
        removeFromEventView.linkViews(myEventsViewInstructor.viewName);
        addToEventView.linkViews(myEventsViewInstructor.viewName);

        // Link Session Views
        mySessionsView.linkViews(createSessionView.viewName, inviteToSessionView.viewName, removeFromSessionView.viewName,
                dashboardView.viewName);
        createSessionView.linkViews(mySessionsView.viewName);
        inviteToSessionView.linkViews(mySessionsView.viewName);
        removeFromSessionView.linkViews(mySessionsView.viewName);

        return loginView.viewName;
    }

    private static void addView(JPanel view, String viewName) {
        views.add(viewName, view);
        viewList.add(view);
    }

    private static LoginView instantiateLoginUseCase(InMemoryEmployeeDataAccessObject employeeDAO) throws IOException {
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(loginPresenter, employeeDAO);
        LoginController loginController = new LoginController(loginInteractor);
        LoginView loginView = new LoginView(loginViewModel, viewManagerModel, loginController, curUserState);
        return loginView;
    }

    private static SignUpView instantiateSignUpUseCase(InMemoryEmployeeDataAccessObject employeeDAO){
        SignUpViewModel signUpViewModel = new SignUpViewModel();
        SignUpPresenter signUpPresenter = new SignUpPresenter(signUpViewModel);
        SignupInteractor signUpInteractor = new SignupInteractor(signUpPresenter, employeeDAO);
        SignUpController signUpController = new SignUpController(signUpInteractor);
        SignUpView signUpView = new SignUpView(signUpController, signUpViewModel, viewManagerModel);
        return signUpView;
    }

    private static MyCoursesViewInstructor InstantiateMyCoursesInsView(InMemoryEmployeeDataAccessObject employeeDAO) {
        GetCoursesViewModel getCoursesViewModel = new GetCoursesViewModel();
        GetCoursesPresenter getCoursesPresenter = new GetCoursesPresenter(getCoursesViewModel);
        GetCoursesInteractor getCoursesInteractor = new GetCoursesInteractor(getCoursesPresenter, employeeDAO);
        GetCoursesController getCoursesController = new GetCoursesController(getCoursesInteractor);
        MyCoursesViewInstructor myCoursesViewInstructor = new MyCoursesViewInstructor(viewManagerModel,
                getCoursesController, getCoursesViewModel, curUserState);
        return myCoursesViewInstructor;
    }

    private static MyCoursesViewTA InstantiateMyCoursesTAView(InMemoryEmployeeDataAccessObject employeeDAO) {
        GetCoursesViewModel getCoursesViewModel = new GetCoursesViewModel();
        GetCoursesPresenter getCoursesPresenter = new GetCoursesPresenter(getCoursesViewModel);
        GetCoursesInteractor getCoursesInteractor = new GetCoursesInteractor(getCoursesPresenter, employeeDAO);
        GetCoursesController getCoursesController = new GetCoursesController(getCoursesInteractor);
        MyCoursesViewTA myCoursesViewTA = new MyCoursesViewTA(viewManagerModel, getCoursesController,
                getCoursesViewModel, curUserState);
        return myCoursesViewTA;
    }

    private static EnrollView instantiateEnrollUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                       InMemoryCourseDataAccessObject courseDAO) {
        EnrollViewModel enrollViewModel = new EnrollViewModel();
        EnrollPresenter enrollPresenter = new EnrollPresenter(enrollViewModel);
        EnrollInteractor enrollInteractor = new EnrollInteractor(enrollPresenter, employeeDAO, courseDAO);
        EnrollController enrollController = new EnrollController(enrollInteractor);
        EnrollView enrollView = new EnrollView(enrollController, enrollViewModel, viewManagerModel, curUserState);
        return enrollView;
    }

    private static RemoveFromCourseView instantiateRemoveFromCourseUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                           InMemoryCourseDataAccessObject courseDAO) {
        RemoveFromCourseViewModel removeFromCourseViewModel = new RemoveFromCourseViewModel();
        RemoveFromCoursePresenter removeFromCoursePresenter = new RemoveFromCoursePresenter(
                removeFromCourseViewModel);
        RemoveFromCourseInteractor removeFromCourseInteractor = new RemoveFromCourseInteractor(
                removeFromCoursePresenter, employeeDAO, courseDAO);
        RemoveFromCourseController removeFromCourseController = new RemoveFromCourseController(
                removeFromCourseInteractor);
        RemoveFromCourseView removeFromCourseView = new RemoveFromCourseView(removeFromCourseController,
                removeFromCourseViewModel, viewManagerModel);
        return removeFromCourseView;
    }


    private static CreateCourseView instantiateCreateCourseUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                   InMemoryCourseDataAccessObject courseDAO) {
        CreateCourseViewModel createCourseViewModel = new CreateCourseViewModel();
        CreateCoursePresenter createCoursePresenter = new CreateCoursePresenter(createCourseViewModel);
        CreateCourseInteractor createCourseInteractor = new CreateCourseInteractor(createCoursePresenter,
                employeeDAO, courseDAO);
        CreateCourseController createCourseController = new CreateCourseController(createCourseInteractor);
        CreateCourseView createCourseView = new CreateCourseView(createCourseController, createCourseViewModel,
                viewManagerModel, curUserState);
        return createCourseView;
    }



    private static EventAdditionView instantiateEventAdditionUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                     InMemoryEventDataAccessObject eventDAO) {

        EventAdditionViewModel eventAdditionViewModel = new EventAdditionViewModel();
        EventAdditionPresenter eventAdditionPresenter = new EventAdditionPresenter(eventAdditionViewModel);
        EventAdditionInteractor eventAdditionInteractor = new EventAdditionInteractor(eventAdditionPresenter,
                employeeDAO, eventDAO);
        EventAdditionController eventAdditionController = new EventAdditionController(eventAdditionInteractor);
        EventAdditionView eventAdditionView = new EventAdditionView(eventAdditionController, eventAdditionViewModel,
                viewManagerModel);
        return eventAdditionView;
    }

    private static CreateEventView instantiateCreateEventUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                 InMemoryEventDataAccessObject eventDAO,
                                                                 InMemoryCourseDataAccessObject courseDAO) {
        CreateEventViewModel createEventViewModel = new CreateEventViewModel();
        CreateEventPresenter createEventPresenter = new CreateEventPresenter(createEventViewModel);
        CreateEventInteractor createEventInteractor = new CreateEventInteractor(createEventPresenter, employeeDAO,
                eventDAO, courseDAO);
        CreateEventController createEventController = new CreateEventController(createEventInteractor);
        CreateEventView createEventView = new CreateEventView(createEventController, createEventViewModel,
                viewManagerModel, curUserState);
        return createEventView;
    }
    private static RemoveFromEventView instantiateRemoveFromEventUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                         InMemoryEventDataAccessObject eventDAO) {
        // To test this, set active view to "Remove From Events"
        RemoveFromEventViewModel removeFromEventViewModel = new RemoveFromEventViewModel();
        RemoveFromEventPresenter removeFromEventPresenter = new RemoveFromEventPresenter(
                removeFromEventViewModel);
        RemoveFromEventInteractor removeFromEventInteractor = new RemoveFromEventInteractor(
                removeFromEventPresenter, employeeDAO, eventDAO);
        RemoveFromEventController removeFromEventController = new RemoveFromEventController(
                removeFromEventInteractor);
        RemoveFromEventView removeFromEventView = new RemoveFromEventView(removeFromEventController,
                removeFromEventViewModel, viewManagerModel);
        return removeFromEventView;
    }

    private static MySessionsView instantiateMySessionsUseCase(InMemoryEmployeeDataAccessObject employeeDAO) {
        GetSessionsViewModel getSessionsViewModel = new GetSessionsViewModel();
        GetSessionsPresenter getSessionsPresenter = new GetSessionsPresenter(getSessionsViewModel);
        GetSessionsInteractor getSessionsInteractor = new GetSessionsInteractor(getSessionsPresenter, employeeDAO);
        GetSessionsController getSessionsController = new GetSessionsController(getSessionsInteractor);
        MySessionsView mySessionsView = new MySessionsView(viewManagerModel, getSessionsController,
                getSessionsViewModel, curUserState);
        return mySessionsView;
    }

    private static CreateSessionView instantiateCreateSessionUseCase(InMemoryEventDataAccessObject eventDAO,
                                                                     InMemorySessionDataAccessObject sessionDAO) {
        CreateSessionViewModel createSessionViewModel = new CreateSessionViewModel();
        CreateSessionPresenter createSessionPresenter = new CreateSessionPresenter(createSessionViewModel);
        CreateSessionInteractor createSessionInteractor = new CreateSessionInteractor(createSessionPresenter,
                eventDAO, sessionDAO);
        CreateSessionController createSessionController = new CreateSessionController(createSessionInteractor);
        CreateSessionView createSessionView = new CreateSessionView(createSessionController, createSessionViewModel,
                viewManagerModel);
        return createSessionView;

    }

    private static RemoveFromSessionView instantiateRemoveFromSessionUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                             InMemorySessionDataAccessObject sessionsDAO) {
        RemoveFromSessionViewModel removeFromSessionViewModel = new RemoveFromSessionViewModel();
        RemoveFromSessionPresenter removeFromSessionPresenter = new RemoveFromSessionPresenter(
                removeFromSessionViewModel);
        RemoveFromSessionInteractor removeFromSessionInteractor = new RemoveFromSessionInteractor(
                removeFromSessionPresenter, employeeDAO, sessionsDAO);
        RemoveFromSessionController removeFromSessionController = new RemoveFromSessionController(
                removeFromSessionInteractor);
        RemoveFromSessionView removeFromSessionView = new RemoveFromSessionView(removeFromSessionController,
                removeFromSessionViewModel, viewManagerModel);
        return removeFromSessionView;
    }

    private static InviteToSessionView instantiateInviteToSessionUseCase(InMemoryEmployeeDataAccessObject employeeDAO,
                                                                         InMemorySessionDataAccessObject sessionsDAO) {
        InviteToSessionViewModel inviteToSessionViewModel = new InviteToSessionViewModel();
        InviteToSessionPresenter inviteToSessionPresenter = new InviteToSessionPresenter(
                inviteToSessionViewModel);
        InviteToSessionInteractor inviteToSessionInteractor = new InviteToSessionInteractor(
                inviteToSessionPresenter, employeeDAO, sessionsDAO);
        InviteToSessionController inviteToSessionController = new InviteToSessionController(
                inviteToSessionInteractor);
        InviteToSessionView inviteToSessionView = new InviteToSessionView(inviteToSessionController,
                inviteToSessionViewModel, viewManagerModel);
        return inviteToSessionView;
    }
}