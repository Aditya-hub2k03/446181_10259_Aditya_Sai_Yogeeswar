package com.yogesh.model;

import lombok.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {
    private int userId;
    private int movieId;
    private Timestamp createdAt;
}
