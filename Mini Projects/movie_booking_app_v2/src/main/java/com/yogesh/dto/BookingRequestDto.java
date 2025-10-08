package com.yogesh.dto;

import lombok.Data;

@Data
public class BookingRequestDto {
    private int userId;
    private int showId;
    private String selectedSeats; // comma-separated seats like "A1,A2,B3"
}
