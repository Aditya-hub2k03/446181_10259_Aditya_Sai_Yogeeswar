package com.yogesh.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.yogesh.dao.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    // HTML template for registration page
    private static final String REGISTER_HTML =
        "<!DOCTYPE html>\n" +
        "<html>\n" +
        "  <head>\n" +
        "    <title>Register - To Do List Application</title>\n" +
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
        "      .register-container {\n" +
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
        "      input[type=\"password\"],\n" +
        "      input[type=\"email\"] {\n" +
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
        "      .login-link {\n" +
        "        text-align: center;\n" +
        "        margin-top: 15px;\n" +
        "      }\n" +
        "      .login-link a {\n" +
        "        color: #4CAF50;\n" +
        "        text-decoration: none;\n" +
        "      }\n" +
        "    </style>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <div class=\"register-container\">\n" +
        "      <h1>Register</h1>\n" +
        "      %ERROR_MESSAGE%\n" +
        "      <form action=\"RegisterServlet\" method=\"post\">\n" +
        "        <div class=\"form-group\">\n" +
        "          <label for=\"username\">Username:</label>\n" +
        "          <input type=\"text\" id=\"username\" name=\"username\" required>\n" +
        "        </div>\n" +
        "        <div class=\"form-group\">\n" +
        "          <label for=\"email\">Email:</label>\n" +
        "          <input type=\"email\" id=\"email\" name=\"email\" required>\n" +
        "        </div>\n" +
        "        <div class=\"form-group\">\n" +
        "          <label for=\"password\">Password:</label>\n" +
        "          <input type=\"password\" id=\"password\" name=\"password\" required>\n" +
        "        </div>\n" +
        "        <button type=\"submit\" class=\"submit-btn\">Register</button>\n" +
        "      </form>\n" +
        "      <div class=\"login-link\">\n" +
        "        Already have an account? <a href=\"LoginServlet\">Login here</a>\n" +
        "      </div>\n" +
        "    </div>\n" +
        "  </body>\n" +
        "</html>";

    public RegisterServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Check for error parameter
        String errorMessage = "";
        String errorType = request.getParameter("error");

        if (errorType != null) {
            if ("username_exists".equals(errorType)) {
                errorMessage = "<div class=\"error-message\">Username already exists. Please choose another.</div>";
            } else {
                errorMessage = "<div class=\"error-message\">Registration failed. Please try again.</div>";
            }
        }

        // Generate HTML with appropriate error message
        String html = REGISTER_HTML.replace("%ERROR_MESSAGE%", errorMessage);
        response.getWriter().append(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Check if username already exists
            UserItem existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                response.sendRedirect(request.getContextPath() + "/RegisterServlet?error=username_exists");
                return;
            }

            UserItem user = new UserItem();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password); 

            boolean success = userDAO.registerUser(user);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/LoginServlet?registered=1");
            } else {
                response.sendRedirect(request.getContextPath() + "/RegisterServlet?error=registration_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/RegisterServlet?error=registration_failed");
        }
    }
}
