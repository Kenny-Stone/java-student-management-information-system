package com.studentinformationmanagementsystem;

public class Student extends Person {
    String programme;

    public Student(String id, String firstName,
                   String middleName, String lastName,
                   String phoneNumber, String email,
                   String password, String gender) {
        super(id, firstName, middleName, lastName, phoneNumber, email, password, gender);
    }
}