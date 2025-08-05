package com.aditya.todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskManager {
    List<Todo> todos = new ArrayList<>();
    int todoCounter = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void addTask(String description, LocalDateTime targetDateTime) {
        todoCounter++;
        todos.add(new Todo(todoCounter, description, targetDateTime));
        System.out.println("Task added successfully.");
    }

    public Todo findTaskById(int id) {
        return todos.stream().filter(t -> t.id == id).findFirst().orElse(null);
    }

    public void editTask(int id, String newDesc, LocalDateTime newDateTime) {
        Todo todo = findTaskById(id);
        if (todo != null) {
            if (!newDesc.isEmpty()) todo.setDescription(newDesc);
            if (newDateTime != null) todo.setTargetDateTime(newDateTime);
            todo.updateEditedDate();
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void updateStatus(int id, String newStatus) {
        Todo todo = findTaskById(id);
        if (todo != null) {
            todo.updateStatus(newStatus);
            System.out.println("Task status updated.");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void printAllTasks() {
        if (todos.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        todos.sort(Comparator.comparing(t -> t.targetDateTime));
        todos.forEach(t -> t.display(formatter));
    }
}
