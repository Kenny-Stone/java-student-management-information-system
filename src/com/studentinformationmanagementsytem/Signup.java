package com.studentinformationmanagementsytem;

import javax.swing.*;

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

    public JButton getLoginInstead() {return loginInstead;}

    public JPanel getSignupPanel() {return signupPanel;}
}
