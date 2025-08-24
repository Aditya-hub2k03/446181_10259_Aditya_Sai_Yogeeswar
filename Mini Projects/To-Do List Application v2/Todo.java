package com.yogesh.todo;

public class Todo {
    private int id;
    private String description;
    private String addedDate;
    private String editedDate;
    private String targetDateTime;
    private String status;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(String editedDate) {
        this.editedDate = editedDate;
    }

    public String getTargetDateTime() {
        return targetDateTime;
    }

    public void setTargetDateTime(String targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
