package com.studentinformationmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

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
    private JButton registerCoursesButton;
    private JLabel errorLabel;

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
    JDialog settingsDialog = new JDialog();
    // below are buttons that appear in dialog when user clicks on student's name
    private Map<String, JButton> dialogButton = new HashMap<>();

    public StudentDashboard() {
        _init();
    }

    private void _init() {
        _setLogo();
        _setWelcomeText("Welcome, " + Main.person.getFirstName() + " " + Main.person.getMiddleName() + " "
                + Main.person.getLastName());
        _setStudentDetailsLink(Main.person.getFirstName() + " " + Main.person.getMiddleName() + " "
                + Main.person.getLastName());
        _getDataFromDatabase();
        _setSchoolInfoData();
        _addCoursesToCoursesPanel();
        _setSettingsDialogData();
        _loadDialogButtons();
//        _addButtonToDialog(settingsDialog,logOutButton);
        _actionListeners();
    }

    private void _addToDialogButtons(String name, JButton button) {
        dialogButton.put(name, button);

    }

    private void _loadDialogButtons() {
        _addToDialogButtons("logout", new JButton("Log Out"));


        for (Map.Entry<String, JButton> entry : dialogButton.entrySet()) {
            _getSettingsPanel().add(entry.getValue());
        }
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
                return true; // make table read-only
            }

        };


        courseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus,
                    int row, int column
            ) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );
                String status = table.getValueAt(row, 4).toString();
                if (!isSelected) {
                    if ("ENROLLED".equals(status)) {
                        c.setBackground(new Color(220, 255, 220));
                    } else {
                        c.setBackground(new Color(255, 230, 230));
                    }
                }
                return c;
            }
        });

        courseTable.setRowHeight(30);
        courseTable.setAutoCreateRowSorter(true); // sorting by column

        enrolledCourses.forEach(course -> {
            model.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCourseCredit(),
                    course.getLecturerFirstName() + " " + course.getLecturerMiddleName() + " "
                            + course.getLecturerLastName(),
                    "ENROLLED"
            });

        });


        availableCourses.forEach(course -> {
            model.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCourseCredit(),
                    course.getLecturerFirstName() + " " + course.getLecturerMiddleName() + " "
                            + course.getLecturerLastName(),
                    "ENROLL"
            });
        });

        courseTable.setModel(model);
        courseTable.getColumn("Status").setCellRenderer(new ButtonRenderer());
        courseTable.getColumn("Status").setCellEditor(new ButtonEditor(model));
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
        ResultSet result = connection.executeQuery("SELECT " +
                "courses.course_id,courses.course_name,courses.credit_hours," +
                "lecturer.lecturer_id,lecturer.first_name,lecturer.middle_name," +
                "lecturer.last_name " +
                "FROM enrollments " +
                "JOIN courses " +
                "ON enrollments.course_id = courses.course_id " +
                "JOIN lecturer " +
                "ON courses.lecturer_id = lecturer.lecturer_id " +
                "WHERE enrollments.student_id = ?", Main.person.getId());

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

    private void _actionListeners() {
        studentDetailsLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                _getSettingsPanel().setVisible(!_getSettingsPanel().isVisible());
                System.out.println("Clicked");

            }
        });
        dialogButton.get("logout").addActionListener(e -> {
            System.out.println("Going back to login page");
            Main.removePanel(this.studentDashboardPanel);
        });

        registerCoursesButton.addActionListener(e -> {
            try {
                _setEnrolledCourses();
                errorLabel.setForeground(Color.GREEN);
                errorLabel.setText("Courses registered successfully");
            } catch (SQLException ex) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Error occurred while registering courses");
            }
        });
    }

    private void _setEnrolledCourses() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) courseTable.getModel();

        for (int row = 0; row < model.getRowCount(); row++) {
            String status = model.getValueAt(row, 4).toString();

            if ("ENROLLED".equals(status)) {

                String courseId = model.getValueAt(row, 0).toString();
//                String courseName = model.getValueAt(row, 1).toString();
//                String creditHours = model.getValueAt(row, 2).toString();

                // student isn't registered
                if (!_isStudentAlreadyRegisteredInCourse(courseId)) {

                    DBConnection connection = new DBConnection();
                    connection.executeUpdate(
                            "INSERT INTO enrollments(semester,academic_year,student_id,course_id) VALUES(" +
                                    "?,?,?,?)",
                            "1", "2026", Main.person.getId(), courseId);

                    connection.close();
                }
            }
        }
    }


    private boolean _isStudentAlreadyRegisteredInCourse(String courseId) throws SQLException {
        boolean exists;
        //TODO:make a sql request to get data if true do not save
        DBConnection connection = new DBConnection();
        ResultSet result = connection.executeQuery("SELECT 1 FROM enrollments WHERE student_id = ? " +
                "AND course_id = ?", Main.person.getId(), courseId);
        exists = result.next();
        result.close();
        connection.close();
        return exists;
    }

    private void _setSettingsDialogData() {
        settingsDialog.setSize(200, 150);
        settingsDialog.setLayout(new GridLayout(3, 1));
    }

    public JDialog _getSettingsPanel() {
        settingsDialog.pack();
        settingsDialog.setLocationRelativeTo(getDashboardPanel());
        return settingsDialog;
    }


    // Custom cell editor for button in Status column
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private DefaultTableModel model;
        private int row;

        public ButtonEditor(DefaultTableModel model) {
            super(new JCheckBox());
            this.model = model;
            button = new JButton();
            button.addActionListener(e -> {
                String status = (String) model.getValueAt(row, 4);
                if ("ENROLL".equals(status)) {
                    model.setValueAt("ENROLLED", row, 4);
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int col) {
            this.row = row;
            String status = (String) value;
            button.setText(status);
            button.setEnabled("ENROLL".equals(status));
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return model.getValueAt(row, 4);
        }
    }

    // Custom cell renderer for button in Status column
    private static class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            String status = (String) value;
            setText(status);
            setEnabled("ENROLL".equals(status));

            if ("ENROLLED".equals(status)) {
                setBackground(new Color(76, 175, 80));
                setForeground(Color.WHITE);
            } else {
                setBackground(new Color(33, 150, 243));
                setForeground(Color.WHITE);
            }

            return this;
        }
    }
}