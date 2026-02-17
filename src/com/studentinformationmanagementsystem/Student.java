package com.studentinformationmanagementsystem;

import com.mysql.cj.protocol.Resultset;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class Student extends Person {
    String programme;

    public Student(String id, String firstName,
                   String middleName, String lastName,
                   String phoneNumber, String email,
                   String password, String gender) {
        super(id, firstName, middleName, lastName, phoneNumber, email, password, gender);
    }

    @Override
    public int store() {
        try {

            DBConnection connection = new DBConnection();
            return connection.executeUpdate(
                    "INSERT INTO students(student_id,first_name,middle_name,last_name," +
                            "phone_number,email,pass_word,gender)" +
                             " VALUES(?,?,?,?,?,?,?,?);",
                    id,firstName,middleName,lastName,phoneNumber,email,password,gender);

        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Resultset getData() {
        try {
            DBConnection connection = new DBConnection();
            return (Resultset) connection.executeQuery("SELECT student_id, first_name,middle_name,last_name,phone_number," +
                    "email,pass_word,gender from students");
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}