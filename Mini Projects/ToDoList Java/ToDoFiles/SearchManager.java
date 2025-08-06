package com.aditya.todo;

import java.util.*;

public class SearchManager extends TaskManager implements Searchable {

    @Override 
    public List<Todo> searchById(int id) {
        List<Todo> results = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.id == id) {
                results.add(todo);
            }
        }
        return results;
    }

    @Override
    public List<Todo> searchByKeyword(String keyword) {
        List<Todo> results = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.description != null && todo.description.contains(keyword)) {
                results.add(todo);
            }
        }
        return results;
    }

    @Override
    public List<Todo> searchByDateRange(Date start, Date end) {
        List<Todo> results = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.targetDateTime.after(start) && todo.targetDateTime.before(end)) {
                results.add(todo);
            }
        }
        return results;
    }

    @Override
    public List<Todo> searchByStatus(String status) {
        List<Todo> results = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.status != null && todo.status.equalsIgnoreCase(status)) {
                results.add(todo);
            }
        }
        return results;
    }
}
