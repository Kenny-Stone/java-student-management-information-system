package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard {
    private JPanel dashboardPanel;
    private JTextField firstName;
    private JTextField middleName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JButton editDetailsButton;
    private JButton saveButton;
    private JTextField studentID;
//    private JLabel studentIDLabel;
//    private JLabel studentNameLabel;
//    private JLabel studentPicture;

    public Dashboard() {
        this.firstName.setEnabled(false);
        this.middleName.setEnabled(false);
        this.lastName.setEnabled(false);
        this.phoneNumber.setEnabled(false);
        this.saveButton.setEnabled(false);
    }

    public Dashboard(String id, String firstName, String middleName, String lastName, String phoneNumber) {
        disableTextFields();
        saveButton.setEnabled(false);
        this.studentID.setText(id);
        this.firstName.setText(firstName);
        this.middleName.setText(middleName);
        this.lastName.setText(lastName);
        this.phoneNumber.setText(phoneNumber);

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
                DBConnection conn = new DBConnection();

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
}