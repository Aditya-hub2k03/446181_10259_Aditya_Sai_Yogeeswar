package com.yogesh.model;

import lombok.Data;
import java.util.Date;

@Data
public class Show {
    private int showId;
    private int theatreId;
    private int movieId;
    private String showTime;
    private Date showDate;
    private double price;
}
