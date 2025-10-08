package com.yogesh.service;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class PaymentService {

    public boolean processPayment(int bookingId, String method, java.math.BigDecimal amount) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO payments (booking_id, payment_method, amount, payment_status) VALUES (?, ?, ?, 'SUCCESS')");
            stmt.setInt(1, bookingId);
            stmt.setString(2, method);
            stmt.setBigDecimal(3, amount);
            stmt.executeUpdate();

            PreparedStatement update = con.prepareStatement("UPDATE bookings SET status='CONFIRMED' WHERE booking_id=?");
            update.setInt(1, bookingId);
            update.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try (Connection con = DBUtil.getConnection()) {
                PreparedStatement update = con.prepareStatement("UPDATE bookings SET status='FAILED' WHERE booking_id=?");
                update.setInt(1, bookingId);
                update.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}
