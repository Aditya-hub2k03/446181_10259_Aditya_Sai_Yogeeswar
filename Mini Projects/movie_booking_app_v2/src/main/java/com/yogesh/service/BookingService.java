package com.yogesh.service;

import com.yogesh.model.Booking;
import com.yogesh.dto.BookingRequestDto;
import com.yogesh.dto.BookingResponseDto;
import com.yogesh.util.DBConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class BookingService {

    @Autowired
    private SeatService seatService;

    public BookingResponseDto createBooking(BookingRequestDto dto) {
        BookingResponseDto response = new BookingResponseDto();
        try (Connection con = DBConnectionUtil.getConnection()) {

            PreparedStatement priceStmt = con.prepareStatement("SELECT price FROM shows WHERE show_id=?");
            priceStmt.setInt(1, dto.getShowId());
            ResultSet rs = priceStmt.executeQuery();

            if (!rs.next()) {
                response.setSuccess(false);
                response.setMessage("Show not found!");
                return response;
            }

            double price = rs.getDouble("price");
            int seatCount = dto.getSelectedSeats().split(",").length;
            double total = price * seatCount;

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bookings(user_id, show_id, seat_count, total_amount, selected_seats, status) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, dto.getUserId());
            ps.setInt(2, dto.getShowId());
            ps.setInt(3, seatCount);
            ps.setDouble(4, total);
            ps.setString(5, dto.getSelectedSeats());
            ps.setString(6, "CONFIRMED");

            if (ps.executeUpdate() > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if(keys.next()) response.setBookingId(keys.getInt(1));
                response.setSuccess(true);
                response.setMessage("Booking successful!");
                response.setTotalAmount(total);
                seatService.markSeatsAsSold(dto.getShowId(), dto.getSelectedSeats());
            }

        } catch(Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public Booking getBookingById(int bookingId) {
        try(Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings WHERE booking_id=?");
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setShowId(rs.getInt("show_id"));
                b.setSeatCount(rs.getInt("seat_count"));
                b.setSelectedSeats(rs.getString("selected_seats"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setStatus(rs.getString("status"));
                b.setBookingDate(rs.getTimestamp("booking_date"));
                return b;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
