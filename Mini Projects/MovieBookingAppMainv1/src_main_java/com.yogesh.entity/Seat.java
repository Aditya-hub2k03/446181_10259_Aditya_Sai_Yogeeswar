package com.yogesh.entity;

import lombok.Data;

@Data
public class Seat {
    private int seatId;
    private int screenId; // FK reference to Screen
    private String seatCode; // Example: A10
    private String rowChar;  // Example: A, B, C
    private int col;         // Column number
    private String category; // Example: VIP, Normal
    private boolean isActive = true;
}
