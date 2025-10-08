package com.yogesh.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;  // bcrypt hash
    private String role;      // USER / OWNER / ADMIN
    private Date createdOn;
}
