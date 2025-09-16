package com.yp.service;

import com.yp.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Hardcoded users
        users.add(new User("john", "password123"));
        users.add(new User("alice", "alicepass"));
        users.add(new User("bob", "bobpass"));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return users;
    }
}
