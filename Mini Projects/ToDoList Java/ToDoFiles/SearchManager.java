package com.aditya.todo;

import java.time.LocalDateTime;

public class SearchManager extends TaskManager implements Searchable {

    @Override
    public void searchById(int id) {
        Todo todo = findTaskById(id);
        if (todo != null) todo.display(formatter);
        else System.out.println("No task found.");
    }

    @Override
    public void searchByKeyword(String keyword) {
        todos.stream()
             .filter(t -> t.description.toLowerCase().contains(keyword.toLowerCase()))
             .forEach(t -> t.display(formatter));
    }

    @Override
    public void searchByDateRange(LocalDateTime start, LocalDateTime end) {
        todos.stream()
             .filter(t -> t.targetDateTime.isAfter(start) && t.targetDateTime.isBefore(end))
             .forEach(t -> t.display(formatter));
    }

    @Override
    public void searchByStatus(String status) {
        todos.stream()
             .filter(t -> t.status.equalsIgnoreCase(status))
             .forEach(t -> t.display(formatter));
    }
}
