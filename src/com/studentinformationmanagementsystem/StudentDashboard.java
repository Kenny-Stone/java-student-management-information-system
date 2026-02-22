package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class StudentDashboard extends NDashboard {
    private JPanel studentDashboardPanel;
    private JPanel header;
    private JLabel schoolLogo;
    private JLabel studentDetailsLink;
    private JPanel main;
    private JLabel welcomeMessage;
    private JPanel coursesPanel;
    private JPanel schoolInfoPanel;
    private JPanel contentPanel;

    /*
     *TODO: create a method that adds a list to coursePanel and maybe make it viewable
     * Add a view more button to the end
     * */
    // get school data and store in variable
    private List<Course> availableCourses = new ArrayList<>();
    private List<Course> studentCourses = new ArrayList<>();
    private int noOfStudents;
    private int noOfLecturers;
    private int noOfMales;
    private int noOfFemales;

    public StudentDashboard() {
        _setLogo();
        _setWelcomeText("Welcome, " + Main.person.getFirstName() + " " + Main.person.getMiddleName() + " " + Main.person.getLastName());
        _setStudentDetailsLink(Main.person.getFirstName() + " " + Main.person.getMiddleName() + " " + Main.person.getLastName());
        _getDataFromDatabase();
        _setSchoolInfoData();
    }

    public JPanel getDashboardPanel() {
        return studentDashboardPanel;
    }

    private JPanel _createCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setBackground(Color.WHITE);

        JLabel header = new JLabel(title, SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setForeground(Color.DARK_GRAY);

        JLabel number = new JLabel(value, SwingConstants.CENTER);
        number.setFont(new Font("Arial", Font.BOLD, 48));
        number.setForeground(new Color(33, 150, 243));

        card.add(header, BorderLayout.NORTH);
        card.add(number, BorderLayout.CENTER);

        return card;

    }

    private void _setSchoolInfoData() {
//        schoolInfoPanel.setLayout(new GridLayout(1, 2))
        contentPanel.setLayout(new GridLayout(1, 3, 15, 15));
        contentPanel.add(_createCard("Students", Integer.toString(noOfStudents)));
        contentPanel.add(_createCard("Males", Integer.toString(noOfMales)));
        contentPanel.add(_createCard("Females", Integer.toString(noOfFemales)));
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    // helper methods
    private void _getDataFromDatabase() {
        try {
            DBConnection connection = new DBConnection();
            noOfStudents = _getNoOfStudentsFromDatabase(connection);
            noOfMales = _getNoOfMalesFromDatabase(connection);
            noOfFemales = _getNoOfFemalesFromDatabase(connection);
            connection.close();
        } catch (SQLException ex) {
            System.out.println("METHOD: _getDataFromDatabase " + ex.getMessage());
        }
    }

    private int _getNoOfStudentsFromDatabase(DBConnection connection) throws SQLException {
        ResultSet result = connection.executeQuery("SELECT COUNT(*) from students");
        return result.next() ? result.getInt(1) : 0;
//        result
    }

    private int _getNoOfMalesFromDatabase(DBConnection connection) throws SQLException {
        ResultSet result = connection.executeQuery("SELECT COUNT(*) from students where gender = 'male'");
        return result.next() ? result.getInt(1) : 0;
    }

    private int _getNoOfFemalesFromDatabase(DBConnection connection) throws SQLException {
        ResultSet result = connection.executeQuery("SELECT COUNT(*) from students where gender = 'female'");
        return result.next() ? result.getInt(1) : 0;
    }


    private void _setWelcomeText(String text) {
        welcomeMessage.setText(text);
    }

    private void _setStudentDetailsLink(String text) {
        studentDetailsLink.setText(text);
    }

    private void _setLogo() {
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(StudentDashboard.class.getResource("/resources/logo1.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        schoolLogo.setIcon(new ImageIcon(scaledImage));
//        logo.setIcon();
    }

    //    private void addCourseToList(Course course)
    private void actionListeners() {
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}