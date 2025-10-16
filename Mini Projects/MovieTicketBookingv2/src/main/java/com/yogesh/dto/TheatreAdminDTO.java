package com.yogesh.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreAdminDTO {
    private int adminId;
    private int userId;
    private int theatreId;
    private String userName;
    private String theatreName;

}
