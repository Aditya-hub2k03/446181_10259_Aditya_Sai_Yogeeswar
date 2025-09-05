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
        "    <title>Register - To Do List Application</title>\n" +"    <style>\n" +
        "      body{font-family:Arial,sans-serif;background:#f4f4f4;display:flex;justify-content:center;align-items:center;height:100vh;margin:0}\n" +
        "      .register-container{background:#fff;padding:30px;border-radius:5px;box-shadow:0 0 10px rgba(0,0,0,.1);width:350px}\n" +
        "      h1{text-align:center;margin-bottom:20px;color:#333}\n" +
        "      .form-group{margin-bottom:15px}\n" +
        "      label{display:block;margin-bottom:5px;font-weight:700}\n" +
        "      input[type=text],input[type=password],input[type=email]{width:100%;padding:10px;border:1px solid #ddd;border-radius:4px;box-sizing:border-box}\n" +
        "      .submit-btn{width:100%;padding:10px;background:#4CAF50;color:#fff;border:none;border-radius:4px;cursor:pointer;font-size:16px}\n" +
        "      .submit-btn:hover{background:#45a049}\n" +
        "      .error-message{color:red;text-align:center;margin-bottom:15px}\n" +
        "      .login-link{text-align:center;margin-top:15px}\n" +
        "      .login-link a{color:#4CAF50;text-decoration:none}\n" +
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
