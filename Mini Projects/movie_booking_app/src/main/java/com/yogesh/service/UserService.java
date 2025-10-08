package com.yogesh.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.yogesh.model.User;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {

    public boolean registerUser(String name, String email, String password, String city) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) return false;

            String hash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO users (user_name, email, password_hash, city) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, hash);
            stmt.setString(4, city);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User authenticate(String email, String password) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), storedHash);
                if (result.verified) {
                    return User.builder()
                            .userId(rs.getInt("user_id"))
                            .userName(rs.getString("user_name"))
                            .email(rs.getString("email"))
                            .role(rs.getString("role"))
                            .city(rs.getString("city"))
                            .createdAt(rs.getTimestamp("created_at"))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserProfile(int userId, String name, String city) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE users SET user_name=?, city=? WHERE user_id=?");
            stmt.setString(1, name);
            stmt.setString(2, city);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
