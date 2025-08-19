package com.aditya.todo;

import java.util.Date;

public class Todo extends BaseTask {
    private boolean completed;

    public Todo(int id, String title, String description, Date dueDate) {
        super(id, title, description, dueDate);
        this.completed = false;
    }

    public void markCompleted() { this.completed = true; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return super.toString() + "\nCompleted: " + (completed ? "Yes" : "No");
    }
}
