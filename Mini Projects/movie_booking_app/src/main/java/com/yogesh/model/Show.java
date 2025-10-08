package com.yogesh.model;

import lombok.*;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {
    private int showId;
    private int movieId;
    private int theatreId;
    private Timestamp showTime;
    private BigDecimal price;
    private int availableSeats;
}
