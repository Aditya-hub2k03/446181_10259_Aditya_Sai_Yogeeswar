package com.yogesh.service;

import com.yogesh.dto.*;
import com.yogesh.exception.UserAlreadyExistsException;
import com.yogesh.model.User;
import com.yogesh.util.DBCon;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;

@Service
public class UserService {

    public SignupUserResponseDto signupUser(SignupUserRequestDto request) throws UserAlreadyExistsException, ClassNotFoundException {
        try (Connection connection = DBCon.getConnection()) {
            if (userExists(connection, request.getEmail())) {
                throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
            }

            String insertQuery = "INSERT INTO users (user_name, email, password, created_by, created_on) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, request.getName());
                statement.setString(2, request.getEmail());
                statement.setString(3, encodePassword(request.getPassword()));
                statement.setString(4, request.getEmail());
                statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long userId = generatedKeys.getLong(1);
                        SignupUserResponseDto response = new SignupUserResponseDto();
                        response.setUserId(userId);
                        response.setName(request.getName());
                        response.setEmail(request.getEmail());
                        return response;
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public LoginResponseDto login(LoginRequestDto request) throws ClassNotFoundException {
        try (Connection connection = DBCon.getConnection()) {
            String selectQuery = "SELECT user_id, user_name, email, password FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                statement.setString(1, request.getEmail());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        boolean isPasswordValid = verifyPassword(request.getPassword(), storedPassword);
                        LoginResponseDto response = new LoginResponseDto();
                        response.setLoggedIn(isPasswordValid);
                        response.setUserId(resultSet.getLong("user_id"));
                        response.setName(resultSet.getString("user_name"));
                        return response;
                    } else {
                        throw new RuntimeException("User not found");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    private boolean userExists(Connection connection, String email) throws SQLException {
        String checkQuery = "SELECT 1 FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(checkQuery)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private String encodePassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private boolean verifyPassword(String rawPassword, String encodedPassword) {
        return BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword).verified;
    }
}
