package com.yogesh.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class User {
    private int userId;
    private String email;
    private String passwordHash;
    private String userName;
    private Role role = Role.USER;
    private Timestamp createdAt;

    public enum Role {
        USER, ADMIN
    }
}
