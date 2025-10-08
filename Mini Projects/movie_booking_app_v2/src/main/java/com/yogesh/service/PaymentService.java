package com.yogesh.service;

import com.yogesh.model.Payment;
import com.yogesh.util.DBConnectionUtil;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class PaymentService {

    public boolean recordPayment(Payment payment) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO payments(booking_id, payment_method, amount, status) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, payment.getBookingId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Payment> getPaymentsByUser(int userId) {
        List<Payment> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT p.* FROM payments p JOIN bookings b ON p.booking_id = b.booking_id WHERE b.user_id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setBookingId(rs.getInt("booking_id"));
                p.setPaymentMethod(rs.getString("payment_method"));
                p.setAmount(rs.getDouble("amount"));
                p.setStatus(rs.getString("status"));
                p.setPaymentDate(rs.getTimestamp("payment_date"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
