package com.xmldi.ci;
public class Student {
    private int id;
    private String name;
    private Address address;
    private String[] subjects;

    public Student(int id, String name, Address address, String[] subjects) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subjects = subjects;
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
