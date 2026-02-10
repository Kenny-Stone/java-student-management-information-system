package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    // using static since only one page exists and is needed for each page.
    static Person person;
    static Signup signup = new Signup();
    static Login login = new Login();
    static Dashboard dashboard = new Dashboard();
    static JFrame mainFrame = new JFrame("Main");
    static CardLayout layout = new CardLayout();
    static JPanel container = new JPanel(layout);

    static void main() {
//        mainFrame.setContentPane(signup.getSignupFrame());

        addPanel(signup.getSignupPanel(),"Signup");
        addPanel(login.getLoginPanel(),"Login");
        addPanel(dashboard.getDashboardPanel(),"Dashboard");
//        mainFrame.setSize(400,400);
        mainFrame.add(container);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLocation(100,100);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    static void addPanel(JPanel panel, String name) {
        container.add(panel,name);
    }

    static void show(String name) {
        layout.show(container,name);
    }

    static JPanel getContainer() {
        return container;
    }
    static CardLayout getLayout() {return layout;};

}