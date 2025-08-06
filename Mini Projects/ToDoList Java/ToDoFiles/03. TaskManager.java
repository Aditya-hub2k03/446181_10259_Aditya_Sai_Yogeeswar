package com.aditya.todo;

import java.util.*;
import java.text.SimpleDateFormat;

public class TaskManager {
     List<Todo> todos = new ArrayList<>();
     int todoCounter = 0;

     SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
     SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public void addTask(String description, Date addedDate, Date targetDateTime) {
        todoCounter++;
        Todo newTodo = new Todo(todoCounter, description, addedDate, targetDateTime, "Pending");
        todos.add(newTodo);
        System.out.println("Task added successfully.");
    }

    public void editTask(int id, String newDescription, Date newTargetDateTime) {
        for (Todo todo : todos) {
            if (todo.id == id) {
                todo.description = newDescription;
                todo.targetDateTime = newTargetDateTime;
                todo.editedDate = new Date();
                System.out.println("Task updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void updateStatus(int id, String newStatus) {
        for (Todo todo : todos) {
            if (todo.id == id) {
                todo.status = newStatus;
                todo.editedDate = new Date();
                System.out.println("Status updated successfully.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void sortTasksByDateTime() {
        Collections.sort(todos, new Comparator<Todo>() {
            public int compare(Todo t1, Todo t2) {
                return t1.targetDateTime.compareTo(t2.targetDateTime);
            }
        });
    }

    public void printAllTasks() {
        sortTasksByDateTime();
        if (todos.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        for (Todo todo : todos) {
            System.out.println("ID: " + todo.id);
            System.out.println("Description: " + todo.description);
            System.out.println("Added Date: " + dateFormatter.format(todo.addedDate));
            System.out.println("Target DateTime: " + dateTimeFormatter.format(todo.targetDateTime));
            System.out.println("Status: " + todo.status);
            if (todo.editedDate != null) {
                System.out.println("Edited Date: " + dateTimeFormatter.format(todo.editedDate));
            }
            System.out.println("-------------------");
        }
    }
}
