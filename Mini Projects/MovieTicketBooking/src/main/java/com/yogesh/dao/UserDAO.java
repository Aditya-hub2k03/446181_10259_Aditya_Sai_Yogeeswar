package com.yogesh.dao;

import com.yogesh.model.User;
import com.yogesh.util.DBUtil;
import java.sql.*;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
	
	
    public User findUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCity(rs.getString("city"));
                user.setPreferredGenre(rs.getString("preferred_genre"));
                user.setCreatedOn(rs.getTimestamp("created_on"));
                user.setModifiedOn(rs.getTimestamp("modified_on"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean isEmailRegistered(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (user_name, email, password, city, preferred_genre, created_on, modified_on) " +
                     "VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getCity());
            stmt.setString(5, user.getPreferredGenre());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Fetch User Information
    public User fetchUserInformation(int userId) {
        String sql = "SELECT user_id, user_name, email, city, created_on FROM users WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setCity(rs.getString("city"));
                user.setCreatedOn(rs.getTimestamp("created_on"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update Profile Information
    public boolean updateProfileInformation(User user) {
        String sql = "UPDATE users SET user_name = ?, email = ?, city = ?, modified_on = NOW() WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getCity());
            stmt.setInt(4, user.getUserId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add Payment Method
    public boolean addPaymentMethod(int userId, String cardNumber, String cardHolder, String expiryDate, String paymentType) {
        String sql = "INSERT INTO payments (user_id, card_number, card_holder, expiry_date, payment_type, created_on) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, cardNumber);
            stmt.setString(3, cardHolder);
            stmt.setString(4, expiryDate);
            stmt.setString(5, paymentType);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
