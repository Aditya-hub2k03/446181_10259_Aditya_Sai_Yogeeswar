package com.aditya.todo;

import java.util.Date;

public class BaseTask {
    int id;
    String description;
    Date addedDate;
    Date editedDate;
    Date targetDateTime;
    String status;

    public BaseTask(int id, String description, Date addedDate, Date targetDateTime, String status) {
        this.id = id;
        this.description = description;
        this.addedDate = addedDate;
        this.targetDateTime = targetDateTime;
        this.status = status;
    }
}
