package com.yogesh.annobased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {

    @Value("101")
    private int id;

    @Value("John Doe")
    private String name;

    @Autowired
    private Address address;

    @Value("Math,Physics,Chemistry")
    private String subjectsString;

    private String[] subjects;

    @Autowired
    public void init() {
        subjects = subjectsString.split(",");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void show() {
        System.out.println("Student ID: " + id);
        System.out.println("Student Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Subjects:");
        for (String subject : subjects) {
            System.out.println(" - " + subject);
        }
    }
}
