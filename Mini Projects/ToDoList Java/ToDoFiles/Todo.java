package com.aditya.todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Todo extends BaseTask {
    private LocalDateTime addedDateTime;
    private LocalDateTime editedDateTime;
    private LocalDateTime statusChangedTime;

    public Todo(int id, String description, LocalDateTime targetDateTime) {
        super(id, description, targetDateTime); // Inherits from BaseTask
        this.addedDateTime = LocalDateTime.now();
        this.statusChangedTime = LocalDateTime.now();
    }

    public void updateEditedDate() {
        this.editedDateTime = LocalDateTime.now();
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        this.statusChangedTime = LocalDateTime.now();
    }

    public void display(DateTimeFormatter formatter) {
        System.out.println("\n--- Task ID: " + id + " ---");
        System.out.println("Description: " + description);
        System.out.println("Added On: " + addedDateTime.format(formatter));
        if (editedDateTime != null) {
            System.out.println("Edited On: " + editedDateTime.format(formatter));
        }
        System.out.println("Target Date & Time: " + targetDateTime.format(formatter));
        System.out.println("Status: " + status + " (last changed: " + statusChangedTime.format(formatter) + ")");
        System.out.println("----------------------------");
    }
}
