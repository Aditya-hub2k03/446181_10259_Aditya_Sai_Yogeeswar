package com.yogesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private int paymentId;
    private int bookingId;
    private int userId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
}
