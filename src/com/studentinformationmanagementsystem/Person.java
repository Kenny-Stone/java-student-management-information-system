package com.studentinformationmanagementsystem;

import java.sql.ResultSet;

public abstract class Person {
    protected String id;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String phoneNumber;
    protected String email;
    protected String password;
    protected String gender;

    public Person(String id, String firstName, String middleName, String lastName,
                  String phoneNumber, String email, String password, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    // SETTER METHODS
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    // GETTER METHODS
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public ResultSet getData() {
        return null;
    }

    public int store() {
        return 0;
    }

    public int update(String id, String firstName, String middleName, String lastName, String phoneNumber) {
        return 0;
    }
}
