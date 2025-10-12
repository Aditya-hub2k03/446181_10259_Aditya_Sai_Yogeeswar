package com.yogesh.service;

import com.yogesh.dao.PaymentDAO;
import com.yogesh.dto.PaymentDTO;
import com.yogesh.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    public boolean insertPaymentRecord(int bookingId, int userId, double amount, String paymentMethod, String paymentStatus, String transactionId) {
        return paymentDAO.insertPaymentRecord(bookingId, userId, amount, paymentMethod, paymentStatus, transactionId);
    }

    public PaymentDTO fetchPaymentDetails(int bookingId) {
        Payment payment = paymentDAO.fetchPaymentDetails(bookingId);
        return convertToDTO(payment);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBookingId());
        dto.setUserId(payment.getUserId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setTransactionId(payment.getTransactionId());
        return dto;
    }
}
