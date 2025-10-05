package com.yogesh.controller;

import com.yogesh.dto.ApiResponse;
import com.yogesh.dto.AuthDto;
import com.yogesh.exception.AuthException;
import com.yogesh.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> login(@Valid @RequestBody AuthDto.LoginRequest loginRequest) {
        try {
            AuthDto.AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", response));
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthDto.RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Registration successful", "User registered"));
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }
}
