package com.aditya.todo;

import java.util.Date;

public class Todo extends BaseTask {
    public Todo(int id, String description, Date addedDate, Date targetDateTime, String status) {
        super(id, description, addedDate, targetDateTime, status);
    }
}
