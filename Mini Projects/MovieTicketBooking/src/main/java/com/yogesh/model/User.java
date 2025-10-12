package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private String city;
    private String preferredGenre;
    private Timestamp createdOn;
    private Timestamp modifiedOn;
}
