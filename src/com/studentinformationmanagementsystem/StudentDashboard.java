package com.studentinformationmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private JScrollPane coursesScrollPanel;
    private JTable courseTable;

    /*
     *TODO: create a method that adds a list to coursePanel and maybe make it viewable
     * Add a view more button to the end
     * */
    // get school data and store in variable
    private List<Course> availableCourses = new ArrayList<>();
    private List<Course> enrolledCourses = new ArrayList<>();
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

    private void _addCoursesToCoursesPanel() {
        String[] columns = {
                "Course ID",
                "Course Name",
                "Credit Hours",
                "Lecturer",
                "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table read-only
            }
        };

        JTable courseTable = new JTable(model);
        courseTable.setRowHeight(28);
        courseTable.setAutoCreateRowSorter(true); // sorting by column

        availableCourses.forEach(course -> {
            model.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCourseCredit(),
                    course.getLecturerFirstName() + " " + course.getLecturerMiddleName() + " " + course.getLecturerLastName(),
                    "ENROLLED"
            });
        });
    }

    private void _addStudentToEnrolledCourses(Course course) {
        enrolledCourses.add(course);
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
            //TODO: reduce number of requests by getting count of females, males and students in one request
            noOfStudents = _getNoOfStudentsFromDatabase(connection);
            noOfMales = _getNoOfMalesFromDatabase(connection);
            noOfFemales = _getNoOfFemalesFromDatabase(connection);
            _getAvailableCoursesFromDatabase(connection);   // stores data in availableCourse list
            _getEnrolledCoursesFromDatabase(connection);
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
        ImageIcon originalIcon = new ImageIcon(
                Objects.requireNonNull(
                        StudentDashboard.class.getResource("/resources/logo1.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        schoolLogo.setIcon(new ImageIcon(scaledImage));
//        logo.setIcon();
    }


    private void _getEnrolledCoursesFromDatabase(DBConnection connection) throws SQLException {
        ResultSet result = connection.executeQuery("SELECT" +
                "courses.course_id,courses.course_name,courses.credit_hours," +
                "lecturer.lecturer_id,lecturer.first_name,lecturer.middle_name," +
                "lecturer.last_name " +
                "FROM enrollments " +
                "JOIN courses " +
                "ON enrollments.course_id = courses.course_id " +
                "JOIN lecturer " +
                "ON courses.lecturer_id = lecturer.lecturer_id" +
                "WHERE enrollment.student_id = :student_id");

        while (result.next()) {
            _addStudentToEnrolledCourses(
                    new Course(
                            result.getString("course_id"),
                            result.getString("course_name"),
                            result.getString("credit_hours"),
                            result.getString("first_name"),
                            result.getString("middle_name"),
                            result.getString("last_name"))
            );
        }
    }

    private void _getAvailableCoursesFromDatabase(DBConnection connection) throws SQLException {
        // gets available courses from database and store them in a parameter in class
        ResultSet result = connection.executeQuery("SELECT courses.course_id,courses.course_name," +
                "courses.credit_hours," +
                " lecturer.first_name,lecturer.middle_name,lecturer.last_name from courses left join lecturer on" +
                " courses.lecturer_id = lecturer.lecturer_id");
        while (result.next()) {
            _addCourseToAvailableCourses(
                    new Course(
                            result.getString("course_id"),
                            result.getString("course_name"),
                            result.getString("credit_hours"),
                            result.getString("first_name"),
                            result.getString("middle_name"),
                            result.getString("last_name")));
        }
    }

    private void _addCourseToAvailableCourses(Course course) {
        availableCourses.add(course);
    }

    private void actionListeners() {
    }
}