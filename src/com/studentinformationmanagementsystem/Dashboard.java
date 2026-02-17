package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard {
    private JPanel dashboardPanel;
    private JTextField firstName;
    private JTextField middleName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JButton editDetailsButton;
    private JButton saveButton;
    private JTextField studentID;

    private String password;
//    private JLabel studentIDLabel;
//    private JLabel studentNameLabel;
//    private JLabel studentPicture;

    public Dashboard(String id, String password) {
        this.studentID.setText(id);
        this.password = password;
        this.studentID.setEnabled(false);
        this.firstName.setEnabled(false);
        this.middleName.setEnabled(false);
        this.lastName.setEnabled(false);
        this.phoneNumber.setEnabled(false);
        this.saveButton.setEnabled(false);

        getDataFromDatabase();


        // add action listener for edit button
        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableTextFields();
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
    }

    public Dashboard(String id, String firstName, String middleName, String lastName, String phoneNumber) {
        disableTextFields();
        saveButton.setEnabled(false);
//        this.studentID.setText(id);
//        this.firstName.setText(firstName);
//        this.middleName.setText(middleName);
//        this.lastName.setText(lastName);
//        this.phoneNumber.setText(phoneNumber);

        getDataFromDatabase();

        // add action listener for edit button
        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableTextFields();
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
    }


//    public void setPicture(String picture) {
//        this.studentPicture.setText(picture);
//    }
//
//    public void setID(String id) {
//        studentIDLabel.setText(id);
//    }

    public JPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public void enableTextFields() {
        this.studentID.setEnabled(true);
        this.firstName.setEnabled(true);
        this.middleName.setEnabled(true);
        this.lastName.setEnabled(true);
        this.phoneNumber.setEnabled(true);

        // enables all text fields in dashboard panel
    }

    public void disableTextFields() {
        this.studentID.setEnabled(false);
        this.firstName.setEnabled(false);
        this.middleName.setEnabled(false);
        this.lastName.setEnabled(false);
        this.phoneNumber.setEnabled(false);

    }


    public void setID(String id) {
        this.studentID.setText(id);
    }

    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    public void setMiddleName(String middleName) {
        this.middleName.setText(middleName);
    }

    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
    }

    public void getDataFromDatabase() {
        try {

            DBConnection conn = new DBConnection();
            ResultSet result = conn.executeQuery("SELECT student_id,first_name," +
                            "middle_name,last_name,phone_number from students where student_id = ? and pass_word = ?",
                    studentID.getText(), password);

            if (result.next()) {
                setFirstName(result.getString("first_name"));
                setMiddleName(result.getString("middle_name"));
                setLastName(result.getString("last_name"));
                setPhoneNumber(result.getString("phone_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateData() {
        try {
            if(Main.person.update(studentID.getText(), firstName.getText(), middleName.getText(), lastName.getText(), phoneNumber.getText()) < 1) {
                System.out.println("Couldn't update data!");
            }
        } catch (Exception ex) {
            //TODO: create an error label field to display error message
            // instead of printing error message
            System.out.println(ex.getMessage());
        }
    }
}