package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.util.Objects;

public class StudentDashboard extends NDashboard {
    private JPanel studentDashboardPanel;
    private JPanel header;
    private JLabel logo;
    private JLabel studentDetailsLink;
    private JPanel main;
    private JLabel welcomeMessage;
    private JPanel coursesPanel;

    public StudentDashboard() {
        _setLogo();
        _setWelcomeText("Welcome, " + Main.person.getFirstName() + " " + Main.person.getLastName());
        _setStudentDetailsLink(Main.person.getFirstName() + " " + Main.person.getLastName());
    }

    @Override
    public JPanel getDashboardPanel() {
        return studentDashboardPanel;
    }


    // helper methods
    private void _setWelcomeText(String text) {
        welcomeMessage.setText(text);
    }
    private void _setStudentDetailsLink(String text) {
        studentDetailsLink.setText(text);
    }

    private void _setLogo() {
        logo.setIcon(new ImageIcon(Objects.requireNonNull(StudentDashboard.class.getResource("resources/images/logo.png"))));
    }

//    private void addCourseToList(Course course)
    private void actionListeners(){}
}