package app;

import data_access.FileTADataAccessObject;
import entity.TeachingAssistantFactory;
import interface_adapter.DashboardViewModel;
import interface_adapter.LoginViewModel;
import interface_adapter.ViewManagerModel;
import view.LeaveRequestView;
import view.LoginView;
import view.DashboardView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class testMain {
    public static void main(String[] args) {
        FileTADataAccessObject fileTADataAccessObject;
//
//        TeachingAssistant alexander = new TeachingAssistant("phanale4", "Alexander", "alexander.phan@mail.utoronto.ca", "123")

        try {
            fileTADataAccessObject = new FileTADataAccessObject("./teachingAssistants.csv", new TeachingAssistantFactory());
            System.out.println(fileTADataAccessObject.getAccounts());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
