package com.yogesh.entity;

import lombok.Data;
import java.sql.Date;

@Data
public class Movie {
    private int movieId;
    private String movieTitle;
    private int durationMinutes;
    private String language;
    private String genre;
    private String description;
    private String posterUrl;
    private Date releaseDate;
}
