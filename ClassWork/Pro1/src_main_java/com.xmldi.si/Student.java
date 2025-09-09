package com.xmldi.si;
public class Student {
    private int id;
    private String name;
    private Address address;
    private String[] subjects;

    public Student() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setSubjects(String[] subjects) {
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
