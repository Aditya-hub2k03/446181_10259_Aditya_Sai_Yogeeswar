package com.yogesh.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import com.yogesh.dao.*;

@WebServlet("/TodoServlet")
public class TodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDAO todoDAO;

    public TodoServlet() {
        super();
        this.todoDAO = new TodoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("changeStatus".equals(action)) {
            handleStatusChange(request, response);
            return;
        } else if ("deleteTodo".equals(action)) {
            handleDeleteTodo(request, response);
            return;
        }

        // Forward to JSP page
        request.getRequestDispatcher("/todo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            UserItem user = (UserItem) session.getAttribute("user");

            TodoItem newTodo = new TodoItem();
            newTodo.setTodoTitle(request.getParameter("todoTitle"));
            newTodo.setTodoDesc(request.getParameter("todoDescription"));
            newTodo.setTargetDatetime(LocalDateTime.parse(request.getParameter("targetDateTime").replace(" ", "T")));
            newTodo.setTodoStatusCode("P");
            newTodo.setUserId(user.getUserId());
            newTodo.setCreatedBy(user.getUsername());

            todoDAO.addTodo(newTodo);

            // Store success message in session
            request.getSession().setAttribute("successMessage", "Task submitted successfully!");

            // Redirect to GET request to prevent form resubmission
            response.sendRedirect(request.getContextPath() + "/todo.jsp");
        } catch (Exception e) {
            // Store error message in session
            request.getSession().setAttribute("errorMessage", "Error: " + e.getMessage());
            // Redirect to GET request
            response.sendRedirect(request.getContextPath() + "/todo.jsp");
            e.printStackTrace();
        }
    }

    private void handleStatusChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.getWriter().write("You must be logged in to change status");
            return;
        }

        try {
            int todoId = Integer.parseInt(request.getParameter("todoId"));
            String newStatus = request.getParameter("newStatus");
            UserItem user = (UserItem) session.getAttribute("user");

            TodoItem todo = todoDAO.getTodoById(todoId, user.getUserId());
            if (todo != null) {
                String statusCode = "P";
                if ("In Progress".equals(newStatus)) statusCode = "I";
                else if ("Completed".equals(newStatus)) statusCode = "C";
                todo.setTodoStatusCode(statusCode);
                todo.setModifiedBy(user.getUsername());
                todoDAO.updateTodo(todo);
                response.getWriter().write("Status updated to " + newStatus + " successfully!");
            } else {
                response.getWriter().write("Todo item not found or you don't have permission!");
            }
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleDeleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.getWriter().write("You must be logged in to delete tasks");
            return;
        }

        try {
            int todoId = Integer.parseInt(request.getParameter("todoId"));
            UserItem user = (UserItem) session.getAttribute("user");

            todoDAO.deleteTodo(todoId, user.getUserId());
            response.getWriter().write("Task deleted successfully!");
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
