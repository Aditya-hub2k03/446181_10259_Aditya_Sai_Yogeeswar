package com.yogesh.dao;

import com.yogesh.model.Payment;
import com.yogesh.util.DBUtil;
import java.sql.*;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO {

    // Insert Payment Record
    public boolean insertPaymentRecord(int bookingId, int userId, double amount, String paymentMethod, String paymentStatus, String transactionId) {
        String sql = "INSERT INTO payments (booking_id, user_id, amount, payment_method, payment_status, transaction_id, created_on) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.setInt(2, userId);
            stmt.setDouble(3, amount);
            stmt.setString(4, paymentMethod);
            stmt.setString(5, paymentStatus);
            stmt.setString(6, transactionId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch Payment Details by Booking ID
    public Payment fetchPaymentDetails(int bookingId) {
        String sql = "SELECT payment_id, booking_id, user_id, amount, payment_method, payment_status, transaction_id, created_on " +
                     "FROM payments WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setUserId(rs.getInt("user_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                payment.setTransactionId(rs.getString("transaction_id"));
                payment.setCreatedOn(rs.getTimestamp("created_on"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
