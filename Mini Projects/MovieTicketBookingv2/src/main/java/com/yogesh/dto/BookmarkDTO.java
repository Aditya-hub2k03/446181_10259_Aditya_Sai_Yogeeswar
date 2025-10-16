package com.yogesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private int bookmarkId;
    private int userId;
    private int movieId;
    private Timestamp createdOn;
}
