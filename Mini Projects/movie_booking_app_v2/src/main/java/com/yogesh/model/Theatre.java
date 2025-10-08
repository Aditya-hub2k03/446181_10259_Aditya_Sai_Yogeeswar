package com.yogesh.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Theatre implements Serializable {
    private int theatreId;
    private String name;
    private String city;
    private String address;
    private int totalScreens;
}
