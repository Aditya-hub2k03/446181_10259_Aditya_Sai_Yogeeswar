package com.yogesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private int bookingId;
    private int userId;
    private int showId;
    private Timestamp bookingTime;
    private double totalPrice;
    private String status;
}
