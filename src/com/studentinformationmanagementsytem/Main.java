package com.studentinformationmanagementsytem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    // using static since only one page exists and is needed for each page.
    Person person;
    static Signup signup = new Signup();
    static Login login = new Login();
    static JFrame mainFrame = new JFrame("Main");
    static CardLayout layout = new CardLayout();
    static JPanel container = new JPanel(layout);

    static void main() {
//        mainFrame.setContentPane(signup.getSignupFrame());

        container.add(signup.getSignupPanel(), "Signup");
        container.add(login.getLoginPanel(), "Login");
//        mainFrame.setSize(400,400);
        mainFrame.add(container);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        performAllPanelActions();

    }

    static void performSignupActions() {
        signup.getLoginInstead().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked");
                layout.show(container, "Login");
            }
        });
    }

    static void performLoginActions() {
        login.getSignInInsteadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked");
                layout.show(container, "Signup");
            }
        });
    }


    static void performAllPanelActions() {
        performSignupActions();
        performLoginActions();
    }
}