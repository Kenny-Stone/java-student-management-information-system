package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private JTextField studentOrLecturerID;
    private JPasswordField password;
    private JCheckBox student;
    private JCheckBox lecturer;
    private JLabel title;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton signInInsteadButton;
    private JLabel schoolLogo;
    private JLabel errorLabel;


    public Login() {
        getSignInInsteadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.show("Signup");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleLogin();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public void handleLogin() throws RuntimeException {
        errorLabel.setVisible(true);
        // check if any of the fields are empty
        if (_checkIfEmpty(_getUserID()) || _checkIfEmpty(_getPassword())) {
            errorLabel.setText("Please fill out all fields");
            return;
        }

        // check if checkboxes have not been selected
        if (!isStudent() && !isLecturer()) {
            errorLabel.setText("Please select your status!");
            return;
        }

        try {
            // if user doesn't exist
            ResultSet result = _validateIfUserExists();
            if (result != null) {
                if (result.next()) {
                    if (isStudent()) {
                        Main.person = new Student(
                                result.getString("student_id"),
                                result.getString("first_name"),
                                result.getString("middle_name"),
                                result.getString("last_name"),
                                result.getString("phone_number"),
                                result.getString("email"),
                                result.getString("pass_word"),
                                result.getString("gender"));
                    }
                }
            }
//            ResultSet rs = _validateIfUserExists();
//            if (!_validateIfUserExists()) {
//                errorLabel.setText("User doesn't exist!");
//                return;
//            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Main.dashboard = new Dashboard();
        Main.addPanel(Main.dashboard.getDashboardPanel(), "Dashboard");
        Main.show("Dashboard");

    }

    public JButton getSignInInsteadButton() {
        return signInInsteadButton;
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }

    public boolean _checkIfEmpty(String data) {
        return data.isEmpty();
    }

    private String _getUserID() {
        return studentOrLecturerID.getText();
    }

    public String _getPassword() {
        char[] pass = password.getPassword();
        return new String(pass);
    }

    public boolean isStudent() {
        return student.isSelected();
    }

    public boolean isLecturer() {
        return lecturer.isSelected();
    }

    private ResultSet _validateIfUserExists() throws SQLException {
        try {
            DBConnection conn = new DBConnection();
            ResultSet result = conn.executeQuery("SELECT * from students where student_id = ? and pass_word = ?", _getUserID(), _getPassword());
            return result;
        } catch (SQLException ex) {
            errorLabel.setText("User doesn't exist!");
        }
        return null;
    }

    private int _getNumberOfResults(ResultSet rs) throws SQLException {
        int count = 0;
        while (rs.next()) {
            count++;
        }
        return count;
    }
}