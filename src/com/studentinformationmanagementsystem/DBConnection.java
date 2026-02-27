package com.studentinformationmanagementsystem;

import java.sql.*;

public class DBConnection {
    Connection con;

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sims", "root", ""
            );

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String statement) throws SQLException {
        try {
            Statement st = con.createStatement();
            return st.executeQuery(statement);
        } catch (SQLException throwables) {
            throw new SQLException(throwables);
        }
    }

    public int executeUpdate(String statement, String... values) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    ps.setString(i + 1, values[i]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throw new SQLException(throwables);
        }
    }

    public ResultSet executeQuery(String statement, String... values) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            for (int i = 0; i < values.length; i++) {
                ps.setString(i + 1, values[i]);
            }
            return ps.executeQuery();

        } catch (SQLException throwables) {
            throw new SQLException(throwables);
        }
    }


    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
