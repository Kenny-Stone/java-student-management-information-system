package com.studentinformationmanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JCheckBox studentCheckBox;
    private JCheckBox lecturerCheckBox;
    private JLabel title;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton signInInsteadButton;


    public Login() {
        getSignInInsteadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.show("Signup");
            }
        });
    }

    public JButton getSignInInsteadButton() {
        return signInInsteadButton;
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}
