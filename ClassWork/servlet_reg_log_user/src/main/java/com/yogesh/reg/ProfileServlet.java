package com.yogesh.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("username");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userdb", "root", "");

            PreparedStatement ps = con.prepareStatement(
                "SELECT gender, dob, country, hobbies FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Welcome, " + username + "</h2>");
                out.println("Gender: " + rs.getString("gender") + "<br>");
                out.println("DOB: " + rs.getDate("dob") + "<br>");
                out.println("Country: " + rs.getString("country") + "<br>");
                out.println("Hobbies: " + rs.getString("hobbies") + "<br><br>");
                out.println("<a href='viewUsers'>View All Users</a>");
            } else {
                out.println("User not found.");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
