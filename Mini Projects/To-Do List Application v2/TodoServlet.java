package com.yogesh.todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class TodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<Todo> todos = new ArrayList<>();
    private int todoCounter = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(convertToJson(todos));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        Todo todo = parseTodoFromJson(requestBody.toString());
        todo.setId(++todoCounter);
        todos.add(todo);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(convertToJson(todo));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        Todo updatedTodo = parseTodoFromJson(requestBody.toString());
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == updatedTodo.getId()) {
                todos.set(i, updatedTodo);
                break;
            }
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(convertToJson(updatedTodo));
        out.flush();
    }

    // Helper method to convert a Todo object to JSON
    private String convertToJson(Todo todo) {
        return String.format(
            "{\"id\":%d,\"description\":\"%s\",\"addedDate\":\"%s\",\"targetDateTime\":\"%s\",\"status\":\"%s\"}",
            todo.getId(),
            escapeJson(todo.getDescription()),
            todo.getAddedDate(),
            todo.getTargetDateTime(),
            todo.getStatus()
        );
    }

    // Helper method to convert a list of Todo objects to JSON
    private String convertToJson(List<Todo> todos) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < todos.size(); i++) {
            json.append(convertToJson(todos.get(i)));
            if (i < todos.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    // Helper method to parse JSON into a Todo object
    private Todo parseTodoFromJson(String json) {
        Todo todo = new Todo();
        String[] parts = json.replace("{", "").replace("}", "").split(",");

        for (String part : parts) {
            String[] keyValue = part.split(":");
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");

            switch (key) {
                case "id":
                    todo.setId(Integer.parseInt(value));
                    break;
                case "description":
                    todo.setDescription(value);
                    break;
                case "addedDate":
                    todo.setAddedDate(value);
                    break;
                case "targetDateTime":
                    todo.setTargetDateTime(value);
                    break;
                case "status":
                    todo.setStatus(value);
                    break;
            }
        }
        return todo;
    }

    // Helper method to escape JSON strings
    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
