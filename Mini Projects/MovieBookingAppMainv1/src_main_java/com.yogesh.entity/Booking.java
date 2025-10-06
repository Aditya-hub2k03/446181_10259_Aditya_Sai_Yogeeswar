package com.yogesh.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Booking {
    private int bookingId;
    private int userId;  // FK to User
    private int showId;  // FK to Show
    private double totalAmount;
    private Status status = Status.INITIATED;
    private Timestamp createdAt;
    private String paymentReference;

    public enum Status {
        INITIATED,
        CONFIRMED,
        CANCELLED,
        FAILED
    }
}
