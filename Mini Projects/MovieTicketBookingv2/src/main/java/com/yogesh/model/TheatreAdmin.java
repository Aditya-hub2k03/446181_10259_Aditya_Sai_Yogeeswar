package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TheatreAdmin {
    private int adminId;
    private int userId;
    private int theatreId;

}
