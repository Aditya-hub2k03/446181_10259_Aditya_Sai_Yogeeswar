package com.yogesh.reg;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//@WebServlet("/register")
import jakarta.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy session
        }
        response.sendRedirect("login.html"); // Redirect back to login
    }
} 
