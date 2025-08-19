package com.aditya.todo;

import java.util.*;

public class SearchManager {
    public static List<BaseTask> search(Collection<BaseTask> tasks, String keyword) {
        List<BaseTask> results = new ArrayList<>();
        for (BaseTask task : tasks) {
            if (task.matches(keyword)) {
                results.add(task);
            }
        }
        return results;
    }
}
