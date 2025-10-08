package com.yogesh.service;

import com.yogesh.model.Booking;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    public Booking createBooking(int userId, int showId, double totalAmount) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO bookings (user_id, show_id, total_amount, status) VALUES (?, ?, ?, 'PENDING')",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setInt(2, showId);
            stmt.setBigDecimal(3, BigDecimal.valueOf(totalAmount));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int bookingId = rs.getInt(1);
                return getBookingById(bookingId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booking getBookingById(int bookingId) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM bookings WHERE booking_id=?");
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapBooking(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM bookings WHERE user_id=? ORDER BY booking_time DESC");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Booking mapBooking(ResultSet rs) throws SQLException {
        return Booking.builder()
                .bookingId(rs.getInt("booking_id"))
                .userId(rs.getInt("user_id"))
                .showId(rs.getInt("show_id"))
                .totalAmount(rs.getBigDecimal("total_amount"))
                .status(rs.getString("status"))
                .bookingTime(rs.getTimestamp("booking_time"))
                .build();
    }
}
