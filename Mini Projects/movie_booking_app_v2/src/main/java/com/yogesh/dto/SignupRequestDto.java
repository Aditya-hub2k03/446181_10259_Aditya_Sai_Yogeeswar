package com.yogesh.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String userName;
    private String email;
    private String password;
    private String role;
}
