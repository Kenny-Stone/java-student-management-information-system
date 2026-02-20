package com.studentinformationmanagementsystem;

//import com.mysql.cj.protocol.Resultset;

import java.sql.ResultSet;
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
            int count =  connection.executeUpdate(
                    "INSERT INTO students(student_id,first_name,middle_name,last_name," +
                            "phone_number,email,pass_word,gender)" +
                            " VALUES(?,?,?,?,?,?,?,?);",
                    id, firstName, middleName, lastName, phoneNumber, email, password, gender);

            connection.close();
            return count;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int update(String userId, String firstName, String middleName, String lastName, String phoneNumber) {
        try {
            DBConnection connection = new DBConnection();
            int count = connection.executeUpdate(
                    "UPDATE students " +
                            "SET first_name = ?, " +
                            "middle_name = ?, " +
                            "last_name = ?, " +
                            "phone_number = ? " +
                            "WHERE student_id = " + "\"" + userId + "\"", firstName, middleName, lastName, phoneNumber
            );
            connection.close();
            this.setFirstName(firstName);
            this.setMiddleName(middleName);
            this.setLastName(lastName);
            this.setPhoneNumber(phoneNumber);
            return count;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResultSet getData() {
        try {
            DBConnection connection = new DBConnection();
            ResultSet rs =  connection.executeQuery("SELECT student_id, first_name,middle_name,last_name,phone_number," +
                    "email,pass_word,gender from students");
            connection.close();
            return rs;
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}