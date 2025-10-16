package com.yogesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private int seatId;
    private int showId;
    private String seatNumber;
    private String seatType;
    private double price;
    private boolean isAvailable;
    private boolean isHandicapped;
}
