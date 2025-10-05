package com.yogesh.service;

import com.yogesh.dto.PaymentDto;
import com.yogesh.util.DbUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentDto.PaymentResponse makePayment(PaymentDto.PaymentRequest request) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "INSERT INTO payments (booking_id, amount, payment_method, transaction_id, gateway_response) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, request.getBookingId());
                ps.setBigDecimal(2, request.getAmount());
                ps.setString(3, request.getPaymentMethod());
                String txnId = "TXN"+System.currentTimeMillis();
                ps.setString(4, txnId);
                ps.setString(5, "SUCCESS");
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    long paymentId = keys.getLong(1);
                    String updateBookingSQL = "UPDATE bookings SET payment_status = 'PAID' WHERE booking_id=?";
                    try (PreparedStatement up = conn.prepareStatement(updateBookingSQL)) {
                        up.setLong(1, request.getBookingId());
                        up.executeUpdate();
                    }

                    PaymentDto.PaymentResponse res = new PaymentDto.PaymentResponse();
                    res.setPaymentId(paymentId);
                    res.setBookingId(request.getBookingId());
                    res.setAmount(request.getAmount());
                    res.setTransactionId(txnId);
                    res.setPaymentMethod(request.getPaymentMethod());
                    res.setGatewayResponse("SUCCESS");
                    return res;
                }
                throw new Exception("Payment failed");
            }
        }
    }

    @Override
    public PaymentDto.PaymentResponse getPaymentById(Long paymentId) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "SELECT * FROM payments WHERE payment_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, paymentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        PaymentDto.PaymentResponse res = new PaymentDto.PaymentResponse();
                        res.setPaymentId(paymentId);
                        res.setBookingId(rs.getLong("booking_id"));
                        res.setAmount(rs.getBigDecimal("amount"));
                        res.setTransactionId(rs.getString("transaction_id"));
                        res.setPaymentMethod(rs.getString("payment_method"));
                        res.setGatewayResponse(rs.getString("gateway_response"));
                        return res;
                    }
                    return null;
                }
            }
        }
    }
}
