package com.yogesh.reg;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
//import jakarta.servlet.annotation.WebServlet;
//@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String[] hobbiesArr = request.getParameterValues("hobbies");
        String hobbies = (hobbiesArr != null) ? String.join(",", hobbiesArr) : "";
        String country = request.getParameter("country");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "root");

            PreparedStatement ps = con.prepareStatement("INSERT INTO users (name,email,password,gender,dob,hobbies,country) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, dob);
            ps.setString(6, hobbies);
            ps.setString(7, country);

            ps.executeUpdate();
            con.close();

            out.println("<h3>Registration successful! <a href='login.html'>Login Here</a></h3>");
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
