package com.yogesh.service;

import com.yogesh.dto.*;
import com.yogesh.model.*;
import com.yogesh.util.DBConnectionUtil;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public SignupResponseDto registerUser(SignupRequestDto dto) {
        SignupResponseDto response = new SignupResponseDto();
        try (Connection con = DBConnectionUtil.getConnection()) {

            PreparedStatement check = con.prepareStatement("SELECT * FROM users WHERE email=?");
            check.setString(1, dto.getEmail());
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                response.setSuccess(false);
                response.setMessage("Email already exists!");
                return response;
            }

            String hashed = BCrypt.withDefaults().hashToString(12, dto.getPassword().toCharArray());
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(user_name, email, password, role) VALUES (?, ?, ?, ?)"
            );
            ps.setString(1, dto.getUserName());
            ps.setString(2, dto.getEmail());
            ps.setString(3, hashed);
            ps.setString(4, dto.getRole() == null ? "USER" : dto.getRole());

            response.setSuccess(ps.executeUpdate() > 0);
            response.setMessage(response.isSuccess() ? "Signup successful!" : "Signup failed.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public LoginResponseDto loginUser(LoginRequestDto dto) {
        LoginResponseDto response = new LoginResponseDto();
        try (Connection con = DBConnectionUtil.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=?");
            ps.setString(1, dto.getEmail());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                BCrypt.Result result = BCrypt.verifyer().verify(dto.getPassword().toCharArray(), storedHash);
                if (result.verified) {
                    response.setSuccess(true);
                    response.setUserId(rs.getInt("user_id"));
                    response.setRole(rs.getString("role"));
                    response.setMessage("Login successful");
                } else {
                    response.setSuccess(false);
                    response.setMessage("Invalid password");
                }
            } else {
                response.setSuccess(false);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public User getUserById(int userId) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUserName(rs.getString("user_name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setCreatedOn(rs.getTimestamp("created_on"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Added method to match controller usage
    public List<Booking> getPreviousBookings(int userId) {
        List<Booking> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setShowId(rs.getInt("show_id"));
                b.setSeatCount(rs.getInt("seat_count"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setStatus(rs.getString("status"));
                b.setBookingDate(rs.getTimestamp("booking_date"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
