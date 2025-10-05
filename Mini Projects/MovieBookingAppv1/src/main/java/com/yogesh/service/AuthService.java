package com.yogesh.service;

import com.yogesh.dto.AuthDto;
import com.yogesh.entity.User;

public interface AuthService {
    AuthDto.AuthResponse login(AuthDto.LoginRequest loginRequest) throws Exception;
    void register(AuthDto.RegisterRequest registerRequest) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
