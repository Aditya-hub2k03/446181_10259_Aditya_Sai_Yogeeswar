package com.yogesh.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.yogesh.dao.TodoDAO;
import com.yogesh.dao.TodoItem;

@WebServlet("/TodoServlet")
public class TodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDAO todoDAO;

    String th = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "  <head>\n"
            + "    <title>To Do List Application</title>\n"
            + "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css\" />\n"
            + "    <style>\n"
            + "      body { font-family: Arial, sans-serif; }\n"
            + "      table { margin: 20px auto; border-collapse: collapse; width: 90%; }\n"
            + "      th, td { border: 1px solid black; padding: 8px; text-align: center; }\n"
            + "      h1, h2 { text-align: center; }\n"
            + "      .success { text-align: center; color: green; font-weight: bold; margin: 10px; }\n"
            + "      .disabled-btn { background-color: #cccccc; cursor: not-allowed; }\n"
            + "      input[type=\"submit\"] { padding: 5px 15px; }\n"
            + "    </style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <h1>To Do List</h1>\n"
            + "    <form method=\"post\" action=\"TodoServlet\">\n"
            + "      <table style=\"margin: 0 auto;\">\n"
            + "        <tr>\n"
            + "          <td><label for=\"todayDate\">Today's Date:</label></td>\n"
            + "          <td>\n"
            + "            <input type=\"date\" id=\"todayDate\" name=\"todayDate\" required readonly />\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"todoTitle\">To Do Title:</label></td>\n"
            + "          <td>\n"
            + "            <input type=\"text\" id=\"todoTitle\" name=\"todoTitle\" required />\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"todoDescription\">Description:</label></td>\n"
            + "          <td>\n"
            + "            <input type=\"text\" id=\"todoDescription\" name=\"todoDescription\" />\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"targetDateTime\">Target Date & Time:</label></td>\n"
            + "          <td>\n"
            + "            <input type=\"text\" id=\"targetDateTime\" placeholder=\"Select target Date and time\" name=\"targetDateTime\" required />\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"todostatus\">Status:</label></td>\n"
            + "          <td>\n"
            + "            <input type=\"text\" id=\"todoStatus\" value=\"Pending\" name=\"todoStatus\" readonly />\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td colspan=\"2\" align=\"center\">\n"
            + "            <input type=\"submit\" value=\"Add To Do\" />\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "      </table>\n"
            + "    </form>\n"
            + "    <h2>To Do List</h2>\n"
            + "    <div id=\"successMessage\" class=\"success\"></div>\n"
            + "    <table border=\"1\" id=\"todoTable\">\n"
            + "      <thead>\n"
            + "        <tr>\n"
            + "          <th>Todo ID</th>\n"
            + "          <th>ToDo Title</th>\n"
            + "          <th>ToDo Description</th>\n"
            + "          <th>Target Date & Time</th>\n"
            + "          <th>Current Status</th>\n"
            + "          <th>Change Status</th>\n"
            + "        </tr>\n"
            + "      </thead>\n"
            + "      <tbody>\n"
            + "        %TODO_ROWS%\n"
            + "      </tbody>\n"
            + "    </table>\n"
            + "    <script src=\"https://cdn.jsdelivr.net/npm/flatpickr\"></script>\n"
            + "    <script>\n"
            + "      document.addEventListener('DOMContentLoaded', function() {\n"
            + "        var today = new Date();\n"
            + "        var yyyy = today.getFullYear();\n"
            + "        var mm = String(today.getMonth() + 1).padStart(2, '0');\n"
            + "        var dd = String(today.getDate()).padStart(2, '0');\n"
            + "        var formattedDate = yyyy + '-' + mm + '-' + dd;\n"
            + "        document.getElementById('todayDate').value = formattedDate;\n"
            + "        flatpickr(\"#targetDateTime\", {\n"
            + "          enableTime: true,\n"
            + "          dateFormat: \"Y-m-d H:i\",\n"
            + "        });\n"
            + "      });\n"
            + "      function changeStatus(todoId, currentStatus) {\n"
            + "        var newStatus;\n"
            + "        if (currentStatus === 'Pending') {\n"
            + "          newStatus = 'In Progress';\n"
            + "        } else if (currentStatus === 'In Progress') {\n"
            + "          newStatus = 'Completed';\n"
            + "        } else {\n"
            + "          // Do nothing if already Completed\n"
            + "          return;\n"
            + "        }\n"
            + "        \n"
            + "        fetch('TodoServlet?action=changeStatus&todoId=' + todoId + '&newStatus=' + newStatus)\n"
            + "          .then(response => response.text())\n"
            + "          .then(data => {\n"
            + "            document.getElementById('successMessage').innerHTML = data;\n"
            + "            // Reload the page to reflect the status change\n"
            + "            setTimeout(function() { location.reload(); }, 1000);\n"
            + "          })\n"
            + "          .catch(error => console.error('Error:', error));\n"
            + "      }\n"
            + "    </script>\n"
            + "  </body>\n"
            + "</html>";

    public TodoServlet() {
        super();
        this.todoDAO = new TodoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("changeStatus".equals(action)) {
            handleStatusChange(request, response);
            return;
        }
        response.setContentType("text/html");
        String todoHTML = generateTodoHTML();
        response.getWriter().append(todoHTML);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This handles only form submissions for adding new tasks
        try {
            TodoItem newTodo = new TodoItem();
            newTodo.setTodoTitle(request.getParameter("todoTitle"));
            newTodo.setTodoDesc(request.getParameter("todoDescription"));
            newTodo.setTargetDatetime(LocalDateTime.parse(request.getParameter("targetDateTime").replace(" ", "T")));
            newTodo.setTodoStatusCode("P");
            newTodo.setCreatedBy("user");

            todoDAO.addTodo(newTodo);

            String todoHTML = generateTodoHTML();
            String acknowledgment = "<script>document.getElementById('successMessage').innerHTML = 'Task submitted successfully!';</script>";
            response.setContentType("text/html");
            response.getWriter().append(todoHTML + acknowledgment);
        } catch (Exception e) {
            response.getWriter().append("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleStatusChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int todoId = Integer.parseInt(request.getParameter("todoId"));
            String newStatus = request.getParameter("newStatus");

            TodoItem todo = todoDAO.getTodoById(todoId);
            if (todo != null) {
                String statusCode = "P"; // Default to Pending
                if ("In Progress".equals(newStatus)) {
                    statusCode = "I";
                } else if ("Completed".equals(newStatus)) {
                    statusCode = "C";
                }
                todo.setTodoStatusCode(statusCode);
                todo.setModifiedBy("user");
                todoDAO.updateTodo(todo);
                response.getWriter().write("Status updated to " + newStatus + " successfully!");
            } else {
                response.getWriter().write("Todo item not found!");
            }
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String generateTodoHTML() {
        StringBuilder todoRows = new StringBuilder();
        try {
            List<TodoItem> todos = todoDAO.getAllTodos();
            for (TodoItem todo : todos) {
                todoRows.append("<tr>");
                todoRows.append("<td>").append(todo.getTodoId()).append("</td>");
                todoRows.append("<td>").append(todo.getTodoTitle() != null ? todo.getTodoTitle() : "").append("</td>");
                todoRows.append("<td>").append(todo.getTodoDesc() != null ? todo.getTodoDesc() : "").append("</td>");
                todoRows.append("<td>").append(todo.getTargetDatetime()).append("</td>");

                String status = getStatusName(todo.getTodoStatusCode());
                todoRows.append("<td>").append(status).append("</td>");

                // Disable button for Completed tasks
                if (status.equals("Completed")) {
                    todoRows.append("<td><button class='disabled-btn' disabled>Change Status</button></td>");
                } else {
                    todoRows.append("<td><button onclick=\"changeStatus(")
                            .append(todo.getTodoId())
                            .append(", '")
                            .append(status)
                            .append("')\">Change Status</button></td>");
                }

                todoRows.append("</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return th.replace("%TODO_ROWS%", todoRows.toString());
    }

    private String getStatusName(String statusCode) {
        switch (statusCode) {
            case "P":
                return "Pending";
            case "I":
                return "In Progress";
            case "C":
                return "Completed";
            default:
                return "Unknown";
        }
    }
}
