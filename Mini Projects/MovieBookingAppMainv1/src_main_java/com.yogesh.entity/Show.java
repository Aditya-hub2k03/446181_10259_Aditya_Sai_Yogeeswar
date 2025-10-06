package com.yogesh.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Show {
    private int showId;
    private int movieId;  // FK to Movie
    private int screenId; // FK to Screen
    private Timestamp startTime;
    private Timestamp endTime;
    private double basePrice;
    private Status status = Status.SCHEDULED;

    public enum Status {
        SCHEDULED,
        CANCELLED
    }
}
