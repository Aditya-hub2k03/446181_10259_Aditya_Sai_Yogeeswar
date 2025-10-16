package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theatre {
    private int theatreId;
    private String theatreName;
    private String city;
    private String address;
}
