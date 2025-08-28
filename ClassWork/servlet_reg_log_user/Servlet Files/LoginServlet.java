package com.yogesh.reg;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
//import jakarta.servlet.annotation.WebServlet;
//@WebServlet("/register")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "root");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("id", rs.getInt("id"));
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("gender", rs.getString("gender"));
                session.setAttribute("dob", rs.getString("dob"));
                session.setAttribute("hobbies", rs.getString("hobbies"));
                session.setAttribute("country", rs.getString("country"));

                response.sendRedirect("profile");
            } else {
                out.println("<h3>Invalid credentials. <a href='login.html'>Try again</a></h3>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
