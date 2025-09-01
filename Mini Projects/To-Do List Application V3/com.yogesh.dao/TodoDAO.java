package com.yogesh.dao;


import com.yogesh.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    // Insert a new todo
    public void addTodo(TodoItem todo) throws Exception {
        String sql = "INSERT INTO todo_items (todo_title, todo_desc, target_datetime, " +
                     "todo_status_code, created_by, created_date) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, todo.getTodoTitle());
            ps.setString(2, todo.getTodoDesc());
            ps.setTimestamp(3, Timestamp.valueOf(todo.getTargetDatetime()));
            ps.setString(4, todo.getTodoStatusCode() == null ? "P" : todo.getTodoStatusCode());
            ps.setString(5, todo.getCreatedBy());
            ps.executeUpdate();
        }
    }

    // Fetch all todos
    public List<TodoItem> getAllTodos() throws Exception {
        List<TodoItem> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo_items";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TodoItem todo = new TodoItem();
                todo.setTodoId(rs.getInt("todo_id"));
                todo.setTodoTitle(rs.getString("todo_title"));
                todo.setTodoDesc(rs.getString("todo_desc"));
                todo.setTargetDatetime(rs.getTimestamp("target_datetime").toLocalDateTime());
                todo.setTodoStatusCode(rs.getString("todo_status_code"));
                todo.setCreatedBy(rs.getString("created_by"));
                if (rs.getTimestamp("created_date") != null)
                    todo.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                todo.setModifiedBy(rs.getString("modified_by"));
                if (rs.getTimestamp("modified_date") != null)
                    todo.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());

                todos.add(todo);
            }
        }
        return todos;
    }

    // Update a todo
    public void updateTodo(TodoItem todo) throws Exception {
        String sql = "UPDATE todo_items SET todo_title=?, todo_desc=?, target_datetime=?, " +
                     "todo_status_code=?, modified_by=?, modified_date=NOW() WHERE todo_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, todo.getTodoTitle());
            ps.setString(2, todo.getTodoDesc());
            ps.setTimestamp(3, Timestamp.valueOf(todo.getTargetDatetime()));
            ps.setString(4, todo.getTodoStatusCode());
            ps.setString(5, todo.getModifiedBy());
            ps.setInt(6, todo.getTodoId());
            ps.executeUpdate();
        }
    }

    // Delete a todo
    public void deleteTodo(int todoId) throws Exception {
        String sql = "DELETE FROM todo_items WHERE todo_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, todoId);
            ps.executeUpdate();
        }
    }

    // Fetch todo by id
    public TodoItem getTodoById(int todoId) throws Exception {
        String sql = "SELECT * FROM todo_items WHERE todo_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, todoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TodoItem todo = new TodoItem();
                    todo.setTodoId(rs.getInt("todo_id"));
                    todo.setTodoTitle(rs.getString("todo_title"));
                    todo.setTodoDesc(rs.getString("todo_desc"));
                    todo.setTargetDatetime(rs.getTimestamp("target_datetime").toLocalDateTime());
                    todo.setTodoStatusCode(rs.getString("todo_status_code"));
                    todo.setCreatedBy(rs.getString("created_by"));
                    if (rs.getTimestamp("created_date") != null)
                        todo.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                    todo.setModifiedBy(rs.getString("modified_by"));
                    if (rs.getTimestamp("modified_date") != null)
                        todo.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
                    return todo;
                }
            }
        }
        return null;
    }
}
