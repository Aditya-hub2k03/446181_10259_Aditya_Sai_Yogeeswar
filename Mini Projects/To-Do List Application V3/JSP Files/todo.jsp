<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.yogesh.dao.*" %>

<%!
    // Helper method to get status name
    private String getStatusName(String statusCode) {
        switch (statusCode) {
            case "P": return "Pending";
            case "I": return "In Progress";
            case "C": return "Completed";
            default: return "Unknown";
        }
    }
%>

<%
    // Check if user is logged in
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    UserItem user = (UserItem) session.getAttribute("user");
    TodoDAO todoDAO = new TodoDAO();
    List<TodoItem> todos = null;
    
    try {
        todos = todoDAO.getAllTodos(user.getUserId());
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>To Do List Application</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" />
    <style>
        body { font-family: Arial, sans-serif; }
        table { margin: 20px auto; border-collapse: collapse; width: 90%; }
        th, td { border: 1px solid #000; padding: 8px; text-align: center; }
        h1, h2 { text-align: center; }
        .disabled-btn { background: #ccc; cursor: not-allowed; }
        input[type=submit] { padding: 5px 15px; }
        input[readonly] { background: #f5f5f5; }
        .user-info { text-align: right; margin: 10px 20px; }
        .logout-btn { padding: 5px 10px; background: #f44336; color: #fff; border: none; border-radius: 3px; }
        #toast {
            visibility: hidden; min-width: 250px; background: #333; color: #fff;
            text-align: center; border-radius: 5px; padding: 16px; position: fixed;
            z-index: 1; left: 50%; bottom: 30px; transform: translateX(-50%); font-size: 17px;
        }
        #toast.show { visibility: visible; animation: fadein .5s, fadeout .5s 2.5s; }
        #toast.success { background: #4CAF50; }
        #toast.error { background: #f44336; }
        @keyframes fadein { from { bottom: 0; opacity: 0; } to { bottom: 30px; opacity: 1; } }
        @keyframes fadeout { from { bottom: 30px; opacity: 1; } to { bottom: 0; opacity: 0; } }
        
        /* Flatpickr custom styles */
        .flatpickr-calendar {
            z-index: 9999 !important;
        }
        
        #targetDateTime {
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="user-info">
        Welcome, <span id="username"><%= user.getUsername() %></span>!
        <button class="logout-btn" onclick="window.location.href='logout.jsp'">Logout</button>
    </div>
    
    <h1>To Do List</h1>
    
    <form method="post" action="TodoServlet">
        <table style="margin: 0 auto;">
            <tr>
                <td><label for="todayDate">Today's Date:</label></td>
                <td><input type="date" id="todayDate" name="todayDate" required readonly /></td>
            </tr>
            <tr>
                <td><label for="todoTitle">To Do Title:</label></td>
                <td><input type="text" id="todoTitle" name="todoTitle" required /></td>
            </tr>
            <tr>
                <td><label for="todoDescription">Description:</label></td>
                <td><input type="text" id="todoDescription" name="todoDescription" /></td>
            </tr>
            <tr>
                <td><label for="targetDateTime">Target Date & Time:</label></td>
                <td><input type="text" id="targetDateTime" placeholder="Select target Date and time" name="targetDateTime" required /></td>
            </tr>
            <tr>
                <td><label for="todostatus">Status:</label></td>
                <td><input type="text" id="todoStatus" value="Pending" name="todoStatus" readonly /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Add To Do" /></td>
            </tr>
        </table>
    </form>
    
    <h2>To Do List</h2>
    <div id="toast"></div>
    
    <table border="1" id="todoTable">
        <thead>
            <tr>
                <th>Todo ID</th>
                <th>ToDo Title</th>
                <th>ToDo Description</th>
                <th>Target Date & Time</th>
                <th>Current Status</th>
                <th>Change Status</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (todos != null) {
                    for (TodoItem todo : todos) {
                        String status = getStatusName(todo.getTodoStatusCode());
            %>
            <tr>
                <td><%= todo.getTodoId() %></td>
                <td><%= todo.getTodoTitle() != null ? todo.getTodoTitle() : "" %></td>
                <td><%= todo.getTodoDesc() != null ? todo.getTodoDesc() : "" %></td>
                <td><%= todo.getTargetDatetime() %></td>
                <td id="status-<%= todo.getTodoId() %>"><%= status %></td>
                <td>
                    <% if ("Completed".equals(status)) { %>
                        <button id="btn-<%= todo.getTodoId() %>" class="disabled-btn" disabled>Change Status</button>
                    <% } else { %>
                        <button id="btn-<%= todo.getTodoId() %>" onclick="changeStatus(<%= todo.getTodoId() %>, '<%= status %>')">Change Status</button>
                    <% } %>
                </td>
                <td>
                    <button onclick="deleteTodo(<%= todo.getTodoId() %>)" style="background-color: #f44336; color: white;">Delete</button>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
    
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var today = new Date();
            var yyyy = today.getFullYear();
            var mm = String(today.getMonth() + 1).padStart(2, '0');
            var dd = String(today.getDate()).padStart(2, '0');
            var formattedDate = yyyy + '-' + mm + '-' + dd;
            document.getElementById('todayDate').value = formattedDate;
            flatpickr("#targetDateTime", { enableTime: true, dateFormat: "Y-m-d H:i" });
        });

        function showToast(message, isError = false) {
            var toast = document.getElementById('toast');
            toast.innerText = message;
            toast.className = isError ? 'show error' : 'show success';
            setTimeout(function(){ toast.className = toast.className.replace('show', ''); }, 3000);
        }

        function changeStatus(todoId, currentStatus) {
            var newStatus;
            if (currentStatus === 'Pending') newStatus = 'In Progress';
            else if (currentStatus === 'In Progress') newStatus = 'Completed';
            else return;

            fetch('TodoServlet?action=changeStatus&todoId=' + todoId + '&newStatus=' + newStatus)
                .then(response => response.text())
                .then(data => {
                    showToast(data);
                    let statusCell = document.getElementById('status-' + todoId);
                    if (statusCell) statusCell.innerText = newStatus;
                    let btn = document.getElementById('btn-' + todoId);
                    if (btn) {
                        if (newStatus === 'Completed') {
                            btn.innerText = 'Change Status';
                            btn.disabled = true;
                            btn.classList.add('disabled-btn');
                        } else {
                            btn.setAttribute('onclick', "changeStatus(" + todoId + ", '" + newStatus + "')");
                        }
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showToast('Error updating status', true);
                });
        }

        function deleteTodo(todoId) {
            if (confirm('Are you sure you want to delete this task?')) {
                fetch('TodoServlet?action=deleteTodo&todoId=' + todoId)
                    .then(response => response.text())
                    .then(data => {
                        showToast(data);
                        setTimeout(function() { location.reload(); }, 1000);
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        showToast('Error deleting task', true);
                    });
            }
        }
        
        // Display session messages
        <% 
            String successMessage = (String) session.getAttribute("successMessage");
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (successMessage != null) {
                session.removeAttribute("successMessage");
        %>
            showToast('<%= successMessage %>');
        <% } %>
        <% if (errorMessage != null) {
                session.removeAttribute("errorMessage");
        %>
            showToast('<%= errorMessage %>', true);
        <% } %>
    </script>
</body>
</html>
