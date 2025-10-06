package com.yogesh.entity;

import lombok.Data;

@Data
public class Screen {
    private int screenId;
    private int theatreId; // FK reference to Theatre
    private String screenName;
    private int totalSeats;
}
