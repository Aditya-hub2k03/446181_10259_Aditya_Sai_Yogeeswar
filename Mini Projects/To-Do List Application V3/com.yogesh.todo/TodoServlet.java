package com.yogesh.todo;

import com.yogesh.dao.TodoDAO;
import com.yogesh.dao.TodoItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {
    private TodoDAO todoDAO = new TodoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Todo List</title></head><body>");
            out.println("<h2>Todo List</h2>");

            // --- Add Todo Form ---
            out.println("<form method='post' action='todos'>");
            out.println("Title: <input type='text' name='title'><br><br>");
            out.println("Description: <input type='text' name='desc'><br><br>");
            out.println("Target DateTime (yyyy-MM-ddTHH:mm): <input type='datetime-local' name='target'><br><br>");
            out.println("Created By: <input type='text' name='createdBy'><br><br>");
            out.println("<input type='submit' name='action' value='Add Todo'>");
            out.println("</form><br>");

            // --- Todo Table ---
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Target</th>");
            out.println("<th>Status</th>");
            out.println("<th>Created By</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");

            List<TodoItem> todos = todoDAO.getAllTodos();
            for (TodoItem todo : todos) {
                out.println("<tr>");
                out.println("<td>" + todo.getTodoId() + "</td>");
                out.println("<td>" + todo.getTodoTitle() + "</td>");
                out.println("<td>" + todo.getTodoDesc() + "</td>");
                out.println("<td>" + todo.getTargetDatetime() + "</td>");
                out.println("<td>" + todo.getTodoStatusCode() + "</td>");
                out.println("<td>" + todo.getCreatedBy() + "</td>");
                out.println("<td>"
                        + "<form style='display:inline;' method='post' action='todos'>"
                        + "<input type='hidden' name='id' value='" + todo.getTodoId() + "'/>"
                        + "<input type='submit' name='action' value='Delete'/>"
                        + "</form>"
                        + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("Add Todo".equals(action)) {
                TodoItem todo = new TodoItem();
                todo.setTodoTitle(req.getParameter("title"));
                todo.setTodoDesc(req.getParameter("desc"));
                todo.setTargetDatetime(LocalDateTime.parse(req.getParameter("target")));
                todo.setTodoStatusCode("P"); // default status
                todo.setCreatedBy(req.getParameter("createdBy"));

                todoDAO.addTodo(todo);
            } else if ("Delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                todoDAO.deleteTodo(id);
            }
            // Refresh page
            resp.sendRedirect("todos");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
