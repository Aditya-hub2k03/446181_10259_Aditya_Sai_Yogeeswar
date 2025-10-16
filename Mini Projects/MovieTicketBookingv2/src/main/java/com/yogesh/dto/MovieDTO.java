package com.yogesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private int movieId;
    private String title;
    private String synopsis;
    private String cast;
    private String crew;
    private Date releaseDate;
    private String posterUrl;
    private double rating;
    private String genre;
    private String language;
    private String formats;
}
