package com.studentinformationmanagementsytem;

import javax.swing.*;

public class Login {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JCheckBox studentCheckBox;
    private JCheckBox lecturerCheckBox;
    private JLabel title;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton signInInsteadButton;

    public JButton getSignInInsteadButton() {
        return signInInsteadButton;
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}
