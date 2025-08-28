package com.yogesh.reg;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
//import jakarta.servlet.annotation.WebServlet;
//@WebServlet("/register")
public class EditUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "root");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Edit User</h2>");
                out.println("<form action='editUser' method='post'>");
                out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'>");
                out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "'><br><br>");
                out.println("Email: <input type='email' name='email' value='" + rs.getString("email") + "'><br><br>");
                out.println("Password: <input type='password' name='password' value='" + rs.getString("password") + "'><br><br>");
                out.println("Gender: <input type='radio' name='gender' value='Male' " + ("Male".equals(rs.getString("gender")) ? "checked" : "") + ">Male ");
                out.println("<input type='radio' name='gender' value='Female' " + ("Female".equals(rs.getString("gender")) ? "checked" : "") + ">Female<br><br>");
                out.println("DOB: <input type='date' name='dob' value='" + rs.getString("dob") + "'><br><br>");
                out.println("Hobbies: <input type='text' name='hobbies' value='" + rs.getString("hobbies") + "'><br><br>");
                out.println("Country: <input type='text' name='country' value='" + rs.getString("country") + "'><br><br>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String hobbies = request.getParameter("hobbies");
        String country = request.getParameter("country");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "root");

            PreparedStatement ps = con.prepareStatement("UPDATE users SET name=?, email=?, password=?, gender=?, dob=?, hobbies=?, country=? WHERE id=?");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, dob);
            ps.setString(6, hobbies);
            ps.setString(7, country);
            ps.setInt(8, Integer.parseInt(id));

            ps.executeUpdate();
            con.close();

            response.sendRedirect("viewUsers");
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
