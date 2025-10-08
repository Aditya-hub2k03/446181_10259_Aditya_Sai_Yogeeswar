package com.yogesh.service;

import com.yogesh.model.Seat;
import com.yogesh.util.DBConnectionUtil;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class SeatService {

    public List<Seat> getSeatsByShow(int showId) {
        List<Seat> seats = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM seats WHERE show_id=? ORDER BY seat_number"
            );
            ps.setInt(1, showId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Seat s = new Seat();
                s.setSeatId(rs.getInt("seat_id"));
                s.setShowId(rs.getInt("show_id"));
                s.setSeatNumber(rs.getString("seat_number"));
                s.setStatus(rs.getString("status"));
                seats.add(s);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void markSeatsAsSold(int showId, String selectedSeats) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            String[] seats = selectedSeats.split(",");
            for(String seat : seats) {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE seats SET status='SOLD' WHERE show_id=? AND seat_number=?"
                );
                ps.setInt(1, showId);
                ps.setString(2, seat);
                ps.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
