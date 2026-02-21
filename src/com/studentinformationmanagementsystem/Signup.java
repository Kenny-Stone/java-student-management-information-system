package com.studentinformationmanagementsystem;

import com.sun.jdi.InvalidTypeException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Signup {
    private JTextField studentOrLecturerID;
    private JTextField firstName;
    private JTextField middleName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JTextField email;
    private JPasswordField password;
    private JPasswordField confirmPassword;
    private JCheckBox male;
    private JCheckBox female;
    private JCheckBox student;
    private JCheckBox lecturer;
    private JButton createAccountButton;
    private JLabel title;
    private JPanel signupPanel;
    private JButton loginInstead;
    private JLabel errorLabel;
    private JLabel schoolLogo;

    public Signup() {
        errorLabel.setVisible(false);

        //action listeners
        getLoginInstead().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.show("Login");
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked");
                try {
                    handleSignup();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public JButton getLoginInstead() {
        return loginInstead;
    }

    public JPanel getSignupPanel() {
        return signupPanel;
    }


    // returns user data retrieved from forms
    public String getUserID() {
        return studentOrLecturerID.getText();
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getMiddleName() {
        return middleName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public String getPhoneNumber() {
        return phoneNumber.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getGender() {
        return isMale() ? "male" : "female";
    }

    public String getPassword() {
        char[] pass = password.getPassword();
        return new String(pass);


    }

    public String getConfirmPassword() {
        char[] confirmPass = confirmPassword.getPassword();
        return new String(confirmPass);
    }

    public boolean isMale() {
        return male.isSelected();
    }

    public boolean isFemale() {
        return female.isSelected();
    }

    public boolean isStudent() {
        return student.isSelected();
    }

    public boolean isLecturer() {
        return lecturer.isSelected();
    }

    /// /////////////////


    public void handleSignup() throws RuntimeException {
        errorLabel.setVisible(true);
        // checks if data is empty
        if (_checkIfAllFieldEmpty()) {
            errorLabel.setText("User must fill all fields");
            return;
        }

        try {
            _checkIfPhoneNumberValid();
        } catch (InvalidTypeException ex) {
            errorLabel.setText(ex.getMessage());
            return;
        }

        // check if password and confirm password is not the same
        if (!getPassword().equals(getConfirmPassword())) {
            errorLabel.setText("Passwords do not match");
            return;
        }


        // checks if checkbox have not been selected

        if (!isMale() && !isFemale()) {
            errorLabel.setText("Please select your gender");
            return;
        }

        if (!isStudent() && !isLecturer()) {
            errorLabel.setText("Please select your status");
            return;
        }


        errorLabel.setVisible(false);
        System.out.println("Account Created successfully");
        if (isStudent()) {
            Main.person = new Student(getUserID(), getFirstName(), getMiddleName(),
                    getLastName(), getPhoneNumber(), getEmail(), getPassword(),
                    getGender());
            Main.nDashboard = new StudentDashboard();
        }
        // if user is a lecturer
        else if (isLecturer()) {
            Main.person = new Lecturer(getUserID(), getFirstName(), getMiddleName(),
                    getLastName(), getPhoneNumber(), getEmail(), getPassword(), getGender());

            // store lecturer details
        } else {
            throw new RuntimeException("Status is invalid...");
        }

        try {
            if (Main.person.store() < 1) {
                errorLabel.setText("Something went wrong. Details could not be saved.");
                return;
            }
        } catch (RuntimeException ex) {
            errorLabel.setText(ex.getMessage());
        }

        Main.addPanel(Main.nDashboard.getDashboardPanel(), "Dashboard");
        Main.show("Dashboard");
    }


    // handler methods
    private boolean _checkIfEmpty(String data) {
        return data.isEmpty();
    }

    private void _checkIfPhoneNumberValid() throws InvalidTypeException {
        // check if mobile number is valid
        String telNumber = phoneNumber.getText();
        for (int i = 0; i < telNumber.length(); i++) {
            char digit = telNumber.charAt(i);
            if (Character.isLetter(digit)) {
                throw new InvalidTypeException("Phone Number field takes in only digits");
            }
        }
    }

    private boolean _checkIfAllFieldEmpty() {
        return _checkIfEmpty(getUserID()) || _checkIfEmpty(getFirstName())
                || _checkIfEmpty(getMiddleName()) || _checkIfEmpty(getLastName())
                || _checkIfEmpty(getPhoneNumber()) || _checkIfEmpty(getEmail())
                || _checkIfEmpty(getPassword()) || _checkIfEmpty(getConfirmPassword());
    }

    public void setErrorLabelValue(String value) {
        errorLabel.setText(value);
    }

}