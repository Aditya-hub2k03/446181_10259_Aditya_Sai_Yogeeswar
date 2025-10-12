package com.yogesh.dao;

import com.yogesh.model.Seat;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SeatDAO {

    // Fetch Seat Layout and Availability for a Show
    public List<Seat> fetchSeatLayoutAndAvailability(int showId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT seat_id, seat_number, seat_type, price, is_available, is_handicapped " +
                     "FROM seats WHERE show_id = ? ORDER BY seat_number";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setShowId(showId);
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
}
