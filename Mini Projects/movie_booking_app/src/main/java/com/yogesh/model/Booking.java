package com.yogesh.model;

import lombok.*;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    private int bookingId;
    private int userId;
    private int showId;
    private BigDecimal totalAmount;
    private String status;
    private Timestamp bookingTime;
}
