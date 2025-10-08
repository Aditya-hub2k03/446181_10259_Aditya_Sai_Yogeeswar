package com.yogesh.model;

import lombok.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int userId;
    private String userName;
    private String email;
    private String passwordHash;
    private String role;
    private String city;
    private Timestamp createdAt;
}
