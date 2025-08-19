package com.aditya.todo;

import java.util.*;  
import java.sql.*;   
import java.sql.Date;

public class TaskManager {
    private Map<Integer, BaseTask> tasks;

    public TaskManager() {
        tasks = new HashMap<>();
        loadTasksFromDB(); 
    }

    public void addTask(BaseTask task) {
        tasks.put(task.getId(), task);
        addTaskToDB(task);
        System.out.println("Task added successfully.");
    }

    private void addTaskToDB(BaseTask task) {
        String sql = "INSERT INTO tasks (id, title, description, due_date, completed) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setTimestamp(4, new java.sql.Timestamp(task.getDueDate().getTime()));
            boolean isCompleted = (task instanceof Todo && ((Todo) task).isCompleted());
            stmt.setBoolean(5, isCompleted);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(int id) {
        tasks.remove(id);
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Task removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BaseTask getTask(int id) {
        return tasks.get(id);
    }

    public Collection<BaseTask> getAllTasks() {
        return tasks.values();
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (BaseTask task : tasks.values()) {
                System.out.println(task);
            }
        }
    }

    public void markCompleted(int id) {
        BaseTask task = tasks.get(id);
        if (task instanceof Todo) {
            ((Todo) task).markCompleted();
            updateTaskCompletionInDB(id, true);
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Task not found or not a Todo.");
        }
    }

    private void updateTaskCompletionInDB(int id, boolean completed) {
        String sql = "UPDATE tasks SET completed = ? WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, completed);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(int id, String newTitle, String newDesc, Date newDueDate) {
        BaseTask task = tasks.get(id);
        if (task != null) {
            task.setTitle(newTitle);
            task.setDescription(newDesc);
            task.setDueDate(newDueDate);

            String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ? WHERE id = ?";
            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newTitle);
                stmt.setString(2, newDesc);
                stmt.setTimestamp(3, new java.sql.Timestamp(newDueDate.getTime()));
                stmt.setInt(4, id);
                stmt.executeUpdate();
                System.out.println("Task updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Task not found.");
        }
    }

    private void loadTasksFromDB() {
        String sql = "SELECT * FROM tasks";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                java.util.Date dueDate = new java.util.Date(rs.getTimestamp("due_date").getTime());
                boolean completed = rs.getBoolean("completed");

                Todo task = new Todo(id, title, desc, dueDate);
                if (completed) task.markCompleted();
                tasks.put(id, task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
