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

    public RegisterServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to JSP page
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Check if username already exists
            UserItem existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                response.sendRedirect(request.getContextPath() + "/register.jsp?error=username_exists");
                return;
            }

            UserItem user = new UserItem();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password); 

            boolean success = userDAO.registerUser(user);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/login.jsp?registered=1");
            } else {
                response.sendRedirect(request.getContextPath() + "/register.jsp?error=registration_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=registration_failed");
        }
    }
}
