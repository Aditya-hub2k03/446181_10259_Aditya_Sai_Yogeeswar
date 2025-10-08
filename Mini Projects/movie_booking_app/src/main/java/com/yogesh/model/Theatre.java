package com.yogesh.model;

import lombok.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theatre {
    private int theatreId;
    private String theatreName;
    private String city;
    private String address;
    private int ownerId;
}
