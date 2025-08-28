package com.yogesh.reg;

import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import java.io.*;
//import jakarta.servlet.annotation.WebServlet;
//@WebServlet("/register")
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("name") == null) {
            response.sendRedirect("login.html");
            return;
        }

        out.println("<h2>User Profile</h2>");
        out.println("Name: " + session.getAttribute("name") + "<br>");
        out.println("Email: " + session.getAttribute("email") + "<br>");
        out.println("Gender: " + session.getAttribute("gender") + "<br>");
        out.println("DOB: " + session.getAttribute("dob") + "<br>");
        out.println("Hobbies: " + session.getAttribute("hobbies") + "<br>");
        out.println("Country: " + session.getAttribute("country") + "<br><br>");
        out.println("<a href='viewUsers'>View All Users</a><br>");
        out.println("<a href='logout'>Logout</a>");
    }
}
