package com.yogesh.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // HTML template for logout confirmation
    private static final String LOGOUT_HTML =
        "<!DOCTYPE html>\n" +
        "<html>\n" +
        "  <head>\n" +
        "    <title>Logout - To Do List Application</title>\n" +
        "    <style>\n" +
        "      body {\n" +
        "        font-family: Arial, sans-serif;\n" +
        "        background-color: #f4f4f4;\n" +
        "        display: flex;\n" +
        "        justify-content: center;\n" +
        "        align-items: center;\n" +
        "        height: 100vh;\n" +
        "        margin: 0;\n" +
        "      }\n" +
        "      .logout-container {\n" +
        "        background-color: white;\n" +
        "        padding: 30px;\n" +
        "        border-radius: 5px;\n" +
        "        box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
        "        width: 350px;\n" +
        "        text-align: center;\n" +
        "      }\n" +
        "      h1 {\n" +
        "        color: #333;\n" +
        "        margin-bottom: 20px;\n" +
        "      }\n" +
        "      .success-message {\n" +
        "        color: green;\n" +
        "        margin-bottom: 20px;\n" +
        "      }\n" +
        "      .login-btn {\n" +
        "        padding: 10px 20px;\n" +
        "        background-color: #4CAF50;\n" +
        "        color: white;\n" +
        "        border: none;\n" +
        "        border-radius: 4px;\n" +
        "        cursor: pointer;\n" +
        "        font-size: 16px;\n" +
        "      }\n" +
        "      .login-btn:hover {\n" +
        "        background-color: #45a049;\n" +
        "      }\n" +
        "    </style>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <div class=\"logout-container\">\n" +
        "      <h1>You have been logged out</h1>\n" +
        "      <div class=\"success-message\">You have successfully logged out of the system.</div>\n" +
        "      <button class=\"login-btn\" onclick=\"window.location.href='LoginServlet'\">Login Again</button>\n" +
        "    </div>\n" +
        "  </body>\n" +
        "</html>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Show logout confirmation page
        response.setContentType("text/html");
        response.getWriter().append(LOGOUT_HTML);
    }
}
