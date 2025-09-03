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
            + "      .disabled-btn { background-color: #cccccc; cursor: not-allowed; }\n"
            + "      input[type=\"submit\"] { padding: 5px 15px; }\n"
            + "      input[readonly] { background-color: #f5f5f5; }\n"
            + "      /* Toast Notification */\n"
            + "      #toast {\n"
            + "        visibility: hidden;\n"
            + "        min-width: 250px;\n"
            + "        background-color: #333;\n"
            + "        color: #fff;\n"
            + "        text-align: center;\n"
            + "        border-radius: 5px;\n"
            + "        padding: 16px;\n"
            + "        position: fixed;\n"
            + "        z-index: 1;\n"
            + "        left: 50%;\n"
            + "        bottom: 30px;\n"
            + "        transform: translateX(-50%);\n"
            + "        font-size: 17px;\n"
            + "      }\n"
            + "      #toast.show {\n"
            + "        visibility: visible;\n"
            + "        animation: fadein 0.5s, fadeout 0.5s 2.5s;\n"
            + "      }\n"
            + "      #toast.success {\n"
            + "        background-color: #4CAF50;\n"
            + "      }\n"
            + "      #toast.error {\n"
            + "        background-color: #f44336;\n"
            + "      }\n"
            + "      @keyframes fadein { from { bottom: 0; opacity: 0; } to { bottom: 30px; opacity: 1; } }\n"
            + "      @keyframes fadeout { from { bottom: 30px; opacity: 1; } to { bottom: 0; opacity: 0; } }\n"
            + "    </style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <h1>To Do List</h1>\n"
            + "    <form method=\"post\" action=\"TodoServlet\">\n"
            + "      <table style=\"margin: 0 auto;\">\n"
            + "        <tr>\n"
            + "          <td><label for=\"todayDate\">Today's Date:</label></td>\n"
            + "          <td><input type=\"date\" id=\"todayDate\" name=\"todayDate\" required readonly /></td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"todoTitle\">To Do Title:</label></td>\n"
            + "          <td><input type=\"text\" id=\"todoTitle\" name=\"todoTitle\" required /></td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"todoDescription\">Description:</label></td>\n"
            + "          <td><input type=\"text\" id=\"todoDescription\" name=\"todoDescription\" /></td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"targetDateTime\">Target Date & Time:</label></td>\n"
            + "          <td><input type=\"text\" id=\"targetDateTime\" placeholder=\"Select target Date and time\" name=\"targetDateTime\" required /></td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td><label for=\"todostatus\">Status:</label></td>\n"
            + "          <td><input type=\"text\" id=\"todoStatus\" value=\"Pending\" name=\"todoStatus\" readonly /></td>\n"
            + "        </tr>\n"
            + "        <tr>\n"
            + "          <td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Add To Do\" /></td>\n"
            + "        </tr>\n"
            + "      </table>\n"
            + "    </form>\n"
            + "    <h2>To Do List</h2>\n"
            + "    <div id=\"toast\"></div>\n"
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
            + "      <tbody>%TODO_ROWS%</tbody>\n"
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
            + "        flatpickr(\"#targetDateTime\", { enableTime: true, dateFormat: \"Y-m-d H:i\" });\n"
            + "      });\n"
            + "\n"
            + "      function showToast(message, isError = false) {\n"
            + "        var toast = document.getElementById('toast');\n"
            + "        toast.innerText = message;\n"
            + "        toast.className = isError ? 'show error' : 'show success';\n"
            + "        setTimeout(function(){ toast.className = toast.className.replace('show', ''); }, 3000);\n"
            + "      }\n"
            + "\n"
            + "      function changeStatus(todoId, currentStatus) {\n"
            + "        var newStatus;\n"
            + "        if (currentStatus === 'Pending') newStatus = 'In Progress';\n"
            + "        else if (currentStatus === 'In Progress') newStatus = 'Completed';\n"
            + "        else return;\n"
            + "\n"
            + "        fetch('TodoServlet?action=changeStatus&todoId=' + todoId + '&newStatus=' + newStatus)\n"
            + "          .then(response => response.text())\n"
            + "          .then(data => {\n"
            + "            showToast(data);\n"
            + "            let statusCell = document.getElementById('status-' + todoId);\n"
            + "            if (statusCell) statusCell.innerText = newStatus;\n"
            + "            let btn = document.getElementById('btn-' + todoId);\n"
            + "            if (btn) {\n"
            + "              if (newStatus === 'Completed') {\n"
            + "                btn.innerText = 'Change Status';\n"
            + "                btn.disabled = true;\n"
            + "                btn.classList.add('disabled-btn');\n"
            + "              } else {\n"
            + "                btn.setAttribute('onclick', \"changeStatus(\" + todoId + \", '\" + newStatus + \"')\");\n"
            + "              }\n"
            + "            }\n"
            + "          })\n"
            + "          .catch(error => {\n"
            + "            console.error('Error:', error);\n"
            + "            showToast('Error updating status', true);\n"
            + "          });\n"
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

        // Check for success message in session
        String successMessage = (String) request.getSession().getAttribute("successMessage");
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");

        String html = generateTodoHTML();

        if (successMessage != null) {
            html = html.replace("</body>", "<script>showToast('" + successMessage + "');</script></body>");
            request.getSession().removeAttribute("successMessage");
        }

        if (errorMessage != null) {
            html = html.replace("</body>", "<script>showToast('" + errorMessage + "', true);</script></body>");
            request.getSession().removeAttribute("errorMessage");
        }

        response.getWriter().append(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TodoItem newTodo = new TodoItem();
            newTodo.setTodoTitle(request.getParameter("todoTitle"));
            newTodo.setTodoDesc(request.getParameter("todoDescription"));
            newTodo.setTargetDatetime(LocalDateTime.parse(request.getParameter("targetDateTime").replace(" ", "T")));
            newTodo.setTodoStatusCode("P");
            newTodo.setCreatedBy("user");

            todoDAO.addTodo(newTodo);

            // Store success message in session
            request.getSession().setAttribute("successMessage", "Task submitted successfully!");

            // Redirect to GET request to prevent form resubmission
            response.sendRedirect(request.getContextPath() + "/TodoServlet");
        } catch (Exception e) {
            // Store error message in session
            request.getSession().setAttribute("errorMessage", "Error: " + e.getMessage());
            // Redirect to GET request
            response.sendRedirect(request.getContextPath() + "/TodoServlet");
            e.printStackTrace();
        }
    }

    private void handleStatusChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int todoId = Integer.parseInt(request.getParameter("todoId"));
            String newStatus = request.getParameter("newStatus");

            TodoItem todo = todoDAO.getTodoById(todoId);
            if (todo != null) {
                String statusCode = "P";
                if ("In Progress".equals(newStatus)) statusCode = "I";
                else if ("Completed".equals(newStatus)) statusCode = "C";
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
                todoRows.append("<td id='status-").append(todo.getTodoId()).append("'>").append(status).append("</td>");
                if (status.equals("Completed")) {
                    todoRows.append("<td><button id='btn-").append(todo.getTodoId())
                            .append("' class='disabled-btn' disabled>Change Status</button></td>");
                } else {
                    todoRows.append("<td><button id='btn-").append(todo.getTodoId())
                            .append("' onclick=\"changeStatus(")
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
            case "P": return "Pending";
            case "I": return "In Progress";
            case "C": return "Completed";
            default: return "Unknown";
        }
    }
}
