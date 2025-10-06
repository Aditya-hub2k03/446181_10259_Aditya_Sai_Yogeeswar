package com.yogesh.entity;

import lombok.Data;

@Data
public class BookingItem {
    private int bookingItemId;
    private int bookingId;   // FK to Booking
    private int showSeatId;  // FK to ShowSeat
    private double price;
}
