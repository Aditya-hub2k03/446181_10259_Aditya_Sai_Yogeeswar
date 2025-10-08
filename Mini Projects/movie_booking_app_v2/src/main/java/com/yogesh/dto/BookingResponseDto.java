package com.yogesh.dto;

import lombok.Data;

@Data
public class BookingResponseDto {
    private boolean success;
    private String message;
    private int bookingId;
    private double totalAmount;
    private String bookedSeats; // comma-separated seats
}
