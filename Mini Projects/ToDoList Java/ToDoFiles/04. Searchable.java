package com.aditya.todo;

import java.util.Date;
import java.util.List;

public interface Searchable {
    List<Todo> searchById(int id);
    List<Todo> searchByKeyword(String keyword);
    List<Todo> searchByDateRange(Date start, Date end);
    List<Todo> searchByStatus(String status);
}
