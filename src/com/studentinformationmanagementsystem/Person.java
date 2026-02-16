package com.studentinformationmanagementsystem;

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
                  String phoneNumber,String email, String password, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
    public int store() {
        return 0;
    }
    public void update() {}
}
