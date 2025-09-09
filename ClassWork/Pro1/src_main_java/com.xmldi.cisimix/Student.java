package com.xmldi.cisimix;

public class Student {

    private int id;
    private String name;
    private Address address;
    private String[] subjects;

    // Constructor for id and name
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() {
    }

    // Setters for address and subjects
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
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
        if (subjects != null) {
            for (String sub : subjects) {
                System.out.println(" - " + sub);
            }
        }
    }
}
