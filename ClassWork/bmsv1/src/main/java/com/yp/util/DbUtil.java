package com.yp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/bookmyshow";
    private static final String USER = "root";
    private static final String PASSWORD = "root";  // replace with your actual password

    public static Connection getConnection() throws Exception {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Return the database connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
