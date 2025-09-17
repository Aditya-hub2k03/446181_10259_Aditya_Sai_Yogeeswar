package com.yp.service;

import com.yp.dto.*;
import com.yp.model.User;
import com.yp.util.DbUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public SignupUserResponseDto signupUser(SignupUserRequestDto requestDto) {
        SignupUserResponseDto responseDto = new SignupUserResponseDto();
        ResponseStatus status = new ResponseStatus();

        try (Connection conn = DbUtil.getConnection()) {
            String checkQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, requestDto.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                status.setStatusCode("400");
                status.setMessage("User already exists.");
                responseDto.setStatus(status);
                return responseDto;
            }

            String insertQuery = "INSERT INTO users (user_name, email, password, createdby, createdon, modifiedby, modifiedon) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            insertStmt.setString(1, requestDto.getName());
            insertStmt.setString(2, requestDto.getEmail());
            insertStmt.setString(3, hashedPassword);
            insertStmt.setString(4, requestDto.getName());
            insertStmt.setTimestamp(5, now);
            insertStmt.setString(6, requestDto.getName());
            insertStmt.setTimestamp(7, now);

            insertStmt.executeUpdate();

            ResultSet keys = insertStmt.getGeneratedKeys();
            if (keys.next()) {
                int userId = keys.getInt(1);
                responseDto.setUserId(userId);
                responseDto.setName(requestDto.getName());
                responseDto.setEmail(requestDto.getEmail());
                status.setStatusCode("200");
                status.setMessage("User registered successfully.");
                responseDto.setStatus(status);
            }
        } catch (Exception e) {
            status.setStatusCode("500");
            status.setMessage("Error: " + e.getMessage());
            responseDto.setStatus(status);
        }
        return responseDto;
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        LoginResponseDto responseDto = new LoginResponseDto();
        ResponseStatus status = new ResponseStatus();

        try (Connection conn = DbUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, requestDto.getEmail());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                boolean matches = passwordEncoder.matches(requestDto.getPassword(), storedPassword);
                responseDto.setLoggedIn(matches);
                if (matches) {
                    status.setStatusCode("200");
                    status.setMessage("Login successful.");
                } else {
                    status.setStatusCode("401");
                    status.setMessage("Invalid credentials.");
                }
            } else {
                responseDto.setLoggedIn(false);
                status.setStatusCode("404");
                status.setMessage("User not found.");
            }
        } catch (Exception e) {
            responseDto.setLoggedIn(false);
            status.setStatusCode("500");
            status.setMessage("Error: " + e.getMessage());
        }
        responseDto.setStatus(status);
        return responseDto;
    }
}
