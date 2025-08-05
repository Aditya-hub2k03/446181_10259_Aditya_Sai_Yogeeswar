package com.aditya.todo;

import java.time.LocalDateTime;

public interface Searchable {
    void searchById(int id);
    void searchByKeyword(String keyword);
    void searchByDateRange(LocalDateTime start, LocalDateTime end);
    void searchByStatus(String status);
}
