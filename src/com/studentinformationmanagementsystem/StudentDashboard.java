package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends NDashboard {
    private JPanel studentDashboardPanel;
    private JPanel header;
    private JLabel schoolLogo;
    private JLabel studentDetailsLink;
    private JPanel main;
    private JLabel welcomeMessage;
    private JPanel coursesPanel;
    private JPanel schoolInfoPanel;

    public StudentDashboard() {
        _setLogo();
        _setWelcomeText("Welcome, " + Main.person.getFirstName() + " " + Main.person.getLastName());
        _setStudentDetailsLink(Main.person.getFirstName() + " " + Main.person.getLastName());
    }

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
//        logo.setIcon(new ImageIcon(Objects.requireNonNull(StudentDashboard.class.getResource("resources/images/logo.png"))));

        ImageIcon originalIcon = new ImageIcon(StudentDashboard.class.getResource("/resources/logo1.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        schoolLogo.setIcon(new ImageIcon(scaledImage));
//        logo.setIcon();
    }

    //    private void addCourseToList(Course course)
    private void actionListeners() {
    }
}