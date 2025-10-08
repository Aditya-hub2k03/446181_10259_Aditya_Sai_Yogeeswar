package com.yogesh.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private boolean success;
    private String message;
    private int userId;
    private String role;
}
