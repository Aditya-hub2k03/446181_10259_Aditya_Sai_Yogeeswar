package com.yogesh.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class ShowSeat {
    private int showSeatId;
    private int showId; // FK to Show
    private int seatId; // FK to Seat
    private double price;
    private Status status = Status.AVAILABLE;
    private Timestamp reservedUntil;
    private String reservationToken;
    private int version;

    public enum Status {
        AVAILABLE,
        RESERVED,
        BOOKED
    }
}
