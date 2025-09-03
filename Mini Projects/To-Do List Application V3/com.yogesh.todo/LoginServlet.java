package com.yogesh.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.yogesh.dao.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    // HTML template for login page
    private static final String LOGIN_HTML =
        "<!DOCTYPE html>\n" +
        "<html>\n" +
        "  <head>\n" +
        "    <title>Login - To Do List Application</title>\n" +
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
        "      .login-container {\n" +
        "        background-color: white;\n" +
        "        padding: 30px;\n" +
        "        border-radius: 5px;\n" +
        "        box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
        "        width: 350px;\n" +
        "      }\n" +
        "      h1 {\n" +
        "        text-align: center;\n" +
        "        margin-bottom: 20px;\n" +
        "        color: #333;\n" +
        "      }\n" +
        "      .form-group {\n" +
        "        margin-bottom: 15px;\n" +
        "      }\n" +
        "      label {\n" +
        "        display: block;\n" +
        "        margin-bottom: 5px;\n" +
        "        font-weight: bold;\n" +
        "      }\n" +
        "      input[type=\"text\"],\n" +
        "      input[type=\"password\"] {\n" +
        "        width: 100%;\n" +
        "        padding: 10px;\n" +
        "        border: 1px solid #ddd;\n" +
        "        border-radius: 4px;\n" +
        "        box-sizing: border-box;\n" +
        "      }\n" +
        "      .submit-btn {\n" +
        "        width: 100%;\n" +
        "        padding: 10px;\n" +
        "        background-color: #4CAF50;\n" +
        "        color: white;\n" +
        "        border: none;\n" +
        "        border-radius: 4px;\n" +
        "        cursor: pointer;\n" +
        "        font-size: 16px;\n" +
        "      }\n" +
        "      .submit-btn:hover {\n" +
        "        background-color: #45a049;\n" +
        "      }\n" +
        "      .error-message {\n" +
        "        color: red;\n" +
        "        text-align: center;\n" +
        "        margin-bottom: 15px;\n" +
        "      }\n" +
        "      .success-message {\n" +
        "        color: green;\n" +
        "        text-align: center;\n" +
        "        margin-bottom: 15px;\n" +
        "      }\n" +
        "      .register-link {\n" +
        "        text-align: center;\n" +
        "        margin-top: 15px;\n" +
        "      }\n" +
        "      .register-link a {\n" +
        "        color: #4CAF50;\n" +
        "        text-decoration: none;\n" +
        "      }\n" +
        "    </style>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <div class=\"login-container\">\n" +
        "      <h1>Login</h1>\n" +
        "      %ERROR_MESSAGE%\n" +
        "      %SUCCESS_MESSAGE%\n" +
        "      <form action=\"LoginServlet\" method=\"post\">\n" +
        "        <div class=\"form-group\">\n" +
        "          <label for=\"username\">Username:</label>\n" +
        "          <input type=\"text\" id=\"username\" name=\"username\" required>\n" +
        "        </div>\n" +
        "        <div class=\"form-group\">\n" +
        "          <label for=\"password\">Password:</label>\n" +
        "          <input type=\"password\" id=\"password\" name=\"password\" required>\n" +
        "        </div>\n" +
        "        <button type=\"submit\" class=\"submit-btn\">Login</button>\n" +
        "      </form>\n" +
        "      <div class=\"register-link\">\n" +
        "        Don't have an account? <a href=\"RegisterServlet\">Register here</a>\n" +
        "      </div>\n" +
        "    </div>\n" +
        "  </body>\n" +
        "</html>";

    public LoginServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Check for error parameter
        String errorMessage = "";
        if (request.getParameter("error") != null) {
            errorMessage = "<div class=\"error-message\">Invalid username or password</div>";
        }

        // Check for success parameter
        String successMessage = "";
        if (request.getParameter("registered") != null) {
            successMessage = "<div class=\"success-message\">Registration successful! Please login.</div>";
        }

        // Generate HTML with appropriate messages
        String html = LOGIN_HTML
            .replace("%ERROR_MESSAGE%", errorMessage)
            .replace("%SUCCESS_MESSAGE%", successMessage);

        response.getWriter().append(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserItem user = userDAO.authenticate(username, password);

            if (user != null) {
                // Create session and store user information
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30*60); // 30 minutes

                // Redirect to TodoServlet
                response.sendRedirect(request.getContextPath() + "/TodoServlet");
            } else {
                // Authentication failed - redirect with error parameter
                response.sendRedirect(request.getContextPath() + "/LoginServlet?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/LoginServlet?error=1");
        }
    }
}
