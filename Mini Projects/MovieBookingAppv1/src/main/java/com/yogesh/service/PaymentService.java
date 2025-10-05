package com.yogesh.service;

import com.yogesh.dto.PaymentDto;

public interface PaymentService {
    PaymentDto.PaymentResponse makePayment(PaymentDto.PaymentRequest request) throws Exception;
    PaymentDto.PaymentResponse getPaymentById(Long paymentId) throws Exception;
}
