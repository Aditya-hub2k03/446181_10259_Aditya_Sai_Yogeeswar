package com.yogesh.model;

import lombok.Data;
import java.util.Date;

@Data
public class Payment {
    private int paymentId;
    private int bookingId;
    private String paymentMethod;
    private double amount;
    private String status; // SUCCESS / FAILURE
    private Date paymentDate;
}
