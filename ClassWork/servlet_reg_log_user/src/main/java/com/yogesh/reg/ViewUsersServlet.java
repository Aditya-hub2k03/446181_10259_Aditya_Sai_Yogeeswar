package com.yogesh.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userdb", "root", "");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            out.println("<h2>All Users</h2>");
            out.println("<table border='1' cellpadding='6' cellspacing='0'>");
            out.println("<tr><th>ID</th><th>Username</th><th>Gender</th><th>DOB</th><th>Country</th><th>Hobbies</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("username") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("<td>" + rs.getDate("dob") + "</td>");
                out.println("<td>" + rs.getString("country") + "</td>");
                out.println("<td>" + rs.getString("hobbies") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
