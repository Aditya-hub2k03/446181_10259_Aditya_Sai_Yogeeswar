package com.yogesh.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Booking {
    private int bookingId;
    private int userId;
    private int showId;
    private String selectedSeats; // Comma separated
    private int seatCount;
    private double totalAmount;
    private String status; // CONFIRMED, PENDING, FAILED
    private Timestamp bookingDate;
}
