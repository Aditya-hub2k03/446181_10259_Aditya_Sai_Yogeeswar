package com.yogesh.model;

import lombok.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    private int movieId;
    private String title;
    private String language;
    private String genre;
    private Date releaseDate;
    private int duration;
    private String posterUrl;
    private float rating;
    private String synopsis;
    private Timestamp createdAt;
}
