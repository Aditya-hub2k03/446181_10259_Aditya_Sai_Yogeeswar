package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private int bookingId;
    private int userId;
    private int showId;
    private Timestamp bookingTime;
    private double totalPrice;
    private String status;
    private Timestamp createdOn;
    private Timestamp modifiedOn;
}
