package com.yogesh.db;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String USER = "root";  
    private static final String PASSWORD = "root"; 

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
