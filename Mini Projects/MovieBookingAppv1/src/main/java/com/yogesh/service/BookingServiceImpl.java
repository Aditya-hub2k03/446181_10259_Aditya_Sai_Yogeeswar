package com.yogesh.service;

import com.yogesh.dto.BookingDto;
import com.yogesh.util.DbUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public BookingDto.BookingResponse bookSeats(BookingDto.BookingRequest request) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);

            for (Long seatId : request.getSeatIds()) {
                if (!isSeatAvailable(conn, request.getShowId(), seatId)) {
                    throw new Exception("Seat " + seatId + " not available");
                }
            }

            String sql = "INSERT INTO bookings (user_id, show_id, booking_time, cancellation_status, payment_status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, request.getUserId());
                ps.setLong(2, request.getShowId());
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.setBoolean(4, false);
                ps.setString(5, "PENDING");
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    long bookingId = keys.getLong(1);
                    insertBookingSeats(conn, bookingId, request.getSeatIds());
                    conn.commit();

                    BookingDto.BookingResponse res = new BookingDto.BookingResponse();
                    res.setBookingId(bookingId);
                    res.setUserId(request.getUserId());
                    res.setShowId(request.getShowId());
                    res.setSeatIds(request.getSeatIds());
                    res.setBookingTime(new Timestamp(System.currentTimeMillis()).toString());
                    res.setCancellationStatus(false);
                    res.setPaymentStatus("PENDING");
                    return res;
                }
                throw new Exception("Booking failed");
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public BookingDto.BookingResponse getBookingById(Long bookingId) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "SELECT * FROM bookings WHERE booking_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, bookingId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        BookingDto.BookingResponse res = new BookingDto.BookingResponse();
                        res.setBookingId(bookingId);
                        res.setUserId(rs.getLong("user_id"));
                        res.setShowId(rs.getLong("show_id"));
                        res.setBookingTime(rs.getTimestamp("booking_time").toString());
                        res.setCancellationStatus(rs.getBoolean("cancellation_status"));
                        res.setPaymentStatus(rs.getString("payment_status"));
                        res.setSeatIds(getSeatIds(conn, bookingId));
                        return res;
                    }
                    return null;
                }
            }
        }
    }

    @Override
    public void cancelBooking(Long bookingId) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET cancellation_status = TRUE WHERE booking_id=?")) {
            ps.setLong(1, bookingId);
            ps.executeUpdate();
        }
    }

    private boolean isSeatAvailable(Connection conn, Long showId, Long seatId) throws Exception {
        String sql = "SELECT COUNT(*) FROM booking_seats bs JOIN bookings b ON bs.booking_id=b.booking_id WHERE bs.seat_id=? AND b.show_id=? AND b.cancellation_status=FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, seatId);
            ps.setLong(2, showId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) == 0;
            }
        }
    }

    private void insertBookingSeats(Connection conn, long bookingId, List<Long> seatIds) throws Exception {
        String sql = "INSERT INTO booking_seats (booking_id, seat_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Long seatId : seatIds) {
                ps.setLong(1, bookingId);
                ps.setLong(2, seatId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private List<Long> getSeatIds(Connection conn, long bookingId) throws Exception {
        List<Long> seatIds = new ArrayList<>();
        String sql = "SELECT seat_id FROM booking_seats WHERE booking_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seatIds.add(rs.getLong("seat_id"));
                }
            }
        }
        return seatIds;
    }
}
