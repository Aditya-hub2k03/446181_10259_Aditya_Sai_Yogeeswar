package com.yogesh.model;

import lombok.Data;
import java.util.Date;

@Data
public class Movie {
    private int movieId;
    private String title;
    private String language;
    private String genre;
    private String synopsis;
    private Date releaseDate;
    private String posterUrl;
}
