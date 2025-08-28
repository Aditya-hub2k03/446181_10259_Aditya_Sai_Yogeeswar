package com.yogesh.reg;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
//import jakarta.servlet.annotation.WebServlet;
//@WebServlet("/register")
public class ViewUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>All Users</h2>");
        out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Email</th><th>Gender</th><th>DOB</th><th>Hobbies</th><th>Country</th><th>Actions</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" +
                        rs.getString("name") + "</td><td>" +
                        rs.getString("email") + "</td><td>" +
                        rs.getString("gender") + "</td><td>" +
                        rs.getString("dob") + "</td><td>" +
                        rs.getString("hobbies") + "</td><td>" +
                        rs.getString("country") + "</td><td>" +
                        "<a href='editUser?id=" + rs.getInt("id") + "'>Edit</a> | " +
                        "<form action='deleteUser' method='post' style='display:inline;'><input type='hidden' name='id' value='" + rs.getInt("id") + "'><input type='submit' value='Delete'></form></td></tr>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='8'>Error: " + e.getMessage() + "</td></tr>");
        }
        out.println("</table>");
    }
}
