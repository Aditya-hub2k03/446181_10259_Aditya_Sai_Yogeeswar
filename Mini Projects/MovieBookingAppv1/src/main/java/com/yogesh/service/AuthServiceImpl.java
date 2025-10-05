package com.yogesh.service;

import com.yogesh.dto.AuthDto;
import com.yogesh.entity.User;
import com.yogesh.util.DbUtil;
import com.yogesh.util.PasswordUtil;
import com.yogesh.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthDto.AuthResponse login(AuthDto.LoginRequest loginRequest) throws Exception {
        User user = findUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new Exception("User not found");
        }

        String[] parts = user.getPassword().split(":");
        if (parts.length != 2) {
            throw new Exception("Stored password format invalid");
        }
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String storedHash = parts[1];

        boolean passwordValid = PasswordUtil.verifyPassword(loginRequest.getPassword(), storedHash, salt);
        if (!passwordValid) {
            throw new Exception("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        AuthDto.AuthResponse response = new AuthDto.AuthResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setRoles(parseRoles(user.getRoles()));
        response.setTokenExpiry(jwtUtil.getTokenExpiry());
        return response;
    }

    @Override
    public void register(AuthDto.RegisterRequest registerRequest) throws Exception {
        User existingUser = findUserByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            throw new Exception("Email already registered");
        }

        byte[] salt = PasswordUtil.getSalt();
        String hashedPwd = PasswordUtil.hashPassword(registerRequest.getPassword(), salt);
        String storedPassword = Base64.getEncoder().encodeToString(salt) + ":" + hashedPwd;

        try (Connection conn = DbUtil.getConnection()) {
            String sql = "INSERT INTO users (name, email, password, roles, email_verified) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, registerRequest.getName());
                ps.setString(2, registerRequest.getEmail());
                ps.setString(3, storedPassword);
                ps.setString(4, "USER");
                ps.setBoolean(5, false);
                ps.executeUpdate();
            }
        }
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setRoles(rs.getString("roles"));
                        user.setEmailVerified(rs.getBoolean("email_verified"));
                        user.setCreatedAt(rs.getTimestamp("created_at"));
                        user.setUpdatedAt(rs.getTimestamp("updated_at"));
                        return user;
                    }
                }
            }
        }
        return null;
    }

    private List<String> parseRoles(String rolesStr) {
        List<String> roles = new ArrayList<>();
        if (rolesStr != null && !rolesStr.isEmpty()) {
            String[] parts = rolesStr.split(",");
            for (String role : parts) {
                roles.add(role.trim());
            }
        }
        return roles;
    }
}
