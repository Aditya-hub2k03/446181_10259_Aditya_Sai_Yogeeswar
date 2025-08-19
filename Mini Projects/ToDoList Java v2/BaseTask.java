package com.aditya.todo;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTask implements Searchable {
    private int id;
    private String title;
    private String description;
    private Date dueDate;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public BaseTask(int id, String title, String description, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDueDate() { return dueDate; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    @Override
    public String toString() {
        return "Task ID: " + id +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nDue Date: " + sdf.format(dueDate);
    }

    @Override
    public boolean matches(String keyword) {
        return title.toLowerCase().contains(keyword.toLowerCase()) ||
               description.toLowerCase().contains(keyword.toLowerCase());
    }
}
