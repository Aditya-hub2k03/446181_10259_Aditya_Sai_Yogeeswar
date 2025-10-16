package com.yogesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private int showId;
    private int movieId;
    private int theatreId;
    private Date showDate;
    private Time showTime;
    private String format;
}
