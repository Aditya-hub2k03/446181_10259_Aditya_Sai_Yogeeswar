package com.aditya.todo;

import java.time.LocalDateTime;

public class BaseTask {
    int id;
    String description;
    LocalDateTime targetDateTime;
    String status;

    public BaseTask(int id, String description, LocalDateTime targetDateTime) {
        this.id = id;
        this.description = description;
        this.targetDateTime = targetDateTime;
        this.status = "Pending";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTargetDateTime(LocalDateTime targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
