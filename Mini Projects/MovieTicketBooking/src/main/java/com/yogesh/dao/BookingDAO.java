package com.yogesh.dao;

import com.yogesh.model.Booking;
import com.yogesh.model.Seat;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository

public class BookingDAO {

    // Fetch Seat Layout and Availability for a Show
    public List<Seat> fetchSeatLayoutAndAvailability(int showId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT seat_id, seat_number, seat_type, price, is_available, is_handicapped FROM seats WHERE show_id = ? ORDER BY seat_number";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setShowId(rs.getInt("show_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setSeatType(rs.getString("seat_type"));
                seat.setPrice(rs.getDouble("price"));
                seat.setAvailable(rs.getBoolean("is_available"));
                seat.setHandicapped(rs.getBoolean("is_handicapped"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    // Reserve Seat Temporarily (before payment)
    public boolean reserveSeatTemporarily(int seatId, int showId) {
        String sql = "UPDATE seats SET is_available = FALSE WHERE seat_id = ? AND show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, seatId);
            stmt.setInt(2, showId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create a Booking Record
    public int createBookingRecord(int userId, int showId, double totalPrice) {
        String sql = "INSERT INTO bookings (user_id, show_id, booking_time, total_price, status, created_on) VALUES (?, ?, NOW(), ?, 'PENDING', NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, showId);
            stmt.setDouble(3, totalPrice);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Update Booking Status
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ?, modified_on = NOW() WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch Booking Details for Payment Page
    public Booking fetchBookingDetails(int bookingId) {
        String sql = "SELECT booking_id, user_id, show_id, booking_time, total_price, status FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setShowId(rs.getInt("show_id"));
                booking.setBookingTime(rs.getTimestamp("booking_time"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                booking.setStatus(rs.getString("status"));
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
