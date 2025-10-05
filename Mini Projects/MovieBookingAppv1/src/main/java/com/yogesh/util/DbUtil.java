package com.yogesh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/movie_booking_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "your_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL driver
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL Driver not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // You can add common query utility methods here if needed
}
