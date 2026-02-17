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
        if (_checkIfEmpty(getUserID()) || _checkIfEmpty(getPassword())) {
            errorLabel.setText("Please fill out all fields");
            return;
        }

        // check if checkboxes have not been selected
        if (!isStudent() && !isLecturer()) {
            errorLabel.setText("Please select your status!");
            return;
        }

        try {
            DBConnection conn = new DBConnection();

            ResultSet result = conn.executeQuery("SELECT * from students where student_id = ? and pass_word = ?", getUserID(), getPassword());
            int count = 0;
            while (result.next()) {
                count++;
            }
            if(count < 1) {
                errorLabel.setText("User does not exist in the database");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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

    private String getUserID() {
        return studentOrLecturerID.getText();
    }

    public String getPassword() {
        char[] pass = password.getPassword();
        return new String(pass);
    }

    public boolean isStudent() {
        return student.isSelected();
    }

    public boolean isLecturer() {
        return lecturer.isSelected();
    }

}