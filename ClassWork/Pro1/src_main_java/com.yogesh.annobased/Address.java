package com.yogesh.annobased;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Address {

    @Value("New York")
    private String city;

    @Value("NY")
    private String state;

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return city + ", " + state;
    }
}
