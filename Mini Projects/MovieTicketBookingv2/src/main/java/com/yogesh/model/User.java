package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

import com.yogesh.dto.RoleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
	private int userId;
    private String userName;
    private String email;
    private String passwordHash;
    private String city;
    private String preferredGenre;
    private Timestamp createdOn;
    private Timestamp modifiedOn;
    private List<Role> roles;
    private transient String password;

    
}
