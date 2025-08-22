package com.yogesh.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String dobStr = request.getParameter("dob");
        String country = request.getParameter("country");
        String[] hobbiesArr = request.getParameterValues("hobbies");

        String hobbies = (hobbiesArr == null) ? "" : String.join(",", hobbiesArr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userdb", "root", "");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username,password,gender,dob,country,hobbies) VALUES(?,?,?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password); // ⚠️ in real apps hash this
            ps.setString(3, gender);
            ps.setDate(4, Date.valueOf(dobStr));
            ps.setString(5, country);
            ps.setString(6, hobbies);

            int i = ps.executeUpdate();
            if (i > 0) {
                response.sendRedirect("login.html");
            } else {
                out.println("Error during registration.");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
