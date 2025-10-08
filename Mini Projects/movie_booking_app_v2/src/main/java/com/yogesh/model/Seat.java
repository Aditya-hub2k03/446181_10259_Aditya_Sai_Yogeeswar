package com.yogesh.model;

import lombok.Data;

@Data
public class Seat {
    private int seatId;
    private int showId;
    private String seatNumber;
    private String status; // AVAILABLE, SOLD, BEST, HANDICAPPED
    private double price;
}
