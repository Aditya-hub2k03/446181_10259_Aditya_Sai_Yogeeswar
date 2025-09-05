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
        "    <title>Login - To Do List Application</title>\n" +"    <style>\n" +
        "      body{font-family:Arial,sans-serif;background:#f4f4f4;display:flex;justify-content:center;align-items:center;height:100vh;margin:0}\n" +
        "      .login-container{background:#fff;padding:30px;border-radius:5px;box-shadow:0 0 10px rgba(0,0,0,.1);width:350px}\n" +
        "      h1{text-align:center;margin-bottom:20px;color:#333}\n" +
        "      .form-group{margin-bottom:15px}\n" +
        "      label{display:block;margin-bottom:5px;font-weight:700}\n" +
        "      input[type=text],input[type=password]{width:100%;padding:10px;border:1px solid #ddd;border-radius:4px;box-sizing:border-box}\n" +
        "      .submit-btn{width:100%;padding:10px;background:#4CAF50;color:#fff;border:none;border-radius:4px;cursor:pointer;font-size:16px}\n" +
        "      .submit-btn:hover{background:#45a049}\n" +
        "      .error-message{color:red;text-align:center;margin-bottom:15px}\n" +
        "      .success-message{color:green;text-align:center;margin-bottom:15px}\n" +
        "      .register-link{text-align:center;margin-top:15px}\n" +
        "      .register-link a{color:#4CAF50;text-decoration:none}\n" +
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

                response.sendRedirect(request.getContextPath() + "/TodoServlet");
            } else {
                response.sendRedirect(request.getContextPath() + "/LoginServlet?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/LoginServlet?error=1");
        }
    }
}
