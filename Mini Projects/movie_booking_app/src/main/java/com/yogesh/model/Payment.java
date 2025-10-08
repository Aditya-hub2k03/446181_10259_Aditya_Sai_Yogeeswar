package com.yogesh.model;

import lombok.*;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private int paymentId;
    private int bookingId;
    private String paymentMethod;
    private BigDecimal amount;
    private String paymentStatus;
    private Timestamp paymentTime;
}
